package com.yinjie.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yinjie.VO.DeleteRedis;
import com.yinjie.dao.EmployeeDao;
import com.yinjie.dao.JobDao;
import com.yinjie.domin.Employee;
import com.yinjie.domin.Job;
import com.yinjie.form.EmployeeSuccess;
import com.yinjie.service.EmployeeService;
import com.yinjie.service.JobService;
import com.yinjie.utils.RedisKeyDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {


    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private DeleteRedis deleteRedis;

    @Override
    public List<Employee> findAllJobAndApartmentAndEmployee() {
        return this.baseMapper.findAllJobAndApartmentAndEmployee();
    }

    @Override
    public IPage<Employee> findAllJobAndApartmentAndEmployeePage(IPage page, QueryWrapper queryWrapper, EmployeeSuccess employeeSuccess) {


        // 构建缓存的键，可以包含分页信息和查询条件（这里简化了）
        String cacheKey = "employees_page_" + page.getCurrent() + "_" + page.getSize();

//        如果是搜索
        if (employeeSuccess.getEmployeeName() != "" || employeeSuccess.getApartmentId() != null ||
                employeeSuccess.getJobName() != "" || employeeSuccess.getState() != null) {
            return this.baseMapper.findAllJobAndApartmentAndEmployeePage(page, queryWrapper);
        }

        // 从Redis获取缓存数据
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cachedEmployeesJson = ops.get(cacheKey);


        // 1.缓存中有数据
        if (cachedEmployeesJson != null) {
            try {
//                反序列化为Employee对象的列表
                List<Employee> employees = new ObjectMapper().readValue(cachedEmployeesJson, new TypeReference<List<Employee>>() {
                });

                long currentPage = page.getCurrent(); // 当前页码
                long pageSize = page.getSize(); // 每页大小
                long total = super.count(); // 总记录数，这通常是另一个需要存储或计算的值

                // 创建一个Page实例来封装Employee列表
                IPage<Employee> page2 = new Page<>(currentPage, pageSize, total);
                page.setRecords(employees); // 设置记录列表

                // 现在page是一个IPage<Employee>实例，返回
                return page2;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 2.缓存中没有数据

        // 执行数据库查询
        IPage<Employee> pageResult = this.baseMapper.findAllJobAndApartmentAndEmployeePage(page, queryWrapper);
        System.out.println(pageResult.getRecords());
        // 将查询结果缓存起来（
        if (pageResult != null && !pageResult.getRecords().isEmpty()) {
            try {
                String jsonString = new ObjectMapper().writeValueAsString(pageResult.getRecords());
                ops.set(cacheKey, jsonString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // 返回数据库查询结果
        return pageResult;


    }


    @Override
    public boolean updateById(Employee entity) {

        //删除缓存
        deleteRedis.deleter();

        this.baseMapper.updateById(entity);
        return true;
    }

    @Override
    public boolean identifyNewOldJobId(int employeeId, int employeeJobId) {
        //如果修改时的 岗位一样 avaliable就不加1了
        Employee employee = employeeDao.selectById(employeeId);
        if (employee.getJobId() == employeeJobId) {
            return false;
        } else {
            // 将新的职位available+1，旧的available-1
            //通过employee.getJobId()取查job表对应的available
            Job job = jobDao.selectById(employee.getJobId());
            job.setAvailable(job.getAvailable() - 1);
            jobDao.updateJobById(job);//更新
            return true;
        }
    }

    @Override
    public void UpdateAvailableAfterDelete(int id) {

        Employee employee = employeeDao.selectById(id);//获取该职员信息
        Job job = jobDao.selectById(employee.getJobId());//获取该职员的岗位信息
        job.setAvailable(job.getAvailable() - 1);//删除后该岗位-1
        jobDao.updateJobById(job);//更新

        //删除缓存
        deleteRedis.deleter();
        employeeDao.deleteById(id);//删除


    }

    @Override
    public void insert(Employee employee) {
        employeeDao.insert(employee);
    }


}

package com.yinjie.service.impl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yinjie.dao.JobDao;
import com.yinjie.domin.Employee;
import com.yinjie.domin.Job;
import com.yinjie.domin.User;
import com.yinjie.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl extends ServiceImpl<JobDao, Job> implements JobService {
    @Autowired
    private JobDao jobDao;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public IPage<Job> findAllJobAndApartment(IPage page, QueryWrapper queryWrapper) {
        return this.baseMapper.findAllJobAndApartment(page, queryWrapper);
    }

    @Override
    public boolean findApartmentIdAndName(int ApartmentId, String name) {
        Job apartmentIdAndName = this.baseMapper.findApartmentIdAndName(ApartmentId, name);
        return true;
    }

    @Override
    public Job findByApartmentIdAndName(int ApartmentId, int employeeJobId) {
        Job byApartmentIdAndName = this.baseMapper.findByApartmentIdAndName(ApartmentId, employeeJobId);

        return byApartmentIdAndName;
    }

    @Override
    public void UpdateAvailable(int newAvailable, int ApartmentId, int employeeJobId) {
        jobDao.UpdateAvailable(newAvailable, ApartmentId, employeeJobId);
    }

    @Override
    public Job sendEmailFind(int ApartmentId, String JobName) {
        return this.baseMapper.sendEmailFind(ApartmentId,JobName);
    }

    @Override
    public int add(int ApartmentId, String name, int maxnum) {
        return jobDao.add(ApartmentId, name, maxnum);
    }

    @Override
    public int deleteById(int id) {
        return this.baseMapper.deleteById(id);
    }

    @Override
    public int updateJobById(Job job) {
        return jobDao.updateJobById(job);
    }

    @Override
    public List<Job> findJobNameByApartmentId(int apartmentId) {
        String key = "findJobNameByApartmentId_" + apartmentId; // 通常为了标识这是一个列表，我们可能会在key中加上复数形式
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();

// 尝试从Redis中获取JSON字符串形式的文章列表
        String JobNameByApartmentIdJson = opsForValue.get(key);
        if (JobNameByApartmentIdJson != null) {
            try {
                // 将JSON字符串反序列化为Article列表
                List<Job> articles = new ObjectMapper().readValue(JobNameByApartmentIdJson, new TypeReference<List<Job>>(){});
                return articles;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

// 如果没有在Redis中找到，则从数据库获取
        List<Job> jobs = this.baseMapper.findJobNameByApartmentId(apartmentId); // 假设findAllById返回List<Article>

// 如果找到了文章，将其序列化为JSON字符串并存储到Redis中
        if (!jobs.isEmpty()) {
            try {
                String json = new ObjectMapper().writeValueAsString(jobs);
                opsForValue.set(key, json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

// 返回文章列表（可能是空的）
        return jobs;
//        return this.baseMapper.findJobNameByApartmentId(apartmentId);
    }

}

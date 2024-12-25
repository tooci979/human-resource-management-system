package com.yinjie.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinjie.VO.EmployeeVo;
import com.yinjie.VO.DeleteRedis;
import com.yinjie.dao.EmployeeDao;
import com.yinjie.domin.Employee;
import com.yinjie.domin.Job;
import com.yinjie.form.EmployeeSuccess;
import com.yinjie.form.JobSuccess;
import com.yinjie.service.EmployeeService;
import com.yinjie.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private JobService jobService;
    @Autowired
    private DeleteRedis deleteRedis;


    @PostMapping("/findEmployeeAll")
    public ResponseEntity findJobAll() {
        List<Employee> allJobAndApartmentAndEmployee = employeeService.findAllJobAndApartmentAndEmployee();
        JobSuccess<Employee> employeeJobSuccess = new JobSuccess<>(allJobAndApartmentAndEmployee, true, 1, null, null, null, null);
        return new ResponseEntity<>(employeeJobSuccess, HttpStatus.OK);
    }

    @PostMapping("/findEmployeeAllPage")
    public ResponseEntity findEmployeeAllPage(@RequestBody EmployeeSuccess employeeSuccess) {

        Integer pageSize = employeeSuccess.getPageSize();
        Integer currentPage = employeeSuccess.getCurrentPage();

        Page<Employee> page = new Page<>(employeeSuccess.getCurrentPage(), employeeSuccess.getPageSize());
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();


//        //员工姓名查
        employeeQueryWrapper.like(employeeSuccess.getEmployeeName() != "","e.name", employeeSuccess.getEmployeeName());
        //员工部门查
        employeeQueryWrapper.eq(employeeSuccess.getApartmentId() != null,"j.apartment_id", employeeSuccess.getApartmentId());
        //员工岗位查
        employeeQueryWrapper.like(employeeSuccess.getJobName() != "","j.name", employeeSuccess.getJobName());
        //员工状态查
        employeeQueryWrapper.eq(employeeSuccess.getState() != null,"e.state", employeeSuccess.getState());

        long startTime=System.currentTimeMillis();
        IPage<Employee> allPage = employeeService.findAllJobAndApartmentAndEmployeePage(page, employeeQueryWrapper,employeeSuccess);
        long endTime=System.currentTimeMillis();
        System.out.println("时间差：" + (endTime - startTime));

        EmployeeSuccess<Employee> employeeEmployeeSuccess = new EmployeeSuccess<>(page.getRecords(), true, allPage.getTotal(), pageSize, currentPage, null, null, null, null);

        return new ResponseEntity<>(employeeEmployeeSuccess, HttpStatus.OK);
    }

    //    返回该部门的所有职位
    @PostMapping("/findJobNameByApartmentId")
    public ResponseEntity findJobNameByApartmentId(@RequestBody int apartmentId) {
        List<Job> jobNameByApartmentId = jobService.findJobNameByApartmentId(apartmentId);

        EmployeeSuccess<Job> jobEmployeeSuccess = new EmployeeSuccess<>(jobNameByApartmentId, null, 0, null, null, null, null, null, null);

        return new ResponseEntity<>(jobEmployeeSuccess, HttpStatus.OK);
    }

    //前端调用avaliabelIsNotFull和addEmployee
    //查询该部门的 该职位是否已满
    @PostMapping("/avaliabelIsNotFull")
    public boolean avaliabelIsNotFull(@RequestBody EmployeeVo employeeVo) {

        Job job = jobService.findByApartmentIdAndName(employeeVo.getApartmentId(), employeeVo.getEmployeeJobId());

//        如果对应职位招满了 就false
        if (job.getMaxnum() == job.getAvailable()) {
            return false;
        } else {
//            否则更新对应职位的getAvailable（以招聘人数）
            jobService.UpdateAvailable(job.getAvailable() + 1, employeeVo.getApartmentId(), employeeVo.getEmployeeJobId());
            return true;
        }
    }

    //    添加
    @PostMapping("/addEmployee")
    public void addEmployee(@RequestBody Employee employee) {
        System.out.println(employee);
        Employee employee1 = new Employee(
                null, "uu" + employee.getTelephone(), employee.getName(), employee.getGender(), employee.getJobId(), employee.getTelephone(), employee.getEmail(), new Date(), employee.getState());
        //删除对应缓存
        deleteRedis.deleter();

        employeeService.insert(employee1);
    }

    //    修改============================================
//查询该部门的 该职位是否已满， 未满则添加
    @PostMapping("/avaliabelIsNotFullWithUpdate")
    public boolean avaliabelIsNotFullWithUpdate(@RequestBody EmployeeVo employeeVo) {
        System.out.println(employeeVo);
        Job job = jobService.findByApartmentIdAndName(employeeVo.getApartmentId(), employeeVo.getEmployeeJobId());

//        修改时，该职位已满则 false
        if (job.getMaxnum() == job.getAvailable()) {
            return false;
        } else {
            int newAvailable=job.getAvailable();
//            先将以前的available-1
            boolean b = employeeService.identifyNewOldJobId(employeeVo.getEmployeeId(), employeeVo.getEmployeeJobId());
            if(b) newAvailable=job.getAvailable()+1;//前后职位不一样才 加1
            //这里更新数据
            jobService.UpdateAvailable(newAvailable, employeeVo.getApartmentId(), employeeVo.getEmployeeJobId());
            return true;
        }
    }


    //    修改
    @PostMapping("/UpdateEmployee")
    public boolean UpdateEmployee(@RequestBody Employee employee) {
        boolean b = employeeService.updateById(employee);
        return b;

    }
    @GetMapping("/deleteElemployee")
    public void deleteElemployee(int id) {

        employeeService.UpdateAvailableAfterDelete(id);


    }


}

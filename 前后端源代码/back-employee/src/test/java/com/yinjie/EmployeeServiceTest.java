package com.yinjie;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinjie.dao.EmployeeDao;
import com.yinjie.domin.Apartment;
import com.yinjie.domin.Employee;
import com.yinjie.domin.Job;
import com.yinjie.domin.User;
import com.yinjie.form.LoginUser;
import com.yinjie.service.EmployeeService;
import com.yinjie.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void findAllJobAndApartmentAndEmployee() {
        List<Employee> allJobAndApartmentAndEmployee = employeeDao.findAllJobAndApartmentAndEmployee();
        System.out.println(allJobAndApartmentAndEmployee);

    }

//    @Test
//    public void findAllJobAndApartmentAndEmployeePageTest() {
//        Page<User> page = new Page<>(1, 2);
//        QueryWrapper<Employee> queryWrapper = new QueryWrapper();
//        queryWrapper.like("e.name","李");
//        IPage<Employee> allJobAndApartmentAndEmployeePage = employeeService.findAllJobAndApartmentAndEmployeePage(page, queryWrapper);
//        System.out.println("总页数==>"+page.getPages());//总页数
//        System.out.println("总记录数==>"+page.getTotal());//总记录数
//        System.out.println("===============");
//        System.out.println(page.getRecords());
//    }

    @Test
    public void addTest() {
//        Employee employee = new Employee(null,"001","张三","男",2,"15671134567","abc@qq.com",new Date(),1);
//        int insert = employeeDao.insert(employee);

    }

    @Test
    public void UpdateByIdTest() {
        Employee employee = new Employee(26,"001","张三update","男",2,"15671134567","abc@qq.com",new Date(),1);

        boolean b = employeeService.updateById(employee);
        System.out.println(b);

    }

    @Test
    public void selectEmployeeByIdTest() {
//        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
//        employeeQueryWrapper.eq("id",1);
//        System.out.println(employeeService.list(employeeQueryWrapper));
        System.out.println(employeeDao.selectById(1));
    }
}

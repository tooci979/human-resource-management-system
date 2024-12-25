package com.yinjie.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yinjie.domin.Employee;
import com.yinjie.domin.User;
import com.yinjie.form.EmployeeSuccess;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
@Service
public interface EmployeeService extends IService<Employee> {
    public List<Employee> findAllJobAndApartmentAndEmployee();

    IPage<Employee> findAllJobAndApartmentAndEmployeePage(IPage page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper, EmployeeSuccess employeeSuccess);


    public boolean identifyNewOldJobId(int employeeId,int employeeJobId);

    public void UpdateAvailableAfterDelete(int id);

    public void insert(Employee employee);
}

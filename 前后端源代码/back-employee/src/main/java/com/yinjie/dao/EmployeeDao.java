package com.yinjie.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yinjie.domin.Employee;
import com.yinjie.domin.Job;
import com.yinjie.domin.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {

    public List<Employee> findAllJobAndApartmentAndEmployee();

    IPage<Employee> findAllJobAndApartmentAndEmployeePage(IPage page, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);


}

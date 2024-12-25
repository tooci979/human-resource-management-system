package com.yinjie.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yinjie.domin.Apartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApartmentDao extends BaseMapper<Apartment> {

    List<Apartment> findApartmentAll();

    @Select("select * from Apartment where department=#{department}")
    Apartment findApartmentByDept(String department);


}

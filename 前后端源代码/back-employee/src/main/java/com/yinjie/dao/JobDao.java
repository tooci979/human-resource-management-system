package com.yinjie.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yinjie.domin.Job;
import com.yinjie.domin.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface JobDao extends BaseMapper<Job> {


    public Job sendEmailFind(int ApartmentId,String JobName);

    IPage<Job> findAllJobAndApartment(IPage page,@Param(Constants.WRAPPER)  QueryWrapper queryWrapper);
//    查询要添加的部门职位 在数据库中有没有 ，有 返回false
    @Select("select * from Job where apartment_id=${ApartmentId} and name='${name}'")
    public Job findApartmentIdAndName(int ApartmentId,String name);

    @Insert("insert into job(apartment_id, name, maxnum, available) " +
            "values(#{ApartmentId},#{name},#{maxnum},0)")  //默认available=maxnum
    public int add(int ApartmentId,String name,int maxnum);

    @Delete("delete from job where id = #{id}")
    public int deleteById(int id);

    @Update("update job set  apartment_id =#{apartmentId}, name =#{name}, maxnum =#{maxnum}, available = #{available} " +
            " where id = #{id}")
    public int updateJobById(Job job);

//    查询该部门的所有职位
//   查询部门id为apartmentId的所有职位
    @Select("SELECT * from Job where apartment_id=#{apartmentId}")
    public List<Job> findJobNameByApartmentId(int apartmentId);

    //查询查询该部门的该职位是否已满  用于employee控制层
    @Select("SELECT * from job WHERE apartment_id=#{ApartmentId} and id=#{employeeJobId} ")
    public Job findByApartmentIdAndName(int ApartmentId,int employeeJobId);
    @Update("update job set available=#{newAvailable} where apartment_id=#{ApartmentId} and id=#{employeeJobId}")
    public void UpdateAvailable(int newAvailable ,int ApartmentId,int employeeJobId);


    //查寻该部门是否有职位存在==》apartment
    @Select("select * from job where apartment_id=#{id}")
    public List<Job> findApartmentIsNull(int id);
}

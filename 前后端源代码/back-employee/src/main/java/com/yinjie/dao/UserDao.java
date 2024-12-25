package com.yinjie.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yinjie.domin.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select * from user where user.username=#{username} and user.password=${password}")
    public User findByUsernameAndPassword(String username,String password);
//分页
    @Select("select * from user ${ew.customSqlSegment}")
    IPage<User> findUserPage(IPage page,@Param(Constants.WRAPPER) QueryWrapper queryWrapper);

    @Select("select * from user where username=#{username}")
    public User findByName(String username);

    @Select("select * from user where id=#{id}")
    public User findById(Integer id);

    @Update("update User set state=#{state} where id=#{id}" )
    public void updateState(Integer state,Integer id);
}

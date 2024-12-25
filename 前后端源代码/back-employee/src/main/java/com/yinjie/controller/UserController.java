package com.yinjie.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinjie.domin.User;
import com.yinjie.form.LoginData;
import com.yinjie.form.LoginSuccess;
import com.yinjie.form.LoginUser;
import com.yinjie.form.UserSuccess;
import com.yinjie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginUser LoginUser) {

        return userService.loginByCondition(LoginUser);


    }



    @PostMapping("/findUserPage")
    public ResponseEntity findUserPage(@RequestBody UserSuccess userSuccess) {
        System.out.println(userSuccess);
        Integer pageSize = userSuccess.getPageSize();
        Integer currentPage = userSuccess.getCurrentPage();
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq(!StringUtils.isEmpty(userSuccess.getCode()),"role",userSuccess.getCode());
        queryWrapper.like(userSuccess.getName()!="","username",userSuccess.getName());
        queryWrapper.eq(userSuccess.getStatus()!=null,"state",userSuccess.getStatus());

        Page<User> page = new Page<>(currentPage, pageSize);
        userService.findUserPage(page,queryWrapper);
//        总页数和总记录数  要加拦截器才能获取到
        System.out.println("总页数==>" + page.getPages());//总页数
        System.out.println("总记录数==>" + page.getTotal());//总记录数
        System.out.println(page.getRecords());

        UserSuccess<User> userUserSuccess =
                new UserSuccess<>(page.getRecords(), true, page.getTotal(), pageSize, currentPage, null, null, null);
        return new ResponseEntity<>(userUserSuccess, HttpStatus.OK);
    }

    @PostMapping("/insertUser")
    public boolean insertUser(@RequestBody User user){
//        查询改用户是否存在
        boolean byName = userService.findByName(user.getUsername());
//        不存在就添加
        if(byName){
            userService.save(user);
            return byName;
        }else {
            return byName;
        }

    }

    @PostMapping("/updateUser")
    public boolean updateUser(@RequestBody User user){

        User user1 = userService.findById(user.getId());
        boolean byName = userService.findByName(user.getUsername());
//
        if(user1.getUsername().equals(user.getUsername())||byName){

            userService.updateById(user);
            return true;
        }else {
           return false;
        }

    }

    @GetMapping("/deleteUser")
    public void deleteUser(int id) {
       userService.removeById(id);
    }


    @PostMapping("/insertUserWithRegister")
    public boolean insertUserWithLogin(@RequestBody User user){
        boolean byName = userService.findByName(user.getUsername());
        if(byName){
            userService.save(user);
        }
        return byName;

    }

    @PostMapping("/updateState")
    public void updateState(@RequestBody User user){
        System.out.println(user);
        userService.updateState(user.getState(),user.getId());
    }

}

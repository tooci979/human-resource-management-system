package com.yinjie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinjie.dao.UserDao;
import com.yinjie.domin.User;
import com.yinjie.form.LoginData;
import com.yinjie.form.LoginSuccess;
import com.yinjie.form.LoginUser;
import com.yinjie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService  {

    @Autowired
    public UserService userService;
    @Override
    public ResponseEntity loginByCondition(LoginUser LoginUser) {
        User byUsernameAndPassword = userService.findByUsernameAndPassword(LoginUser.getUsername(), LoginUser.getPassword());
//        http://localhost:8081/login?username=admin&&password=123456


        if(byUsernameAndPassword==null) {//用户不存在
            LoginSuccess<LoginData> loginSuccess = new LoginSuccess<LoginData>(null, false);
            return new ResponseEntity<>(loginSuccess, HttpStatus.OK);
        }
        if (byUsernameAndPassword.getRole() == 1&&byUsernameAndPassword.getState()==1) {//==1是admin
            LoginData adminUser = new LoginData();
            adminUser.setUsername(byUsernameAndPassword.getUsername());//用户名
            adminUser.setNickname(byUsernameAndPassword.getUsername());//昵称

//            List<String> mutableList = Arrays.asList("admin"); // 注意：Arrays.asList()返回的列表也是固定大小的，但可以通过set()修改元素
//            List<String> immutableListView = Collections.unmodifiableList(mutableList);//无法修改
            ArrayList<String> strings = new ArrayList<>();
            strings.add("admin");
            adminUser.setRoles(strings);  //页面级别权限 ,根据权限来生成对于的 左侧栏

            adminUser.setAvatar("src\\assets\\user.jpg");
            adminUser.setAccessToken("admin");
            adminUser.setRefreshToken("eyJhbGciOiJIUzUxMiJ9.adminRefresh");
            adminUser.setExpires(new Date(2030, 10, 30, 0, 0)); // 设置token过期的时间

            LoginSuccess<LoginData> loginSuccess = new LoginSuccess<LoginData>(adminUser, true);
            return new ResponseEntity<>(loginSuccess, HttpStatus.OK);

        } else if(byUsernameAndPassword.getRole() == 2&&byUsernameAndPassword.getState()==1){
            LoginData commonUser = new LoginData();
            commonUser.setUsername("common");
            commonUser.setNickname(byUsernameAndPassword.getUsername());
            List<String> mutableList = Arrays.asList("common"); // 注意：Arrays.asList()返回的列表也是固定大小的，但可以通过set()修改元素
            List<String> immutableListView = Collections.unmodifiableList(mutableList);

            commonUser.setRoles(immutableListView);
            commonUser.setAvatar("src\\assets\\common.jpg");
            commonUser.setAccessToken("common");
            commonUser.setRefreshToken("eyJhbGciOiJIUzUxMiJ9.commonRefresh");
            commonUser.setExpires(new Date(2030, 10, 30, 0, 0)); // 注意Java月份是从0开始的

            LoginSuccess<LoginData> loginSuccess = new LoginSuccess<LoginData>(commonUser, true);
            return new ResponseEntity<>(loginSuccess, HttpStatus.OK);
        }else {
            LoginSuccess<LoginData> loginSuccess = new LoginSuccess<LoginData>(null, false);
            return new ResponseEntity<>(loginSuccess,HttpStatus.OK);
        }

    }

    @Override
    public User findByUsernameAndPassword(String username,String password) {

        return this.baseMapper.findByUsernameAndPassword(username,password);
    }

    @Override
    public IPage<User> findUserPage(IPage page, QueryWrapper queryWrapper) {

        return this.baseMapper.findUserPage(page,queryWrapper);
    }

    @Override
    public boolean findByName(String username) {
        User byName = this.baseMapper.findByName(username);
        if(byName==null) return true;
        return false;
    }

    @Override
    public User findById(Integer id) {
        User byId = this.baseMapper.findById(id);

        return byId;
    }

    @Override
    public void updateState(Integer state, Integer id) {
        this.baseMapper.updateState(state,id);
    }

//    @Override
//    public boolean save(User entity) {
//        //如果没有此用户就 可以添加
//        if(findByName(entity.getUsername())){
//           return super.save(entity);
//        }
//        return false;
//    }
//    public boolean findNameIsExist(){
//        if(findByName(entity.getUsername())){
//            return super.save(entity);
//        }
//        return false;
//    }
}

package com.yinjie.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yinjie.domin.Apartment;
import com.yinjie.domin.User;
import com.yinjie.form.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
@Service
public interface UserService extends IService<User> {
    public ResponseEntity loginByCondition(LoginUser LoginUser);
    public User findByUsernameAndPassword(String username,String password);

    IPage<User> findUserPage(IPage page, QueryWrapper queryWrapper);


    public boolean findByName(String username);

    public User findById(Integer id);
    public void updateState(Integer state,Integer id);
}

package com.yinjie;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinjie.domin.User;
import com.yinjie.form.LoginUser;
import com.yinjie.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void findByUsernameAndPassword() {
        LoginUser admin = new LoginUser("admin", "123456");
        System.out.println(admin.getPassword());
        User byUsernameAndPassword = userService.findByUsernameAndPassword(admin.getUsername(), admin.getPassword());

        System.out.println(byUsernameAndPassword);

    }

    @Test
    public void findUserAll() {
        System.out.println(userService.list());
    }

    @Test
    public void findUserPage() {
        Page<User> page = new Page<>(1, 2);
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.like("username",null);
        IPage<User> userPage = userService.findUserPage(page,queryWrapper);
//        总页数和总记录数  要加拦截器才能获取到
        System.out.println("总页数==>"+page.getPages());//总页数
        System.out.println("总记录数==>"+page.getTotal());//总记录数
        System.out.println("===============");
        System.out.println(page.getRecords());

        System.out.println(userPage);


    }
}

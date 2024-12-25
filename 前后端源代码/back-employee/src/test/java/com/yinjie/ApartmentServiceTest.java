package com.yinjie;


import com.yinjie.dao.ApartmentDao;
import com.yinjie.domin.Apartment;
import com.yinjie.domin.User;
import com.yinjie.form.LoginUser;
import com.yinjie.service.ApartmentService;
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
public class ApartmentServiceTest {
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private ApartmentDao apartmentDao;

    @Test
    public void findAll() {

        List<Apartment> list = apartmentDao.selectList(null);
        System.out.println(list);
    }
    @Test
    public void InsertTest() {
        Apartment apartment = new Apartment(null,"13542132","111",1,"15678896362",new Date(),"13");

        boolean save = apartmentService.save(apartment);

    }

    @Test
    public void removeTest() {
        apartmentService.removeById(34);

    }
    @Test
    public void uPDATETest() {
        Apartment apartment = new Apartment(33,"13542132","111",1,"15678896362",new Date(),"13");

        apartmentService.updateById(apartment);

    }


}

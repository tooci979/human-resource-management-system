package com.yinjie;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinjie.dao.JobDao;
import com.yinjie.domin.Apartment;
import com.yinjie.domin.Job;
import com.yinjie.domin.User;
import com.yinjie.service.JobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Null;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobServiceTest {
    @Autowired
    private JobDao jobDao;

    @Autowired
    private JobService jobService;

//    @Test
//    public void findAllJobAndApartment() {
//        List<Job> allJobAndApartment = jobDao.findAllJobAndApartment();
//        System.out.println(allJobAndApartment);
//
//    }

    @Test
    public void findApartmentIdAndNameTest() {

        boolean apartmentIdAndName = jobService.findApartmentIdAndName(1,"教授");

        System.out.println(jobService.findApartmentIdAndName(5,"教授"));
    }

    @Test
    public void addTest() {

        jobService.add(3,"教授",10);
    }


    @Test
    public void updateJobByIdTest() {

        Job q = new Job(24, 999, "q", 5, 5, new Apartment());
        jobDao.updateJobById(q);

    }

    @Test
    public void findJobPage() {
        Page<Job> page = new Page<>(1, 2);
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
//        jobQueryWrapper.eq("apartment_id",2);
        IPage<Job> allJobAndApartment = jobService.findAllJobAndApartment(page,jobQueryWrapper);
        System.out.println("总页数==>"+page.getPages());//总页数
        System.out.println("总记录数==>"+page.getTotal());//总记录数
        System.out.println("===============");
        System.out.println(page.getRecords());
    }


    @Test
    public void findJobNameByApartmentIdTest() {
        List<Job> jobNameByApartmentId = jobService.findJobNameByApartmentId(4);
        System.out.println(jobNameByApartmentId);
    }

    @Test
    public void findAllById() {
        Job job = jobDao.selectById(1);
        System.out.println(job);
    }
}

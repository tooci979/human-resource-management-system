package com.yinjie.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yinjie.domin.Job;
import com.yinjie.domin.User;
import com.yinjie.form.*;
import com.yinjie.service.JobService;
import com.yinjie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;


    @PostMapping("/findJobAll")
    public ResponseEntity findJobAll(@RequestBody JobSuccess jobSuccess) {


        Integer pageSize = jobSuccess.getPageSize();
        Integer currentPage = jobSuccess.getCurrentPage();
        Page<Job> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        if(jobSuccess.getName()!="") jobQueryWrapper.like("name",jobSuccess.getName());
        if(jobSuccess.getApartmentId()!=null) jobQueryWrapper.eq("apartment_id",jobSuccess.getApartmentId());



        IPage<Job> allJobAndApartment = jobService.findAllJobAndApartment(page,jobQueryWrapper);


        JobSuccess<Job> jobJobSuccess = new JobSuccess<>(page.getRecords(),true,page.getTotal(), pageSize, currentPage,null,null);
        return new ResponseEntity<>(jobJobSuccess, HttpStatus.OK);
    }

    @PostMapping("/JobIsExist")
    public boolean JobIsExist(@RequestBody Job job) {
        jobService.add(job.getApartmentId(),job.getName(),job.getMaxnum());
        return true;


//        boolean apartmentIdAndName = jobService.findApartmentIdAndName(job.getApartmentId(), job.getName());
//        if(apartmentIdAndName){
//
//            return apartmentIdAndName;
//        }else {
//            return apartmentIdAndName;
//        }
    }

    @GetMapping("/deleteJob")
    public void deleteJob(int id) {
        jobService.deleteById(id);
    }


    @PostMapping("/updateJobById")
    public boolean updateJobById(@RequestBody Job job) {

        if(job.getMaxnum()<job.getAvailable()) return false;
        jobService.updateJobById(job);
        return true;
    }
}

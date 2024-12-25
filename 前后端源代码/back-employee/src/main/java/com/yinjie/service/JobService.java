package com.yinjie.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yinjie.domin.Job;
import com.yinjie.domin.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
@Service
public interface JobService extends IService<Job> {
    IPage<Job> findAllJobAndApartment(IPage page, QueryWrapper queryWrapper);

    public boolean findApartmentIdAndName(int ApartmentId,String name);

    public int add(int ApartmentId,String name,int maxnum);
    public int deleteById(int id);

    public int updateJobById(Job job);

    public List<Job> findJobNameByApartmentId(int apartmentId);
    public Job findByApartmentIdAndName(int ApartmentId,int employeeJobId);
    public void UpdateAvailable(int newAvailable ,int ApartmentId,int employeeJobId);
    public Job sendEmailFind(int ApartmentId,String JobName);
}

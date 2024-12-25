package com.yinjie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinjie.dao.ApartmentDao;
import com.yinjie.dao.JobDao;
import com.yinjie.domin.Apartment;
import com.yinjie.domin.Job;
import com.yinjie.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentServiceImpl extends ServiceImpl<ApartmentDao, Apartment> implements ApartmentService {

    @Autowired
    private ApartmentDao apartmentDao;
    @Autowired
    private JobDao jobDao;

    public boolean findApartmentByDept(String department){
        Apartment apartmentByDept = apartmentDao.findApartmentByDept(department);
        //添加时判断该部门是否存在
        if(apartmentByDept==null) return true;
        else return false;

    }

    @Override
    public boolean findApartmentIsNull(int id) {//id为apartment的id
        System.out.println(id);
        List<Job> apartmentIsNull = jobDao.findApartmentIsNull(id);//通过apartment的id去查job表
        System.out.println(apartmentIsNull);
        if(apartmentIsNull.isEmpty()) {
            super.removeById(id);//若该部门 没有要招聘的职位，就可以删除
            return true;
        }
        return false;
    }


}

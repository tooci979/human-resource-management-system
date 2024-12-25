package com.yinjie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yinjie.domin.Apartment;
import com.yinjie.domin.Job;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
@Service
public interface ApartmentService extends IService<Apartment> {
   public boolean findApartmentByDept(String department);

   public boolean findApartmentIsNull(int id);
}

package com.yinjie.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yinjie.domin.Apartment;
import com.yinjie.domin.User;
import com.yinjie.form.ApartmentSuccess;
import com.yinjie.form.LoginData;
import com.yinjie.form.LoginSuccess;
import com.yinjie.form.LoginUser;
import com.yinjie.service.ApartmentService;
import com.yinjie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/apartment")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @PostMapping("/deptList")
    public ResponseEntity deptList() {

        List<Apartment> list = apartmentService.list();
        ArrayList<Apartment> data = new ArrayList<>();
        for (Apartment o :list) {
            System.out.println(o);
            data.add(o);
        }
        ApartmentSuccess<Apartment> apartmentApartmentSuccess = new ApartmentSuccess<>(data, true);
        return new ResponseEntity<>(apartmentApartmentSuccess, HttpStatus.OK);
    }


    @PostMapping("/deptListQueryWrapper")
    public ResponseEntity deptListQueryWrapper(@RequestBody Apartment apartment) {
        QueryWrapper<Apartment> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(!StringUtils.isEmpty(apartment.getDepartment()),"department",apartment.getDepartment())
                .eq(!StringUtils.isEmpty(apartment.getPrincipal()),"principal",apartment.getPrincipal());

        List<Apartment> list = apartmentService.list(queryWrapper);
        ArrayList<Apartment> data = new ArrayList<>();
        for (Apartment o :list) {
            System.out.println(o);
            data.add(o);
        }
        ApartmentSuccess<Apartment> apartmentApartmentSuccess = new ApartmentSuccess<>(data, true);
        return new ResponseEntity<>(apartmentApartmentSuccess, HttpStatus.OK);
    }

    @GetMapping("/deptIsExist")
    public boolean deptIsExist(String department) {
//        判断该部门是否存在
        boolean apartmentByDept = apartmentService.findApartmentByDept(department);
        return apartmentByDept;
    }
    @PostMapping("/insertApartment")
    public boolean insertApartment(@RequestBody Apartment apartment) {
        boolean save = apartmentService.save(apartment);
        return save;
    }

    @PostMapping("/updateApartment")
    public boolean updateApartment(@RequestBody Apartment apartment) {
        boolean save = apartmentService.updateById(apartment);
        return save;
    }

    @GetMapping("/deleteApartment")
    public boolean deleteApartment(int id) {
        boolean apartmentByDept = apartmentService.findApartmentIsNull(id);
        return apartmentByDept;
    }
}

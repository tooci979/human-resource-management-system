package com.yinjie.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSuccess<T> {
    List<T> data;
    Boolean success;
//分页
    long total;
    Integer pageSize;
    Integer currentPage;

    String apartmentId;
    String name;
}

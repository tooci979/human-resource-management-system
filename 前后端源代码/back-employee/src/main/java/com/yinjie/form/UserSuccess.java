package com.yinjie.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSuccess<T> {
    List<T> data;
    Boolean success;
    long total;
    Integer pageSize;
    Integer currentPage;
    String name;
    String code;
    Integer status;
}

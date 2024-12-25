package com.yinjie.form;

import com.yinjie.domin.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginSuccess<T> {
    T data;
    Boolean success;
}

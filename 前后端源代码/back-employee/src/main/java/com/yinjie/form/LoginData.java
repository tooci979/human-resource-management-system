package com.yinjie.form;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class LoginData {
    private String username;
    private String nickname;
    private List<String> roles;
    private String avatar;
    private String accessToken;
    private String refreshToken;
    private Date expires;

}

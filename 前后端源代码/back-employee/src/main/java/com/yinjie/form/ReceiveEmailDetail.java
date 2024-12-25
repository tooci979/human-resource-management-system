package com.yinjie.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveEmailDetail {
    private Integer id;//邮件id
    public String textContext;
    public String username;
    public String telephone;
    public String jobName;
    public Date createdDate;



}

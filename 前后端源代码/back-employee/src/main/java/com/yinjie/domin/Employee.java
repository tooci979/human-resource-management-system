package com.yinjie.domin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *

 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String number;

    private String name;

    private String gender;

    private Integer jobId;
    private String telephone;

    private String email;
    private Date createDate;

    private Integer state;
//    一个员工对应一个职位
    @TableField(exist = false)  //exist = false  表示不是表中的字段
    private Job job;

    public Employee() {
    }

    public Employee(Integer id, String number, String name, String gender, Integer jobId, String telephone, String email, Date createDate, Integer state, Job job) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.jobId = jobId;
        this.telephone = telephone;
        this.email = email;
        this.createDate = createDate;
        this.state = state;
        this.job = job;
    }

    public Employee(Integer id, String number, String name,
                    String gender, Integer jobId,
                    String telephone, String email,
                    Date createDate, Integer state) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.jobId = jobId;
        this.telephone = telephone;
        this.email = email;
        this.createDate = createDate;
        this.state = state;
    }
}

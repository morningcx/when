package com.morningcx.when.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Role implements HasId, HasName {
    private Integer id;
    private String name;
    private Integer age;
    private Date time;

}

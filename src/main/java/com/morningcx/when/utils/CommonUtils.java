package com.morningcx.when.utils;

import com.morningcx.when.pojo.Role;
import com.morningcx.when.pojo.SuperRole;

import java.util.Date;

public class CommonUtils {

    public static Role getRole() {
        Role role = new Role();
        role.setId(1);
        role.setName("role01");
        role.setAge(18);
        role.setTime(new Date());
        return role;
    }

    public static SuperRole getSuperRole() {
        SuperRole role = new SuperRole();
        role.setId(2);
        role.setName("superRole01");
        role.setAge(22);
        role.setTime(new Date());
        return role;
    }

}

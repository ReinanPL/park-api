package com.reinan.demo_park_api.common;

import com.reinan.demo_park_api.entity.User;
import com.reinan.demo_park_api.web.dto.UserPasswordDto;

import java.util.ArrayList;
import java.util.List;

public class UserConstants {
    public static final User USER = new User("ana@gmail.com", "123456");
    public static final User USER2 = new User("bob@gmail.com", "234567");
    public static final List<User> USERS = new ArrayList<>() {
        {
            add(USER);
            add(USER2);
        }
    };

    public static final UserPasswordDto USER_PASSWORD = new UserPasswordDto("123456", "1234567", "1234567");
    public static final UserPasswordDto USER_PASSWORD_NOT_MATCH = new UserPasswordDto("123456", "12345678", "1234567");
    public static final UserPasswordDto USER_PASSWORD_OLD_NOT_MATCH = new UserPasswordDto("321496", "1234567", "1234567");

}

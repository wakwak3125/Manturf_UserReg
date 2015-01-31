package com.manturf.user_reg_test;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RyoSakaguchi on 15/01/30.
 */
public class UserReg {

    public static final String TAG = UserReg.class.getSimpleName();

    public String email;
    public String password;
    public String password_confirmation;

    public static void UserInfoConv(String eEmail, String ePassword, String ePassword_c) throws JsonProcessingException {
        UserReg userReg = new UserReg();
        Map<String,UserReg> userRegMap = new HashMap<String,UserReg>();
        userRegMap.put("user",userReg);

        userReg.email = eEmail;
        userReg.password = ePassword;
        userReg.password_confirmation = ePassword_c;

        ObjectMapper mapper = new ObjectMapper();
        String userinfo = mapper.writeValueAsString(userRegMap);
        Log.i(TAG + "JSON返還後", userinfo);
    }
}

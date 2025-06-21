package com.gymloggingapp.gymloggingapp;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.Entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

    public static UserEntity createUserA(){
    UserEntity a = new UserEntity();
    a.setName("Brady");
    List<SessionEntity> session = new ArrayList<>();
    a.setSession(session);
    return a;
    }
}

package com.gymloggingapp.gymloggingapp.util;

import com.gymloggingapp.gymloggingapp.Entities.MovementEntity;
import com.gymloggingapp.gymloggingapp.Entities.SessionEntity;
import com.gymloggingapp.gymloggingapp.Entities.SetEntity;
import com.gymloggingapp.gymloggingapp.Entities.UserEntity;

public class References {

    public static void setUserParentReference(UserEntity userEntity){
        if(userEntity.getSessions()!=null){
            for(SessionEntity session: userEntity.getSessions()){
                session.setUser(userEntity);
                setSessionParentReference(session);
            }
        }
    }

    public static void setSessionParentReference(SessionEntity sessionEntity) {
        if (sessionEntity.getMovements() != null) {
            for (MovementEntity movement : sessionEntity.getMovements()) {
                movement.setSession(sessionEntity);
                setMovementParentReference(movement);
            }
        }
    }

    public static void setMovementParentReference(MovementEntity movementEntity){
        if(movementEntity.getSets()!=null){
            for(SetEntity set: movementEntity.getSets()){
                set.setMovement(movementEntity);
            }
        }
    }
}

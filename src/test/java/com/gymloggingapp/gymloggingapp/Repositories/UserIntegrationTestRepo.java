package com.gymloggingapp.gymloggingapp.Repositories;
import static org.assertj.core.api.Assertions.assertThat;

import com.gymloggingapp.gymloggingapp.Entities.UserEntity;
import com.gymloggingapp.gymloggingapp.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class UserIntegrationTestRepo {

    private final UserRepository tester;

    @Autowired
    public UserIntegrationTestRepo(final UserRepository tester){
        this.tester = tester;
    }
    @Test
    public void testThatUserCanBeCreatedAndRecalled(){
        UserEntity user = TestDataUtil.createUserA();
        tester.save(user);
        Optional<UserEntity> result = tester.findById(user.getId());
        assertThat(result).isPresent();
    }

}

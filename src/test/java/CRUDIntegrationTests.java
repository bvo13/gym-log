import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymloggingapp.gymloggingapp.Entities.UserEntity;
import com.gymloggingapp.gymloggingapp.Repositories.RefreshTokenRepository;
import com.gymloggingapp.gymloggingapp.Repositories.UserRepository;
import com.gymloggingapp.gymloggingapp.Service.UserService;
import com.gymloggingapp.gymloggingapp.gymloggingappApplication;
import com.gymloggingapp.gymloggingapp.util.RegistrationRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = gymloggingappApplication.class)
@AutoConfigureMockMvc
public class CRUDIntegrationTests {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;






    @BeforeAll
    void clearData(){
        refreshTokenRepository.deleteAll();
       userRepository.deleteAll();
    }

    @Test
    void testThatUserIsRegistered() throws Exception{
        RegistrationRequest registrationRequest =
                new RegistrationRequest("brady","bradylvo@gmail.com","password");
        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequest))).andExpect(status().isOk());

        Optional<UserEntity> user = userRepository.findByEmail(registrationRequest.getEmail());
        assertTrue(user.isPresent(),"user not found to be created in database");
    }


}

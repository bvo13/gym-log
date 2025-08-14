import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
public class AuthenticationIntegrationTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private final TestUtil testUtil;

    public AuthenticationIntegrationTests(TestUtil testUtil) {
        this.testUtil = testUtil;
    }


}

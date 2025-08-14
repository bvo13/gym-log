import com.gymloggingapp.gymloggingapp.Entities.UserEntity;
import com.gymloggingapp.gymloggingapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TestUtil {

    private final UserService userService;

    @Autowired
    public TestUtil(UserService userService) {
        this.userService = userService;
    }


    public UserEntity createUser(String name, String email, String password){

        return userService.save(new UserEntity(name,email,password));
    }


}

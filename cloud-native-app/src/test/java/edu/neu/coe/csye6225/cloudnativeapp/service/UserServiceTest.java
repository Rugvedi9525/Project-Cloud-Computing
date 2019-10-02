package edu.neu.coe.csye6225.cloudnativeapp.service;

import edu.neu.coe.csye6225.cloudnativeapp.domain.UserAccount;
import edu.neu.coe.csye6225.cloudnativeapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

public class UserServiceTest {


    @Mock
    UserService userService;

    @Mock
    UserRepository userRepository;


    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);


    }

    @Test
    public void save() {

        UserAccount userAccount = new UserAccount();
        userAccount.setEmailAddress("aa@gmail.com");
        userAccount.setPassword("1234");
        userService.save(userAccount);

    }
}
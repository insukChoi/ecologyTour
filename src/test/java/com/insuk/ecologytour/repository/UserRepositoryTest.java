package com.insuk.ecologytour.repository;

import com.insuk.ecologytour.domain.entity.UserInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private UserInfo userInfo;

    @Before
    public void init(){
        userInfo = new UserInfo();
        userInfo.setUserId("insuk");
        userInfo.setPassword("123");

        testEntityManager.persist(userInfo);
    }

    @Test
    public void 유저_검색(){
        Optional<UserInfo> userInfoOptional = userRepository.findByUserId(userInfo.getUserId());
        UserInfo savedUser = userInfoOptional.orElse(UserInfo.EMPTY);

        assertThat(savedUser.getUserId(), is(userInfo.getUserId()));
        assertThat(savedUser.getPassword(), is(userInfo.getPassword()));
    }


}
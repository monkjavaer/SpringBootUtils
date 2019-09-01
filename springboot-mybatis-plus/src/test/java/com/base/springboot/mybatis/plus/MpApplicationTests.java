package com.base.springboot.mybatis.plus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.base.springboot.mybatis.plus.mappers.UserMapper;
import com.base.springboot.mybatis.plus.po.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MpApplicationTests {


    @Autowired
    private UserMapper userMapper;

 /*   @Autowired
    private UsersMapper usersMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void contextLoads() {
    }



    @Test
    public void testInsertUsers(){
        log.info("===test insert====");
        Student.StudentBuilder userbuild = Student.builder();
        Student user = userbuild
                .username("test")
                .password("123")
                .email("121313@uouo.com")
                .build();
        int res = usersMapper.insert(user);
        log.info("res :{}",res);
    }*/


    @Test
    public void testInsert(){
        log.info("===test insert====");
        User.UserBuilder userbuild = User.builder();
        User user = userbuild
                .name("wakaka")
                .age(23)
                .email("121313@uouo.com")
                .build();
        int res = userMapper.insert(user);
        log.info("res :{}",res);
    }

    @Test
    public void testQueryByWarp(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper
                .like("name","雨")
                .lt("age",51);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

}

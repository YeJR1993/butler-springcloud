package com.tuxiaoer.shanghai;

import com.tuxiaoer.shanghai.modules.system.dao.UserDao;
import com.tuxiaoer.shanghai.modules.system.entity.User;
import com.tuxiaoer.shanghai.modules.system.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ：YeJR
 * @version : 1.0
 * @date ：2019/4/9 14:06
 * @description：测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemServerApplicationTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void test() {
        List<User> userList = userDao.getUserList(new User());
        System.out.println(userList.size());
    }

}

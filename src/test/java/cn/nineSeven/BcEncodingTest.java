package cn.nineSeven;

import cn.nineSeven.mapper.MenuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class BcEncodingTest {
    @Autowired
    MenuMapper menuMapper;
    @Test
    public void bcEncoding(){
        System.out.println(menuMapper.selectMenusByUserId(2L));
    }
}

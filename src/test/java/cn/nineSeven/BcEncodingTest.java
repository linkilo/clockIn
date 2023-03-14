package cn.nineSeven;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class BcEncodingTest {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    public void bcEncoding(){
        System.out.println(passwordEncoder.encode("asdfghjkl123"));
    }
}

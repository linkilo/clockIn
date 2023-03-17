package cn.nineSeven;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.nineSeven.mapper")
public class ClockApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClockApplication.class, args);
    }
}

package gxnu.newkids;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
@MapperScan("gxnu.newkids.dao")
public class NewkidsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewkidsApplication.class, args);
    }

}

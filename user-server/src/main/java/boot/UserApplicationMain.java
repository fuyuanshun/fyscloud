package boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("boot.wx.persistence")
@EnableDiscoveryClient
public class UserApplicationMain extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(UserApplicationMain.class, args);
    }
}

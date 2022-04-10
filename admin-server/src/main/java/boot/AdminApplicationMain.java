package boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("boot.wx.persistence")
@EnableFeignClients
public class AdminApplicationMain extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplicationMain.class, args);
    }
}

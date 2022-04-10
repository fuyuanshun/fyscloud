package boot.wx.config;

import boot.wx.entity.User;
import boot.wx.properties.UserInfoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UserInfoConfig.class)
public class UserInfoAutoConfig {
    @Autowired
    private UserInfoConfig userInfoConfig;

    @Bean
    public User userAuto(){
        return new User(userInfoConfig.getId(), userInfoConfig.getUsername(), null, null);
    }
}

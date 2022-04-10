package boot.wx.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "user")
@RefreshScope
public class UserInfoConfig {
    private Long id = 1L;
    private String username = "fys";
    private List<String> locations;
    private Integer sex = 1;
    private Integer age = 18;
}

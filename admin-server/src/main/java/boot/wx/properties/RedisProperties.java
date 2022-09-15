package boot.wx.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author fys
 * @date 2022/9/16
 * @description
 */
@RefreshScope
@Getter
@Setter
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {
    private String host = "127.0.0.1";
    private Integer database = 1;
    private Integer port = 6379;
}

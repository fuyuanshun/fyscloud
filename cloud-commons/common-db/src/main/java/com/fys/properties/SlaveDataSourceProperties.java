package com.fys.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "db.slave")
public class SlaveDataSourceProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}

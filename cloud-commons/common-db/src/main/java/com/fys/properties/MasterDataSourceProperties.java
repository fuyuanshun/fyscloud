package com.fys.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "db.master")
public class MasterDataSourceProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}

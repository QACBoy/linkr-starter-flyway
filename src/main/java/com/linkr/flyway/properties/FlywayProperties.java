package com.linkr.flyway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描述: <br>
 * < TODO >
 *
 * @author linkr
 * @create 2019-11-03
 * @since TODO
 */
@ConfigurationProperties(prefix = "flywaydb")
public class FlywayProperties {

    private String url;

    private String username;

    private String password;

    private Boolean startEnable = true;

    private Boolean baselineOnMigrate = true;

    private Boolean cleanDisabled = true;

    private Boolean enable = false;

    private List<String> locations = new ArrayList<>(
            Collections.singletonList("classpath:db/migration"));

    public FlywayProperties() {
    }

    public FlywayProperties(String url, String username, String password, Boolean startEnable, Boolean baselineOnMigrate, Boolean cleanDisabled, Boolean enable, List<String> locations) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.startEnable = startEnable;
        this.baselineOnMigrate = baselineOnMigrate;
        this.cleanDisabled = cleanDisabled;
        this.enable = enable;
        this.locations = locations;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStartEnable() {
        return startEnable;
    }

    public void setStartEnable(Boolean startEnable) {
        this.startEnable = startEnable;
    }

    public Boolean getBaselineOnMigrate() {
        return baselineOnMigrate;
    }

    public void setBaselineOnMigrate(Boolean baselineOnMigrate) {
        this.baselineOnMigrate = baselineOnMigrate;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public Boolean getCleanDisabled() {
        return cleanDisabled;
    }

    public void setCleanDisabled(Boolean cleanDisabled) {
        this.cleanDisabled = cleanDisabled;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}

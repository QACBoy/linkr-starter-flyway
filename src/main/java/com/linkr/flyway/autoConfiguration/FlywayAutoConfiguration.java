package com.linkr.flyway.autoConfiguration;

import com.linkr.flyway.properties.FlywayProperties;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述: <br>
 * ConditionalOnWebApplication： web 应用生效
 * ConditionalOnClass：
 *
 * @author linkr
 * @create 2019-11-03
 * @since TODO
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties({FlywayProperties.class})
public class FlywayAutoConfiguration {

    @Autowired
    private FlywayProperties properties;

    /**
     * 当容器中没有指定 Bean 的情况下，自动配置 flyway 类
     * 配置默认执行 repair, migrate 两个方法
     * 每次程序启动的时候都会执行 repair 和 migrate 的方法，执行最新的数据脚本
     * 数据脚本默认的存放位置： src\main\resources\db\migration
     * 在 migration 文件夹下，根据每个版本的版本号新建一个文件夹用于存放当前版本的数据库脚本，命名规则：V${version}，eg： V1_0_0
     * SQL 文件命名规则： V${version}_${time}__${name}.sql, eg: V1_0_0_20191008150600__init_usertest.sql
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(Flyway.class)
    public Flyway flyway() {
        if (!properties.getEnable()) {
            return new Flyway(new ClassicConfiguration());
        }
        ClassicConfiguration classicConfiguration = new ClassicConfiguration();

        classicConfiguration.setDataSource(properties.getUrl(), properties.getUsername(), properties.getPassword());
        classicConfiguration.setBaselineOnMigrate(properties.getBaselineOnMigrate());
        classicConfiguration.setCleanDisabled(properties.getCleanDisabled());
        List<Location> locationList = new ArrayList<>();
        List<String> locations = properties.getLocations();
        locations.forEach(locationStr -> {
            locationList.add(new Location(locationStr));
        });
        classicConfiguration.setLocations(locationList.toArray(new Location[locationList.size()]));

        Flyway flyway = new Flyway(classicConfiguration);
        System.out.println(" ****** locations: " + properties.getLocations() + " ****** ");
        System.out.println(" ****** flywayStartEnable: " + properties.getStartEnable() + " ****** ");
        if (properties.getStartEnable()) {
            System.out.println(" ****** repair ****** ");
            flyway.repair();
            System.out.println(" ****** migrate ****** ");
            flyway.migrate();
        }
        return flyway;
    }
}

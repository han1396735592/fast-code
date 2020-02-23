package cn.qqhxj.fastcode.starter;

import cn.qqhxj.fastcode.core.config.FastCodeConfig;
import cn.qqhxj.fastcode.core.config.FastCodeHelper;
import cn.qqhxj.fastcode.core.db.DbDao;
import cn.qqhxj.fastcode.core.db.MysqlDbDao;
import cn.qqhxj.fastcode.core.service.FreemarkerService;
import cn.qqhxj.fastcode.core.service.LocalFreemarkerServiceImpl;
import cn.qqhxj.fastcode.core.util.FastCodeTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;

@ComponentScan
@Slf4j
@ConditionalOnClass(DataSource.class)
@Configuration
@EnableConfigurationProperties(FastCodeProperties.class)
public class FastCodeAutoConfiguration {
    @Bean
    FastCodeHelper fastCodeHelper(FastCodeProperties fastCodeProperties) {
        FastCodeConfig fastCodeConfig = new FastCodeConfig();
        fastCodeConfig.setConfigPath(fastCodeProperties.getConfigPath());
        fastCodeConfig.setTableDataPath(fastCodeProperties.getTableDataPath());
        FastCodeHelper fastCodeHelper = new FastCodeHelper();
        fastCodeHelper.setFastCodeConfig(fastCodeConfig);
        log.info("init fastCodeConfigInto  configPath = {} ,tableDataPath = {} ", fastCodeHelper.getConfigPath(), fastCodeHelper.getTableDataPath());
        return fastCodeHelper;
    }

    @Bean
    @ConditionalOnClass(Driver.class)
    public DbDao mysqlDbDao(FastCodeTool tool, final DataSource dataSource, FastCodeHelper helper) {
        MysqlDbDao mysqlDbDao = new MysqlDbDao(){
            public Connection getConnection() {
                try {
                    return dataSource.getConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        mysqlDbDao.setTool(tool);
        mysqlDbDao.setFastCodeHelper(helper);
        log.info("init dbDao = {} ", mysqlDbDao);
        return mysqlDbDao;
    }

    @Bean
    public FastCodeTool tool() {
        FastCodeTool fastCodeTool = new FastCodeTool();
        log.info("init fastCodeTool = {}", fastCodeTool);
        return fastCodeTool;
    }

    @Bean
    public FreemarkerService freemarkerService(DbDao dbdao, FastCodeTool tool, FastCodeHelper helper) {
        LocalFreemarkerServiceImpl localFreemarkerService = new LocalFreemarkerServiceImpl();
        localFreemarkerService.setDbDao(dbdao);
        localFreemarkerService.setTool(tool);
        localFreemarkerService.setFastCodeHelper(helper);
        log.info("init FreemarkerService = {}", localFreemarkerService);
        return localFreemarkerService;
    }

}

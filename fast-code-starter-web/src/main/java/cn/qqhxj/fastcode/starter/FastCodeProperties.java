package cn.qqhxj.fastcode.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "fast-code")
public class FastCodeProperties {
    private String configPath = "classPath:fast-code";
    private String tableDataPath = "table";
}

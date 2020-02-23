package cn.qqhxj.fastcode.core.vo;

import lombok.Data;

import java.util.Map;

@Data
public class TemplateConfig {
    private TableConfig tableConfig;

    private String template;

    private boolean save;

    private String table;

    private String savePath;

    private Map<String, Object> metaData;

}

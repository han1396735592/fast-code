package cn.qqhxj.fastcode.core.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class TableConfig {

    private ProjectConfig projectConfig;

    private List<String> templateList;

    private List<TemplateConfig> templateConfigList;

    private Map<String, Object> metaData;

    private String table;

    private boolean save;

    private String savePath;

    public List<TemplateConfig> getTemplateConfigList() {
        return templateList.stream().map(item -> {
            TemplateConfig config = new TemplateConfig();
            config.setTable(table);
            config.setTemplate(item);
            config.setSave(save);
            config.setSavePath(savePath);
            config.setMetaData(metaData);
            config.setTableConfig(this);
            return config;
        }).collect(Collectors.toList());
    }

}

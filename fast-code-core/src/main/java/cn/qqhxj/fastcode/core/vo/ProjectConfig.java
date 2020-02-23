package cn.qqhxj.fastcode.core.vo;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProjectConfig {
    private List<String> tableList;

    private List<String> templateList;

    private List<TableConfig> tableConfigList;
    private boolean save;

    private String savePath;

    public List<TableConfig> getTableConfigList() {
        return tableList.stream().map(item -> {
            TableConfig config = new TableConfig();
            config.setTable(item);
            config.setSave(save);
            config.setSavePath(savePath);
            config.setProjectConfig(this);
            config.setTemplateList(templateList);
            return config;
        }).collect(Collectors.toList());
    }
}

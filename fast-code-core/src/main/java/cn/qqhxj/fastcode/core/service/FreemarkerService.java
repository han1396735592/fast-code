package cn.qqhxj.fastcode.core.service;

import cn.qqhxj.fastcode.core.vo.ProjectConfig;
import cn.qqhxj.fastcode.core.vo.TableConfig;
import cn.qqhxj.fastcode.core.vo.TemplateConfig;

import java.util.Map;

public interface FreemarkerService {


    void generate(TemplateConfig config);


    default void generate(TableConfig config) {
        config.getTemplateConfigList().forEach(this::generate);
    }

    default void generate(ProjectConfig config) {
        config.getTableConfigList().forEach(this::generate);
    }

    String preview(TemplateConfig config);


    Map<String, Object> initDataMap();

    void processMetaData(TemplateConfig config);


}

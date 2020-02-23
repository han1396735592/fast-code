package cn.qqhxj.fastcode.core.service;

import cn.qqhxj.fastcode.core.config.FastCodeHelper;
import cn.qqhxj.fastcode.core.db.DbDao;
import cn.qqhxj.fastcode.core.model.TableInfo;
import cn.qqhxj.fastcode.core.util.FastCodeTool;
import cn.qqhxj.fastcode.core.vo.TemplateConfig;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class LocalFreemarkerServiceImpl implements FreemarkerService {
    private FastCodeHelper fastCodeHelper;
    private DbDao dbDao;
    private FastCodeTool tool;

    public void setFastCodeHelper(FastCodeHelper fastCodeHelper) {
        this.fastCodeHelper = fastCodeHelper;
    }

    public void setDbDao(DbDao dbDao) {
        this.dbDao = dbDao;
    }

    public void setTool(FastCodeTool tool) {
        this.tool = tool;
    }

    @Override
    public Map<String, Object> initDataMap() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put(FastCodeTool.VARIABLE_NAME, tool);
        dataMap.put("dbInfo", dbDao.getDbInfo());
        return dataMap;
    }

    @Override
    public void processMetaData(TemplateConfig config) {
        String s = tool.readFile(fastCodeHelper.getConfigPath() + "/metadata.json");
        if (s != null && !"".equals(s)) {
            HashMap hashMap = JSONObject.parseObject(s, HashMap.class);
            config.setMetaData(hashMap);
        }
    }

    @Override
    public void generate(TemplateConfig config) {
        String preview = preview(config);
        if (config.isSave()) {
            tool.writeFile(config.getSavePath(), preview);
        }
    }

    @Override
    public String preview(TemplateConfig config) {
        String rootPath = fastCodeHelper.getConfigPath();
        TableInfo tableInfo = dbDao.getTableInfo(config.getTable());
        processMetaData(config);
        Configuration defaultConfiguration = new Configuration(Configuration.VERSION_2_3_0);
        defaultConfiguration.clearTemplateCache();
        StringWriter writer = new StringWriter();
        try {
            Map<String, Object> dataMap = initDataMap();
            dataMap.put("tableInfo", tableInfo);
            dataMap.put("config", config);
            defaultConfiguration.setDirectoryForTemplateLoading(new File(rootPath));
            defaultConfiguration.getTemplate("/templates/" + config.getTemplate() + ".ftl").process(dataMap, writer);
            String data = writer.toString();
            System.out.println("===========================================================================");
            System.out.println(String.format("table = [%s],template = [%s]",
                    config.getTable(),
                    config.getTemplate()
            ));
            if (config.isSave()) {
                System.out.println(String.format("savePath = [%s]", config.getSavePath()));
            }
            System.out.println("\n===========================================================================");
            System.out.println(data);
            System.out.println("===========================================================================");
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

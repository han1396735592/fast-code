package cn.qqhxj.fastcode.core.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DbInfo {
    private String type;
    private String sql;
    private List<String> tableNameList;
    private Map<String, TableInfo> tableMap;
}

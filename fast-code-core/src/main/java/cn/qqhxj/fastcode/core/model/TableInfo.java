package cn.qqhxj.fastcode.core.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class TableInfo {
    private String name;
    private String comment;
    List<ColumnInfo> columnInfoList;
    List<ColumnInfo> pkColumn;

    private Map<String, Object> ext = new HashMap<>();

    public List<ColumnInfo> getPkColumn() {
        return columnInfoList.stream().filter(item -> {
            return "PRI".equals(item.getKeyType());
        }).collect(Collectors.toList());
    }

    public List<ColumnInfo> getOtherColumn() {
        return columnInfoList.stream().filter(item -> {
            return !"PRI".equals(item.getKeyType());
        }).collect(Collectors.toList());
    }

}

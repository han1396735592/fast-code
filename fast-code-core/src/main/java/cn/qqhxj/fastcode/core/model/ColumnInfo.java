package cn.qqhxj.fastcode.core.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ColumnInfo {
    private String name;

    private String type;

    private String javaType;

    private String keyType;

    private String comment;

    private Map<String, Object> ext = new HashMap<>();


}

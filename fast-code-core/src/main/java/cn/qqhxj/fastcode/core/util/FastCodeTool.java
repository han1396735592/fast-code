package cn.qqhxj.fastcode.core.util;

import cn.qqhxj.fastcode.core.model.TableInfo;
import cn.qqhxj.fastcode.core.model.TypeMapper;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.*;


public class FastCodeTool {
    public final static String VARIABLE_NAME = "tool";

    public String toJson(Object arguments) {
        return JSONObject.toJSONString(arguments);
    }

    public void matchType(TableInfo tableInfo, List<TypeMapper> mapperList) {
        tableInfo.getColumnInfoList().forEach(info -> {
            String type = info.getType();
            info.setJavaType("java.lang.Object");
            Iterator<TypeMapper> iterator = mapperList.iterator();
            while (iterator.hasNext()) {
                TypeMapper next = iterator.next();
                if (type.matches(next.getColumnType())) {
                    info.setJavaType(next.getJavaType());
                    break;
                }
            }
        });
    }

    public boolean stringIsEmpty(String str) {
        return str == null || "".equals(str);
    }


    public String readFile(String path) {
        File metaDataFile = new File(path);
        if (metaDataFile.exists()) {
            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(new FileReader(metaDataFile));
                StringBuilder sb = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    sb.append(line + System.getProperty("line.separator"));
                    line = bufferedReader.readLine();
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void writeFile(String path, String str) {
        File file = new File(path);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public JSONObject parseJson(String json) {
        return JSONObject.parseObject(json);
    }

    public String toClassName(String type) {
        int i = type.lastIndexOf("_");
        if (i != -1) {
            String[] split = type.split("_");
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < split.length; j++) {
                sb.append(split[j].substring(0, 1).toUpperCase());
                sb.append(split[j].substring(1));
            }
            return sb.toString();
        } else {
            return type.substring(0, 1).toUpperCase() + type.substring(1);
        }
    }

    public HashSet newHashSet() {
        return new HashSet();
    }

    public String toFieldName(String type) {
        String[] split = type.split("_");
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < split.length; j++) {
            if (j != 0) {
                sb.append(split[j].substring(0, 1).toUpperCase());
            } else {
                sb.append(split[j].substring(0, 1));
            }
            sb.append(split[j].substring(1));
        }
        return sb.toString();
    }

    public HashMap newHashMap() {
        return new LinkedHashMap();
    }

}

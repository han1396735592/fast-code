package cn.qqhxj.fastcode.core.db;

import cn.qqhxj.fastcode.core.config.FastCodeHelper;
import cn.qqhxj.fastcode.core.model.ColumnInfo;
import cn.qqhxj.fastcode.core.model.DbInfo;
import cn.qqhxj.fastcode.core.model.TableInfo;
import cn.qqhxj.fastcode.core.model.TypeMapper;
import cn.qqhxj.fastcode.core.util.FastCodeTool;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class MysqlDbDao implements DbDao {

    private FastCodeTool tool;

    private FastCodeHelper fastCodeHelper;

    public void setTool(FastCodeTool tool) {
        this.tool = tool;
    }

    public void setFastCodeHelper(FastCodeHelper fastCodeHelper) {
        this.fastCodeHelper = fastCodeHelper;
    }

    @Override
    public List<String> getTableList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Connection connection = getConnection();
        try {
            ResultSet show_tables = connection.createStatement().executeQuery("show tables");
            while (show_tables.next()) {
                arrayList.add(show_tables.getString(1));
            }
            show_tables.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayList;

    }

    @Override
    public DbInfo getDbInfo() {
        List<String> tableList = getTableList();
        DbInfo dbInfo = new DbInfo();
        dbInfo.setTableNameList(tableList);
        Map<String, TableInfo> tableInfoMap = tableList.stream().collect(Collectors.toMap(String::toString, this::getTableInfo));
        dbInfo.setTableMap(tableInfoMap);
        return dbInfo;
    }

    @Override
    public TableInfo getTableInfo(String name) {
        String path = fastCodeHelper.getTableDataPath();
        TableInfo tableInfo = new TableInfo();
        String extJson = tool.readFile(path + File.separator + name + ".json");
        JSONObject extHashMap = null;
        JSONObject columnExt = null;
        if (!tool.stringIsEmpty(extJson)) {
            extHashMap = JSONObject.parseObject(extJson);
            if (extHashMap != null) {
                columnExt = extHashMap.getJSONObject("columns");
                tableInfo.setExt(extHashMap);
            }
        } else {
            extHashMap = new JSONObject();
            columnExt = new JSONObject();
            extHashMap.put("columns", columnExt);
        }
        tableInfo.setName(name);
        JSONObject finalColumnExt = columnExt;
        List<ColumnInfo> columnInfoList = new ArrayList<>();

        try {
            Connection connection = getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("select * from information_schema.COLUMNS where TABLE_SCHEMA = (select database()) and TABLE_NAME ='" + name + "'");
            while (resultSet.next()) {
                ColumnInfo fieldInfo = new ColumnInfo();
                fieldInfo.setName(resultSet.getString("COLUMN_NAME"));
                fieldInfo.setKeyType(resultSet.getString("COLUMN_KEY"));
                fieldInfo.setType(resultSet.getString("COLUMN_TYPE"));
                fieldInfo.setComment(resultSet.getString("COLUMN_COMMENT"));
                if (tool.stringIsEmpty(extJson)) {
                    JSONObject jsonObject = new JSONObject();
                    fieldInfo.setExt(jsonObject);
                    finalColumnExt.put(fieldInfo.getName(), jsonObject);
                } else {
                    fieldInfo.setExt(finalColumnExt.getJSONObject(fieldInfo.getName()));
                }
                columnInfoList.add(fieldInfo);
            }
            resultSet.close();
            if (connection!=null){
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableInfo.setColumnInfoList(columnInfoList);
        if (tool.stringIsEmpty(extJson)) {
            tableInfo.setExt(extHashMap);
            tool.writeFile(path + File.separator + name + ".json", extHashMap.toJSONString());
        }
        String typeMapperStr = tool.readFile(fastCodeHelper.getConfigPath() + "/typeMapper.json");
        if (!tool.stringIsEmpty(typeMapperStr)) {
            List<TypeMapper> typeMappers = JSONObject.parseArray(typeMapperStr, TypeMapper.class);
            tool.matchType(tableInfo, typeMappers);
        }
        return tableInfo;
    }

}

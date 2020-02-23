package cn.qqhxj.fastcode.core.db;

import cn.qqhxj.fastcode.core.model.DbInfo;
import cn.qqhxj.fastcode.core.model.TableInfo;

import java.sql.Connection;
import java.util.List;

public interface DbDao {

    Connection getConnection();

    DbInfo getDbInfo();

    List<String> getTableList();

    TableInfo getTableInfo(String name);
}

package cn.qqhxj.fastcode.core.db;

import cn.qqhxj.fastcode.core.model.DbInfo;
import cn.qqhxj.fastcode.core.model.TableInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DbDao {

    Connection getConnection();

    default void releaseConnection(Connection connection){
        if (connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    DbInfo getDbInfo();

    List<String> getTableList();

    TableInfo getTableInfo(String name);
}

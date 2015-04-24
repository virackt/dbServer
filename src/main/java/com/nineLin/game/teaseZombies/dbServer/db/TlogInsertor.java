package com.nineLin.game.teaseZombies.dbServer.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by vic on 15-4-22.
 */
public class TlogInsertor {

    private static final DataSource dataSource = DBUtil.getDataSource();

    public static void execute(List<String> sqlList) {
        if (sqlList == null || sqlList.size() == 0) {
            return;
        }
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = dataSource.getConnection();
            String sql = sqlList.get(0);
            connection.setAutoCommit(false);
            pst = connection.prepareStatement(sql);
            for (String sqlTmp : sqlList) {
                pst.addBatch(sqlTmp);
            }
            pst.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println(connection.isClosed());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void execute(String sql) {
        Connection connection = null;
        PreparedStatement pst = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            pst = connection.prepareStatement(sql);
            pst.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println(connection.isClosed());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

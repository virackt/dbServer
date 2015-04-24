package com.nineLin.game.teaseZombies.dbServer.masterWorker;

import com.nineLin.game.teaseZombies.dbServer.net.message.LogMessage;
import com.nineLin.game.teaseZombies.dbServer.util.Constants;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by vic on 15-4-23.
 */
public class TlogSqlWorker extends Worker {
    private int batchNum;
    private int nullNum;
    private Connection connection;
    private Statement statement;

    public void setConnection(Connection connection) {
        this.connection = connection;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object handle(Object obj) {
        try {
            if (obj == null && batchNum != 0) {
                nullNum++;
                if (nullNum >= Constants.SQL_NULL_NUM) {
                    statement.executeBatch();
                    connection.commit();
                    nullNum = batchNum = 0;
                }
            } else if (obj != null) {
                LogMessage msg = (LogMessage) obj;
//                System.out.println(workerId + " -- " + msg.getUid() + " -- " + msg.getSql());
//                if ((msg.getUid() % Constants.WORKER_NUM == workerId)) {
                batchNum++;
                statement.addBatch(msg.getSql());
                if (batchNum >= Constants.SQL_BATCH_NUM) {
                    statement.executeBatch();
                    connection.commit();
                    batchNum = 0;
                }
//                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean shutdown() {
        super.shutdown();
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}

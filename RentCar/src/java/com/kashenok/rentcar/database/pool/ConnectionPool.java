package com.kashenok.rentcar.database.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/**
 * The Class ConnectionPool. Contains a initialize connections to the database.
 */
public class ConnectionPool {

    public static final Logger LOG = Logger.getLogger(ConnectionPool.class);
    private BlockingQueue<Connection> pool;
    private static ConnectionPool instance;
    public static ResourceBundle dbConnectionConfig = ResourceBundle.getBundle("resources.dbconnection");
    private int size = Integer.parseInt(dbConnectionConfig.getString("poolSize"));
    public static AtomicBoolean isExist = new AtomicBoolean(false);
    public static AtomicBoolean receiveConnection = new AtomicBoolean(true);
    public static Lock lock = new ReentrantLock();

    /**
     * The constructor initialize ConnectionPool, registers jdbc driver and
     * creates connection to the database.
     *
     */
    private ConnectionPool() {
        pool = new ArrayBlockingQueue<>(size);
        Properties properties = new Properties();
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            properties.setProperty("user", dbConnectionConfig.getString("user"));
            properties.setProperty("password", dbConnectionConfig.getString("password"));
            properties.setProperty("useUnicode", dbConnectionConfig.getString("unicode"));
            properties.setProperty("characterEncoding", dbConnectionConfig.getString("encoding"));
            for (int i = 0; i < size; i++) {
                Connection connection = DriverManager.getConnection(dbConnectionConfig.getString("url"), properties);
                pool.offer(connection);
            }
            int counter = 0;
            if (pool.size() < size) {
                do {
                    Connection connection = DriverManager.getConnection(dbConnectionConfig.getString("url"), properties);
                    pool.offer(connection);
                    counter++;
                } while (pool.size() >= size || counter >= size * 2);
            }
        } catch (SQLException ex) {
            LOG.error("Exception while initialising connection pool:" + ex);
        }
    }

    /**
     * Gets the single instance of the ConnectionPool.
     *
     * @return instance that is object of the ConnectionPool
     */
    public static ConnectionPool getInstance() {
        if (!isExist.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isExist.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * The method getConnection. Gets the connection.
     *
     * @return connection
     */
    public Connection getConnection() {
        Connection connection = null;
        if (receiveConnection.get()) {
            try {
                connection = pool.take();
            } catch (InterruptedException ex) {
                LOG.error("Impossible to get Connection: ", ex);
            }
        }
        return connection;
    }

    /**
     * The method returnConnection. Puts connection to the pool.
     *
     * @param connection is the connection
     */
    public void returnConnection(Connection connection) {
        if (connection != null) {
            try {
                pool.put(connection);
            } catch (InterruptedException ex) {
                LOG.error("Impossible to return connection back to ConnectionPool: ", ex);
            }
        }
    }

    /**
     * The method clearPool. Closes all connections and cleans up pool.
     */
    public void clearPool() {
        receiveConnection.set(false);
        Iterator<Connection> poolIterator = pool.iterator();
        if (instance != null) {
            try {
                Thread.sleep(100);
                while (poolIterator.hasNext()) {

                    Connection connection = poolIterator.next();
                    connection.close();
                    poolIterator.remove();
                }
            } catch (SQLException ex) {
                LOG.error("Impossible to clear ConnectionPool: ", ex);
            } catch (InterruptedException ex) {
                LOG.error("InterruptedException", ex);
            }
        }
    }

}

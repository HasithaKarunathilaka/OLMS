package lk.karunathilaka.OLMS.db;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionPool {
    private static DBConnectionPool instance;
    private final BasicDataSource basicDataSource;

    private DBConnectionPool()
    {

        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");     //this is the driver class for the mysql
        basicDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/olms");         //should be of the form jdbc:mysql://localhost:3306/<db name>
        basicDataSource.setUsername("root");                                //db username
        basicDataSource.setPassword("root");                            //db password
        basicDataSource.setMinIdle(2);
        basicDataSource.setMaxIdle(10000);
        basicDataSource.setMaxTotal(20000);
    }

    public static DBConnectionPool getInstance()
    {
        if(instance == null)
        {
            instance = new DBConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException
    {
        return this.basicDataSource.getConnection();
    }

    public void close(AutoCloseable closeable)
    {
        try
        {
            if(closeable != null)
            {
                closeable.close();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

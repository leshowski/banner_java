package cl.leshowski.banner.utilities;
import java.sql.Connection;
import java.sql.SQLException;
 
import oracle.jdbc.pool.OracleDataSource;

public class DataHandler {
	
    public DataHandler() {
    }
    
    public String jdbcUrl = null;
    public String userid = null;
    public String password = null; 
    public Connection conn;
    
    public void getDBConnection() throws SQLException{
        OracleDataSource ds;
        ds = new OracleDataSource();
        ds.setURL(jdbcUrl);
        conn=ds.getConnection(userid,password);
        
    }
}
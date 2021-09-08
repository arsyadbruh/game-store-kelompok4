package core;
import java.sql.*;


/**
 * class {@code ConnectDB} for connect to MySQL database
 * 
 * <p> the username for default connection is {@code root}
 * with no password, default port is {@code 3306}
 * default name database is {@code gamestore}
 * default ipadders is {@code localhost}
 * <p> {@code useSSL = false}
 */
public class ConnectDB {
    public static String USERNAME = "root";
    public static String PASSWORD = "";
    public static int PORT = 3306;
    public static String DATABASE = "gamestore";
    public static String IPADDRESS = "localhost";
    public static String DRIVER = "jdbc:mysql://"+IPADDRESS+":"+PORT+"/"+DATABASE+"?useSSL=false";

    public static final Connection connect(){
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DRIVER, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("Connection Failed! "+e.getMessage());
        }
        return con;
    }
}

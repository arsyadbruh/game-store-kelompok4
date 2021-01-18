package core;
import java.sql.*;

public class ConnectDB {
    public static String USERNAME = "root";
    public static String PASSWORD = "";
    public static int PORT = 3306;
    public static String DATABASE = "gamestore";
    public static String IPADDRESS = "localhost";
    public static String DRIVER = "jdbc:mysql://"+IPADDRESS+":"+PORT+"/"+DATABASE;

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


import java.sql.*;


public class PostgresqlDocker_Connections {

private static final String DB_DRIVER = "org.postgresql.Driver";
private static final String DB_CONNECTION="jdbc:postgresql://192.168.99.100:5432/pgdb1";
private static final String DB_USER = "pguser1";
private static final String DB_PASSWORD = "welcome1";

public static void main(String[] argc) throws SQLException {
	
	Statement stmt = null;

        try 
        {
        Class.forName(DB_DRIVER);
        }
        catch (ClassNotFoundException e) 
        {
        System.err.println("Please add PostgreSQL JDBC Driver in your Classpath ");
        System.err.println(e.getMessage());
        return;
        }

        System.out.println("PostgreSQL JDBC Driver Registered!");
        Connection connection = null;
        try 
        {
        connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        
        System.out.println("...Deleting already created Tables...");
        stmt = connection.createStatement();
        String sql1 = "DROP TABLE Registration ";
        stmt.executeUpdate(sql1);
        
        System.out.println("...Creating Registartion Table...");
        String sql = "CREATE TABLE REGISTRATION " +
                     "(id INTEGER not NULL, " +
                     " first VARCHAR(255), " + 
                     " last VARCHAR(255), " + 
                     " age INTEGER, " + 
                     " PRIMARY KEY ( id ))"; 
        stmt.executeUpdate(sql);
        
        System.out.println("...Inserting Values in created tables created Tables...");
               sql = "INSERT INTO Registration " +
                "VALUES (100, 'Zara', 'Ali', 18)";
        stmt.executeUpdate(sql);
        
               sql = "INSERT INTO Registration " +
                "VALUES (101, 'Mahnaz', 'Fatma', 25)";
        stmt.executeUpdate(sql);
        
               sql = "INSERT INTO Registration " +
                "VALUES (102, 'Zaid', 'Khan', 30)";
        stmt.executeUpdate(sql);
        
               sql = "INSERT INTO Registration " +
                "VALUES(103, 'Sumit', 'Mittal', 28)";
        stmt.executeUpdate(sql);
        
        System.out.println("...Updating rows into a tables...");
               sql = "UPDATE Registration " +
                "SET age = 30 WHERE id in (100, 101)";
        stmt.executeUpdate(sql);
        
        System.out.println("...Deleting a particular records...");
               sql = "DELETE FROM Registration " +
                "WHERE id = 101";
        stmt.executeUpdate(sql);
        
        }
        catch (SQLException e) 
        {
        System.err.println("Connection Failed, Check console");
        System.err.println(e.getMessage());
        return;
        }
        
      finally{
            //finally block used to close resources
            try{
               if(stmt!=null)
                  connection.close();
            }catch(SQLException se){
            }// do nothing
            try{
               if(connection!=null)
                  connection.close();
            }catch(SQLException se){
               se.printStackTrace();
            }//end finally try
         }//end try
}
}//end JDBCExample
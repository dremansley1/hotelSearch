import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class findHotels {

    public ResultSet runQuery(String location) {
    	
    	ResultSet results = null;
    	
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/NXl3AN4huT", "NXl3AN4huT", "Dltbdu2lBZ");
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM hotels WHERE hotelLocation = ?");
            stmt.setString(1, location);
            results = stmt.executeQuery(); 
           
        } 
        catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }
}
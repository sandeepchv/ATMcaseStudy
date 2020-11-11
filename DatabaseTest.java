import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseTest {
	private static Connection con;
	private static boolean hasData = false;
	
	public ResultSet DisplayUsers() throws ClassNotFoundException, SQLException {
		if(con == null)
			getConnection();
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT fName, lName FROM user");
		return res;
	}
	
	private void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:TestDatabase.db");
		Initialise();
	}
	
	private void Initialise() throws SQLException{
		if(!hasData) {
			hasData = true;
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
			if( !res.next() ) {
				System.out.println("Building the table with pre-populated values");
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE user(id integer, "
						+"fName varchar(60), "+"lName varchar(60), "
						+"primary key(id));");
				PreparedStatement prep = con.prepareStatement("INSERT INTO user values(?,?,?);");
				prep.setString(2, "12345");
				prep.setString(3, "54321");
				prep.execute();
				
				PreparedStatement prep2 = con.prepareStatement("INSERT INTO user values(?,?,?);");
				prep2.setString(2, "56789");
				prep2.setString(3, "98765");
				prep2.execute();
			}
			
		}
	}
}

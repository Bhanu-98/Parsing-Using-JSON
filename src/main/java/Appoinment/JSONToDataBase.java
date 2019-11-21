package Appoinment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONToDataBase {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mavoix?"+"user=bhanu&password=bhanu");
				System.out.println("Connected");
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(new FileReader("F:/output.json"));
				JSONArray array = (JSONArray) obj.get("Appointments");
				String sql = "insert into apn values (?,?,?,?,?)";
				PreparedStatement p = conn.prepareStatement(sql);
			for(Object object : array) {
				JSONObject record = (JSONObject) object;
				String apn_date = (String) record.get("apn_date");
				String apn_id = (String) record.get("apn_id");
				String dr_name = (String) record.get("dr_name");
				String pt_name = (String) record.get("pt_name");
				String apn_time = (String) record.get("apn_time");
			System.out.println(apn_id);
				p.setString(1, apn_date);
				p.setString(2, apn_id);
				p.setString(3, dr_name);
				p.setString(4, pt_name);
				p.setString(5, apn_time);
				p.executeUpdate();
				
			}
			System.out.println("Records inserted");	
			} catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
			}

		}
	
}

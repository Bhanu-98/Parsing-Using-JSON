package Appoinment;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class DataBaseToJSON {
	public static ResultSet RetrieveData() throws Exception{
		Connection conn = null;

		try {
			conn =
					DriverManager.getConnection("jdbc:mysql://localhost:3306/mavoix?" +
							"user=bhanu&password=bhanu");
			System.out.println("Connected");
			Scanner scan = new Scanner(System.in);
			String mob = scan.next();
			scan.close();
			Statement stmt = conn.createStatement();
			String q = "select appointment.apn_id, patient.pt_name,appointment.dr_name, appointment.apn_date,appointment.apn_time from appointment INNER JOIN patient ON appointment.pt_id = patient.pt_id where patient.mob_number="+mob;
			ResultSet rs = stmt.executeQuery(q);
			System.out.println("retrieved");
		
			return rs;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;

	}
	public static void main(String[] args) throws Exception {
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		ResultSet rs = RetrieveData();
		while(rs.next()) {
			JSONObject record = new JSONObject();
			record.put("apn_id",rs.getString(1));
			record.put("pt_name",rs.getString(2));
			record.put("dr_name",rs.getString(3));
			record.put("apn_date",rs.getString(4));
			record.put("apn_time",rs.getString(5));
			array.add(record);
			for(int i=1;i<5;i++) {
				System.out.println(rs.getString(i));
				}
		}
		obj.put("Appointments", array);
		try {
	         FileWriter file = new FileWriter("F:/output.json");
	         file.write(obj.toJSONString());
	         file.close();
	      } catch (IOException e) {
	        
	         e.printStackTrace();
	      }
	      System.out.println("JSON file created......");
	   }
	}


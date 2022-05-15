package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 

public class Payment
{ //A common method to connect to the DB
		private Connection connect(){ 
			
						Connection con = null; 
						
						try{ 
								Class.forName("com.mysql.jdbc.Driver"); 
 
								//Provide the correct details: DBServer/DBName, username, password 
								con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/payments", "root", ""); 
						} 
						catch (Exception e) {
							e.printStackTrace();
							} 
						
						return con; 
			} 
		
		public String insertPayment(String AccNumber, String DateOfPayment, String Amount, String TypeOfPayment){ 
			
			String output = ""; 
			
			try
			{ 
				Connection con = connect(); 
				
				if (con == null) 
				{
					return "Error while connecting to the database for inserting."; 
					
				} 
				// create a prepared statement
				
				String query = " insert into payments (`PaymentID`,`AccNumber`,`DateOfPayment`,`Amount`,`TypeOfPayment`)"+" values (?, ?, ?, ?, ?)"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				// binding values
				preparedStmt.setInt(1, 0); 
				preparedStmt.setString(2, AccNumber); 
				preparedStmt.setString(3, DateOfPayment); 
				preparedStmt.setDouble(4, Double.parseDouble(Amount)); 
				preparedStmt.setString(5, TypeOfPayment); 
				// execute the statement

				preparedStmt.execute(); 
				con.close(); 
				
				String newPayments = readPayments(); 
				output = "{\"status\":\"success\",\"data\":\""+newPayments+"\"}"; 
			} 
			
			catch (Exception e) 
			{ 
				output = "{\"status\":\"error\", \"data\":\"Error while inserting the payment.\"}"; 
				System.err.println(e.getMessage()); 
			} 
			return output; 
	}

		public String readPayments() 
		{ 
			String output = ""; 
			try
			{ 
				Connection con = connect(); 
		 if (con == null) 
		 { 
		 return "Error while connecting to the database for reading."; 
		 } 
		 // Prepare the html table to be displayed
		 output = "<table border=\"1\" class=\"table\"><tr><th>Acc Number</th>"
		 		+ "<th>Date Of Payment</th><th>Amount</th>"
		 		+ "<th>Type Of Payment</th>"
		 		+ "<th>Update</th>"
		 		+ "<th>Remove</th></tr>"; 
		
		 String query = "select * from payments"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query); 
		 // iterate through the rows in the result set
		 while (rs.next()) 
		 { 
		 String PaymentID = Integer.toString(rs.getInt("PaymentID")); 
		 String AccNumber = rs.getString("AccNumber"); 
		 String DateOfPayment = rs.getString("DateOfPayment"); 
		 String Amount = Double.toString(rs.getDouble("Amount")); 
		 String TypeOfPayment = rs.getString("TypeOfPayment"); 
		 // Add into the html table
		 output += "<tr><td><input id='hidPaymentIDUpdate' name='hidPaymentIDUpdate' type='hidden' value='"+PaymentID+"'>"+AccNumber+"</td>"; 
		 output += "<td>" + DateOfPayment + "</td>"; 
		 output += "<td>" + Amount + "</td>"; 
		 output += "<td>" + TypeOfPayment + "</td>"; 
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' "
				 + "class='btnUpdate btn btn-secondary' data-paymentid='" + PaymentID + "'></td>"
				 + "<td><input name='btnRemove' type='button' value='Remove' "
				 + "class='btnRemove btn btn-danger' data-paymentid='" + PaymentID + "'></td></tr>"; 
		 
		 } 
		 con.close(); 
		 // Complete the html table
		 output += "</table>"; 
		 } 
		 
		catch (Exception e) 
		 { 
		 output = "Error while reading the payments."; 
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
		}
		
		public String updatePayment(String ID, String AccNumber, String DateOfPayment, String Amount, String TypeOfPayment){ 
			
			String output = ""; 
			
			try{ 
					Connection con = connect(); 
					if (con == null){
						return "Error while connecting to the database for updating.";
						} 
					// create a prepared statement
					String query = "UPDATE payments SET AccNumber=?,DateOfPayment=?,Amount=?,TypeOfPaymet=? WHERE PaymentID=?"; 
					PreparedStatement preparedStmt = con.prepareStatement(query); 
					// binding values
					preparedStmt.setString(1, AccNumber); 
					preparedStmt.setString(2, DateOfPayment); 
					preparedStmt.setDouble(3, Double.parseDouble(Amount)); 
					preparedStmt.setString(4, TypeOfPayment); 
					preparedStmt.setInt(5, Integer.parseInt(ID)); 
					// execute the statement
					preparedStmt.execute(); 
					con.close(); 
					String newPayments = readPayments(); 
					output = "{\"status\":\"success\",\"data\":\""+newPayments+"\"}"; 

			} 
			
			catch (Exception e){ 
				
				output = "{\"status\":\"error\",\"data\":\"Error while updating the payment.\"}"; 

				System.err.println(e.getMessage()); 
				
			} 
			
			return output; 
	} 
		
		public String deletePayment(String PaymentID){ 
			
			String output = ""; 
			
			try{ 
				Connection con = connect(); 
				
				if (con == null){
					return "Error while connecting to the database for deleting."; 
					} 
				// create a prepared statement
				String query = "delete from payments where PaymentID=?"; 
				PreparedStatement preparedStmt = con.prepareStatement(query); 
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(PaymentID)); 
				// execute the statement
				preparedStmt.execute(); 
				con.close(); 
				String newPayments = readPayments(); 
				 output = "{\"status\":\"success\",\"data\":\""+newPayments+"\"}"; 

			} 
			
			catch (Exception e){ 
				output = "{\"status\":\"error\",\"data\":\"Error while deleting the payment.\"}";
				System.err.println(e.getMessage()); 
			} 
			return output; 
	} 
	
	
}
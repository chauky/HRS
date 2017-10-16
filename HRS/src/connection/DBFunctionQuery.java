//package connection;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import dataPool.RoomCombo;
//import dataPool.RoomGrid;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//
//public class DBFunctionQuery {
//	MysqlConnection db;
//	ResultSet rs;
//	String query;
//
//	public DBFunctionQuery() {
//		db = MysqlConnection.getDbCon();
//	}
//
//	// ==============================================DB functions for Employee
//	// ==========================================
//
//	public void addEmployee(int id, String usr, String pwd, int role, String first, String last, String adr,
//			String ssn) {
//		query = "INSERT IGNORE INTO employee (ID, Username, Password, Role, Firstname, Lastname, Address, SSN) VALUES("
//				+ id + ",'" + usr + "','" + pwd + "'," + role + ",'" + first + "','" + last + "','" + adr + "','" + ssn
//				+ "')";
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void updateEmployee(int id, String pwd, int role, String first, String last, String adr, String ssn) {
//		query = "UPDATE employee SET Password='" + pwd + "',Role=" + role + ",Firstname='" + first + "',Lastname='"
//				+ last + "',Address='" + adr + "',SSN='" + ssn + "'WHERE ID=" + id;
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void deleteEmployee(int id) {
//
//		query = "DELETE FROM employee WHERE ID=" + id;
//		System.out.println(query);
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void deleteEmployee(String usr) {
//		query = "DELETE FROM employee WHERE Username='" + usr + "'";
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	// ==============================================DB functions for Customer
//	// ==========================================
//
//	public void addCustomer(String first, String last, String adr, String phone) {
//		query = "INSERT IGNORE INTO customer (Firstname, Lastname, Address, PhoneNumber) VALUES('" + first + "','"
//				+ last + "','" + adr + "','" + phone + "')";
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void updateCustomer(int id, String first, String last, String adr, String phone) {
//		query = "UPDATE customer SET Firstname='" + first + "',Lastname='" + last + "',Address='" + adr
//				+ "',PhoneNumber='" + phone + "'WHERE ID=" + id;
//		System.out.println(query);
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void deleteCustomer(int id) {
//
//		query = "DELETE FROM customer WHERE ID=" + id;
//		System.out.println(query);
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	// ==============================================DB functions for Room
//	// ==========================================
//
//	public void addRoom(int roomNo, String type, String state, double price) {
//		query = "INSERT IGNORE INTO room (RoomNumber, Type, State, PricePerDay) VALUES(" + roomNo + ",'" + type + "','"
//				+ state + "'," + price + ")";
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void updateRoom(int id, int roomNo, String type, String state, double price) {
//		query = "UPDATE room SET roomNumber=" + roomNo + ",Type='" + type + "',State='" + state + "',PricePerDay="
//				+ price + "WHERE ID=" + id;
//		System.out.println(query);
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void updateRoom(int roomNo, String type, String state, double price) {
//		query = "UPDATE room SET Type='" + type + "',State='" + state + "',PricePerDay=" + price + "WHERE RoomNumber="
//				+ roomNo;
//		System.out.println(query);
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void deleteRoom(int roomNumber) {
//
//		query = "DELETE FROM room WHERE RoomNumber=" + roomNumber;
//		System.out.println(query);
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//		
//		public double getPricePerDay(int roomNo) {
//			double price =0.0;
//			query = "SELECT * from room WHERE RoomNumber=" + roomNo;
//			try {
//				rs = db.query(query);
//				price = rs.getDouble("PricePerDay");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return price;
//		}
//	
//
//	// ==============================================DB functions for Registration
//	// ==========================================
//
//	public void addRegistration(String chkinDate, String chkoutDate, int roomID, String payment, double totalPayment,
//			int customerID) {
//		query = "INSERT IGNORE INTO registration (CheckinDate, CheckoutDate, RoomID, Payment, TotalPayment, CustomerID) VALUES('"
//				+ chkinDate + "','" + chkoutDate + "'," + roomID + ",'" + payment + "'," + totalPayment + ","
//				+ customerID + ")";
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void updateRegistration(int id, String chkinDate, String chkoutDate, int roomID, String payment,
//			double totalPayment, int customerID) {
//		query = "UPDATE registration SET checkinDate='" + chkinDate + "',checkoutDate='" + chkoutDate + "',RoomID="
//				+ roomID + ",Payment='" + payment + "',TotalPayment=" + totalPayment + ",CustomerID=" + customerID
//				+ " WHERE ID=" + id;
//		System.out.println(query);
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void deleteRegistration(int id) {
//		query = "DELETE FROM registration WHERE ID=" + id;
//		System.out.println(query);
//		try {
//			db.executeQuery(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public int getRegistrationID(int customerID) {
//		int id = -1;
//		query = "SELECT * FROM registration WHERE CustomerID=" + customerID;
//		try {
//			rs = db.query(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			while (rs.next()) {
//				// System.out.println(rs.getString("RegistrationID"));
//				id = Integer.parseInt(rs.getString("RegistrationID"));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return id;
//	}
//
//	// ==============================================DB Services
//	// ==========================================
//
//	public List<Integer> getListOfService(int customerID) {
//		List<Integer> services = new ArrayList<Integer>();
//		int regID = getRegistrationID(customerID);
//
//		query = "SELECT * FROM usedservice WHERE RegistrationID=" + regID;
//		try {
//			rs = db.query(query);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			while (rs.next()) {
//				// System.out.println(rs.getString("ServiceID"));
//				services.add(Integer.parseInt(rs.getString("ServiceID")));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return services;
//	}
//
//	public List<String> getRoomAttributes() {
//		List<String> strArray = new ArrayList<>();
//		
//		query = "SELECT room.RoomNumber,room.Type,room.State,room.PricePerDay,customer.FirstName,registration.CheckinDate,registration.CheckoutDate FROM `room`\r\n" + 
//				"left join registration  on room.ID = registration.RoomID and room.State <> 'Available'\r\n" + 
//				"left join customer on registration.CustomerID = customer.ID\r\n" + 
//				"order by room.RoomNumber";
//		try {
//			rs = db.query(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			while (rs.next()) {
//				strArray.add(rs.getString("RoomNumber"));
//				strArray.add(rs.getString("Type"));
//				strArray.add(rs.getString("State"));
//				strArray.add(rs.getString("PricePerDay"));
//				strArray.add(rs.getString("firstname"));
//				strArray.add(rs.getString("Checkindate"));
//				strArray.add(rs.getString("Checkoutdate"));
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return strArray;
//	}
//	
//	public List<String> getRoomAttributesByRoomNo(String roomNo) {
//		List<String> strArray = new ArrayList<>();
//		
//		query = "SELECT * FROM room WHERE RoomNumber=" + roomNo;
//		try {
//			rs = db.query(query);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			while (rs.next()) {
//				strArray.add(rs.getString("RoomNumber"));
//				strArray.add(rs.getString("Type"));
//				strArray.add(rs.getString("State"));
//				strArray.add(rs.getString("PricePerDay"));
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return strArray;
//	}
//}
package connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataPool.RoomCombo;
import dataPool.RoomGrid;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBFunctionQuery {
	MysqlConnection db;
	ResultSet rs;
	String query;

	public DBFunctionQuery() {
		db = MysqlConnection.getDbCon();
	}

	// ==============================================DB functions for Employee
	// ==========================================

	public void addEmployee(int id, String usr, String pwd, int role, String first, String last, String adr,
			String ssn) {
		query = "INSERT IGNORE INTO employee (ID, Username, Password, Role, Firstname, Lastname, Address, SSN) VALUES("
				+ id + ",'" + usr + "','" + pwd + "'," + role + ",'" + first + "','" + last + "','" + adr + "','" + ssn
				+ "')";
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateEmployee(int id, String pwd, int role, String first, String last, String adr, String ssn) {
		query = "UPDATE employee SET Password='" + pwd + "',Role=" + role + ",Firstname='" + first + "',Lastname='"
				+ last + "',Address='" + adr + "',SSN='" + ssn + "'WHERE ID=" + id;
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteEmployee(int id) {

		query = "DELETE FROM employee WHERE ID=" + id;
		System.out.println(query);
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteEmployee(String usr) {
		query = "DELETE FROM employee WHERE Username='" + usr + "'";
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ==============================================DB functions for Customer
	// ==========================================

	public void addCustomer(String first, String last, String adr, String phone) {
		query = "INSERT IGNORE INTO customer (Firstname, Lastname, Address, PhoneNumber) VALUES('" + first + "','"
				+ last + "','" + adr + "','" + phone + "')";
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateCustomer(int id, String first, String last, String adr, String phone) {
		query = "UPDATE customer SET Firstname='" + first + "',Lastname='" + last + "',Address='" + adr
				+ "',PhoneNumber='" + phone + "'WHERE ID=" + id;
		System.out.println(query);
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteCustomer(int id) {

		query = "DELETE FROM customer WHERE ID=" + id;
		System.out.println(query);
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getCustomerID(String first, String last){
		int id =0;
		query = "SELECT ID FROM customer WHERE FirstName ='" + first + "' AND LastName ='"+ last+ "'"; 
		System.out.println(query);
		try {
			rs = db.query(query);
			//roomID = Integer.parseInt(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				// System.out.println(rs.getString("RegistrationID"));
				id = rs.getInt("ID");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public List<String> getListOfCustomer(){
		List<String> list = new ArrayList<>();
		query = "SELECT * FROM customer"; 
		System.out.println(query);
		try {
			rs = db.query(query);
			//roomID = Integer.parseInt(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				// System.out.println(rs.getString("RegistrationID"));
				list.add(rs.getString("ID"));
				list.add(rs.getString("FirstName"));
				list.add(rs.getString("LastName"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// ==============================================DB functions for Room
	// ==========================================

	public void addRoom(int roomNo, String type, String state, double price) {
		query = "INSERT IGNORE INTO room (RoomNumber, Type, State, PricePerDay) VALUES(" + roomNo + ",'" + type + "','"
				+ state + "'," + price + ")";
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateRoom(int id, int roomNo, String type, String state, double price) {
		query = "UPDATE room SET roomNumber=" + roomNo + ",Type='" + type + "',State='" + state + "',PricePerDay="
				+ price + "WHERE ID=" + id;
		System.out.println(query);
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateRoom(int roomNo, String type, String state, double price) {
		query = "UPDATE room SET Type='" + type + "',State='" + state + "',PricePerDay=" + price + "WHERE RoomNumber="
				+ roomNo;
		System.out.println(query);
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateRoomStatus(String roomNo, String state) {
		query = "UPDATE room SET State='" + state + "'WHERE roomNumber='" + roomNo+ "'";
		System.out.println(query);
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getRoomID(String roomNo) {
		int roomID =-1;
		query = "SELECT * FROM room WHERE RoomNumber='" + roomNo +"'"; 
		System.out.println(query);
		try {
			rs = db.query(query);
			//roomID = Integer.parseInt(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				// System.out.println(rs.getString("RegistrationID"));
				roomID = Integer.parseInt(rs.getString("ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roomID;
	}
	
	public void deleteRoom(int roomNumber) {

		query = "DELETE FROM room WHERE RoomNumber=" + roomNumber;
		System.out.println(query);
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		public double getPricePerDay(int roomNo) {
			double price =0.0;
			query = "SELECT * from room WHERE RoomNumber=" + roomNo;
			try {
				rs = db.query(query);
				price = rs.getDouble("PricePerDay");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return price;
		}
		public List<String> getRoomAttributes() {
			List<String> strArray = new ArrayList<>();
			
			query = "SELECT * FROM room";
			try {
				rs = db.query(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				while (rs.next()) {
					strArray.add(rs.getString("RoomNumber"));
					strArray.add(rs.getString("Type"));
					strArray.add(rs.getString("State"));
					strArray.add(rs.getString("PricePerDay"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return strArray;
		}
		
		public List<String> getRoomAttributesByRoomNo(String roomNo) {
			List<String> strArray = new ArrayList<>();
			
			query = "SELECT * FROM room WHERE RoomNumber=" + roomNo;
			try {
				rs = db.query(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				while (rs.next()) {
					strArray.add(rs.getString("RoomNumber"));
					strArray.add(rs.getString("Type"));
					strArray.add(rs.getString("State"));
					strArray.add(rs.getString("PricePerDay"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return strArray;
		}
		
		public List<String> getAvailableRoom() {
			List<String> strArray = new ArrayList<>();
			
			query = "SELECT * FROM room WHERE State='Available' OR State='Booked'";
			try {
				rs = db.query(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				while (rs.next()) {
					strArray.add(rs.getString("RoomNumber"));

				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return strArray;
		}

	// ==============================================DB functions for Registration
	// ==========================================

	public void addRegistration(String chkinDate, String chkoutDate, String roomNo, String payment, double totalPayment, int customerID) {
			int roomID = getRoomID(roomNo);
			System.out.println(chkinDate);
			System.out.println(chkoutDate);
			
		query = "INSERT IGNORE INTO registration (CheckinDate, CheckoutDate, RoomID, Payment, TotalPayment, CustomerID) VALUES('"
				+ chkinDate + "','" + chkoutDate + "'," + roomID + ",'" + payment + "'," + totalPayment + ","
				+ customerID + ")";
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateRegistration(int id, String chkinDate, String chkoutDate, int roomID, String payment,
			double totalPayment, int customerID) {
		query = "UPDATE registration SET checkinDate='" + chkinDate + "',checkoutDate='" + chkoutDate + "',RoomID="
				+ roomID + ",Payment='" + payment + "',TotalPayment=" + totalPayment + ",CustomerID=" + customerID
				+ " WHERE RegistrationID=" + id;
		System.out.println(query);
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteRegistration(int id) {
		query = "DELETE FROM registration WHERE ID=" + id;
		System.out.println(query);
		try {
			db.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getRegistrationID(int customerID) {
		int id = -1;
		query = "SELECT * FROM registration WHERE CustomerID=" + customerID;
		try {
			rs = db.query(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				// System.out.println(rs.getString("RegistrationID"));
				id = Integer.parseInt(rs.getString("RegistrationID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	// ==============================================DB Services
	// ==========================================

	public List<Integer> getListOfService(int customerID) {
		List<Integer> services = new ArrayList<Integer>();
		int regID = getRegistrationID(customerID);

		query = "SELECT * FROM usedservice WHERE RegistrationID=" + regID;
		try {
			rs = db.query(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				// System.out.println(rs.getString("ServiceID"));
				services.add(Integer.parseInt(rs.getString("ServiceID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return services;
	}
 
	public void addServiceUsed(List<Integer> services, int registrationID) {
		for(int i = 0; i < services.size();i++) {
			query = "INSERT IGNORE INTO usedservice (ServiceID, RegistrationID) VALUES("+ services.get(i)+","+ registrationID + ")";
			try {
				db.executeQuery(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//===========================================Report functions==================================================
	
	public List<String> RoomShow(){
		
		List<String> strArray = new ArrayList<String>();
		query = "select a.RoomNumber, a.Type, a.State, a.PricePerDay, c.FirstName, c.LastName, b.CheckinDate, b.CheckoutDate From room a, registration b , customer c WHERE a.ID = b.RoomID AND b.CustomerID = c.ID";
		
		try {
			rs = db.query(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				strArray.add(rs.getString("RoomNumber"));
				strArray.add(rs.getString("Type"));
				strArray.add(rs.getString("State"));
				strArray.add(rs.getString("PricePerDay"));
				strArray.add(rs.getString("FirstName"));
				strArray.add(rs.getString("LastName"));
				strArray.add(rs.getString("CheckinDate"));
				strArray.add(rs.getString("CheckoutDate"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return strArray;

	}
	
public List<String> showRooms(){
		
		List<String> strArray = new ArrayList<String>();
		query = "select a.RoomNumber, a.Type, a.State, a.PricePerDay from room as a order by a.roomnumber";
		
		try {
			rs = db.query(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				strArray.add(rs.getString("RoomNumber"));
				strArray.add(rs.getString("Type"));
				strArray.add(rs.getString("State"));
				strArray.add(rs.getString("PricePerDay"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return strArray;
	}

public List<String> showEmployeeList(){
		
		List<String> strArray = new ArrayList<String>();
		query = "select a.FirstName,  a.LastName,b.Description, a.SSN,a.Address, a.Username from employee a, role b where a.Role = b.ID";
		
		try {
			rs = db.query(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while (rs.next()) {
				strArray.add(rs.getString("FirstName"));
				strArray.add(rs.getString("LastName"));
				strArray.add(rs.getString("Description"));
				strArray.add(rs.getString("SSN"));
				strArray.add(rs.getString("Address"));
				strArray.add(rs.getString("Username"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return strArray;
	}

public List<String> showBookingList(){
	
	List<String> strArray = new ArrayList<String>();
	query = "select r1.checkindate,r1.CheckoutDate,r1.Payment,r1.TotalPayment,r2.RoomNumber,r2.Type,r2.State,r2.PricePerDay,c1.FirstName,c1.LastName,c1.Address,c1.PhoneNumber from registration as r1\r\n" + 
			"left join room r2 on r1.RoomID = r2.ID\r\n" + 
			"left join customer c1 on r1.CustomerID = c1.ID";
	
	try {
		rs = db.query(query);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	try {
		while (rs.next()) {
			strArray.add(rs.getString("CheckinDate"));
			strArray.add(rs.getString("CheckoutDate"));
			strArray.add(rs.getString("Payment"));
			strArray.add(rs.getString("TotalPayment"));
			strArray.add(rs.getString("RoomNumber"));
			strArray.add(rs.getString("Type"));
			strArray.add(rs.getString("State"));
			strArray.add(rs.getString("PricePerDay"));
			strArray.add(rs.getString("FirstName"));
			strArray.add(rs.getString("LastName"));
			strArray.add(rs.getString("Address"));
			strArray.add(rs.getString("PhoneNumber"));
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	return strArray;
}
}

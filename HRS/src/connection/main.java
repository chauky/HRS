package connection;
import java.util.*;

import util.*;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;



public class main {

    public static void main(String args[]) throws SQLException{  
    	DBFunctionQuery dbemp = new DBFunctionQuery();
    	Util util = new Util();
    	
    	//============Test for Employee ===================================
//    	dbemp.addEmployee(1005, "username", "1234",1, "mandak", "ABC", "1000N 6th Street, IA", "789-546-5214");
//    	dbemp.updateEmployee(1005,"username",1, "KY", "ABC", "1000N 6th Street, IA", "789-546-5214");
//    	dbemp.deleteEmployee(1005);
//    	dbemp.deleteEmployee("username");
    	
    	
    	//============Test for Customer ===================================
    	
//    	dbemp.addCustomer("mandak", "ABC", "1000N 6th Street, IA", "789-546-5214");
//    	dbemp.updateCustomer(2,"username", "KY", "1000N 6th Street, IA", "789-546-5214");
//    	dbemp.deleteCustomer(2);
    
    	//============Test for Room =======================================
    	
//    	dbemp.addRoom(209, "Deluxe", "Occupied", 900.7);
//    	dbemp.updateRoom(10,209, "Single", "Occupied", 500.7);
//    	dbemp.updateRoom(209, "Single", "Occupied", 100.7);
    	dbemp.deleteRoom(209);
    	
    	
    	//============Test for Registration =======================================
    	
//        Date now = new Date();
//        String mysqlDateString = util.dateToString(now);
//    	dbemp.addRegistration(mysqlDateString, mysqlDateString, 12, 1, 234, 2);
//    	dbemp.updateRegistration(11, mysqlDateString, mysqlDateString, 12, 1, 790, 2);
//        dbemp.deleteRegistration(11);
    	
//    	dbemp.getListOfService(2);
    	
    	
	}  
}

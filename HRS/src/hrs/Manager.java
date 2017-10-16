package hrs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DBFunctionQuery;
import connection.MysqlConnection;
import dataPool.RegistrationGrid;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Manager extends Stage implements Employee {
	Stage primaryStage;
	boolean firstTime = true;
	BorderPane border = new BorderPane();
	Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();
	GridPane grid = new GridPane();
	GridPane gridNew = new GridPane();
	private TableView<Person> table = new TableView<Person>();
	private final ObservableList<Person> data = FXCollections.observableArrayList();
	final HBox hb = new HBox();
	private TableView<RegistrationGrid> tableBook = new TableView<RegistrationGrid>();
	private static ObservableList<RegistrationGrid> dataBook = FXCollections.observableArrayList();
	DBFunctionQuery dbfunc = new DBFunctionQuery();
	public Manager(Stage ps) throws SQLException {
		primaryStage = ps;
		setTitle("Manager page");
		dbCon();
		border.setTop(addHBox());
		border.setCenter(addEmployeeGrid());
		Scene scene = new Scene(border, bounds.getWidth(), bounds.getHeight());
		setScene(scene);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private GridPane addEmployeeGrid() {

		HBox labelHbox = new HBox(10);
		labelHbox.setAlignment(Pos.CENTER);

		TableColumn col1 = new TableColumn("Employee ID");
		col1.setMinWidth(150);
		col1.setCellValueFactory(new PropertyValueFactory<Person, String>("ID"));

		TableColumn col2 = new TableColumn("First name");
		col2.setMinWidth(150);
		col2.setCellValueFactory(new PropertyValueFactory<Person, String>("FirstName"));

		TableColumn col3 = new TableColumn("Last name");
		col3.setMinWidth(150);
		col3.setCellValueFactory(new PropertyValueFactory<Person, String>("LastName"));

		TableColumn col4 = new TableColumn("Position");
		col4.setMinWidth(150);
		col4.setCellValueFactory(new PropertyValueFactory<Person, String>("Role"));

		TableColumn col5 = new TableColumn("Address");
		col5.setMinWidth(300);
		col5.setCellValueFactory(new PropertyValueFactory<Person, String>("Address"));

		TableColumn col6 = new TableColumn("SSN");
		col6.setMinWidth(150);
		col6.setCellValueFactory(new PropertyValueFactory<Person, String>("SSN"));

		TableColumn col7 = new TableColumn("User name");
		col7.setMinWidth(150);
		col7.setCellValueFactory(new PropertyValueFactory<Person, String>("Username"));

		table.setItems(data);
		table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);

		//GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		grid.setHgap(10);
		grid.add(labelHbox, 0, 0);
		grid.add(table, 0, 1);

		HBox btnBox = new HBox(10);
		btnBox.setAlignment(Pos.CENTER);
		grid.add(btnBox, 0, 4);
		grid.add(new HBox(10), 0, 5);

		return grid;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private GridPane addGridPaneBookingList() {

		// border.getChildren().clear();

		// GridPane grid = new GridPane();

		HBox labelHbox = new HBox(10);
		labelHbox.setAlignment(Pos.CENTER);

		TableColumn col1 = new TableColumn("CheckinDate");
		col1.setMinWidth(150);
		col1.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("CheckinDate"));

		TableColumn col2 = new TableColumn("CheckoutDate");
		col2.setMinWidth(150);
		col2.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("CheckoutDate"));

		TableColumn col3 = new TableColumn("Payment");
		col3.setMinWidth(150);
		col3.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("Payment"));

		TableColumn col4 = new TableColumn("TotalPayment");
		col4.setMinWidth(150);
		col4.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("TotalPayment"));

		TableColumn col5 = new TableColumn("RoomNumber");
		col5.setMinWidth(150);
		col5.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("RoomNumber"));

		TableColumn col6 = new TableColumn("Type");
		col6.setMinWidth(150);
		col6.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("Type"));

		TableColumn col7 = new TableColumn("State");
		col7.setMinWidth(150);
		col7.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("State"));

		TableColumn col8 = new TableColumn("PricePerDay");
		col8.setMinWidth(150);
		col8.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("PricePerDay"));

		TableColumn col9 = new TableColumn("FirstName");
		col9.setMinWidth(150);
		col9.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("FirstName"));

		TableColumn col10 = new TableColumn("LastName");
		col10.setMinWidth(150);
		col10.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("LastName"));

		TableColumn col11 = new TableColumn("Address");
		col11.setMinWidth(150);
		col11.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("Address"));

		TableColumn col12 = new TableColumn("PhoneNumber");
		col12.setMinWidth(150);
		col12.setCellValueFactory(new PropertyValueFactory<RegistrationGrid, String>("PhoneNumber"));

		tableBook.setItems(dataBook);
		tableBook.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12);

		gridNew.setAlignment(Pos.CENTER);
		gridNew.setVgap(10);
		gridNew.setHgap(10);
		gridNew.add(labelHbox, 0, 0);
		gridNew.add(tableBook, 0, 1);

		HBox btnBox = new HBox(10);
		btnBox.setAlignment(Pos.CENTER);
		gridNew.add(btnBox, 0, 4);
		gridNew.add(new HBox(10), 0, 5);
		return gridNew;
	}
	
	public HBox addHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");

		Button buttonAllEmployee = new Button("All Employees");
		buttonAllEmployee.setPrefSize(150, 20);

		Button buttonReport = new Button("Room report");
		buttonReport.setPrefSize(150, 20);
		hbox.getChildren().addAll(buttonAllEmployee, buttonReport);

		class MessageBarHandler implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				border.setTop(hbox);
				border.setCenter(grid);
			}
		}
		buttonAllEmployee.setOnAction(new MessageBarHandler());

		class MessageBarHandlerNew implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				border.setTop(hbox);
				// border.setCenter(null);
				if (firstTime) {
					border.setCenter(addGridPaneBookingList());
					setBookRegData();
					firstTime = false;
				} else {
					border.setCenter(gridNew);
					setBookRegData();
				}
			}
		}
		buttonReport.setOnAction(new MessageBarHandlerNew());

		return hbox;
	}
	
	public void setBookRegData() {
		List<String> list = new ArrayList<String>();
		list = dbfunc.showBookingList();
		dataBook.clear();
		for (int j = 0; j <= list.size() - 12; j += 12) {
			dataBook.add(new RegistrationGrid(list.get(j), list.get(j + 1), list.get(j + 2), list.get(j + 3),
					list.get(j + 4), list.get(j + 5), list.get(j + 6), list.get(j + 7), list.get(j + 8),
					list.get(j + 9), list.get(j + 10), list.get(j + 11)));
		}
	}

	public void dbCon() throws SQLException {
		MysqlConnection db;
		db = MysqlConnection.getDbCon();
		ResultSet rs = db.query("select id,firstname,lastname,role,address,ssn,username from employee");
		while (rs.next()) {
			data.add(new Person(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7)));
		}
	}
	
	public static class Person {

		private final SimpleStringProperty id;
		private final SimpleStringProperty firstname;
		private final SimpleStringProperty lastname;
		private final SimpleStringProperty role;
		private final SimpleStringProperty address;
		private final SimpleStringProperty ssn;
		private final SimpleStringProperty username;

		private Person(String id, String fname, String lname, String role, String address, String ssn,
				String username) {
			this.id = new SimpleStringProperty(id);
			this.firstname = new SimpleStringProperty(fname);
			this.lastname = new SimpleStringProperty(lname);
			if(role.equals("1")) role ="Administrator";
			if(role.equals("2")) role ="Receptionist";
			if(role.equals("3")) role ="Manager";
			this.role = new SimpleStringProperty(role);
			this.address = new SimpleStringProperty(address);
			this.ssn = new SimpleStringProperty(ssn);
			this.username = new SimpleStringProperty(username);
		}

		public String getID() {
			return id.get();
		}

		public void setID(String id) {
			this.id.set(id);
		}

		public String getFirstName() {
			return firstname.get();
		}

		public void setFirstName(String fName) {
			firstname.set(fName);
		}

		public String getLastName() {
			return lastname.get();
		}

		public void setLastName(String fName) {
			lastname.set(fName);
		}

		public String getRole() {
			return role.get();
		}

		public void setRole(String role) {
			this.role.set(role);
		}

		public String getAddress() {
			return address.get();
		}

		public void setAddress(String address) {
			this.address.set(address);
		}

		public String getSSN() {
			return ssn.get();
		}

		public void setSSN(String ssn) {
			this.ssn.set(ssn);
		}

		public String getUsername() {
			return username.get();
		}

		public void setUsername(String username) {
			this.username.set(username);
		}
	}
}

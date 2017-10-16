package hrs;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import connection.DBFunctionQuery;
import dataPool.EmployeeListGrid;
import dataPool.RoomCombo;
import dataPool.RoomGrid;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import util.Util;

public class Admin extends Stage implements Employee {
	Stage primaryStage;
	BorderPane border = new BorderPane();
	Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();
	private TableView<EmployeeListGrid> table = new TableView<EmployeeListGrid>();
	private static ObservableList<EmployeeListGrid> data = FXCollections.observableArrayList();
	private TableView<RoomGrid> tableRoom = new TableView<>();
	private static ObservableList<RoomGrid> dataRoom = FXCollections.observableArrayList();
	boolean firstTime = true;
	GridPane grid = new GridPane();
	GridPane gridRoom = new GridPane();
	DBFunctionQuery dbfunc = new DBFunctionQuery();
	
	private static final ObservableList<String> positionOption = FXCollections.observableArrayList("Adminstrator",
			"Receptionist", "Manager");
	private static final ObservableList<String> roomTypeOpt = FXCollections.observableArrayList("Single",
			"Double", "Deluxe");
	private static final ObservableList<String> roomStateOpt = FXCollections.observableArrayList("Available",
			"Booked", "Occupied");
	public Admin(Stage ps) {
		primaryStage = ps;
		setTitle("Admin page");

		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		setData();
		setDataRoom();
		HBox hbox = addHBox();
		border.setTop(hbox);
		// border.setLeft(addEmployeeGrid());
		border.setCenter(addGridPaneEmpList());

		Scene scene = new Scene(border, bounds.getWidth(), bounds.getHeight());
		setScene(scene);
	}

	public void setData() {
		List<String> list = new ArrayList<String>();
		list = dbfunc.showEmployeeList();
		for (int j = 0; j <= list.size() - 6; j += 6) {

			data.add(new EmployeeListGrid(list.get(j), list.get(j + 1), list.get(j + 2), list.get(j + 3),
					list.get(j + 4), list.get(j + 5)));
		}
	}

	public void setDataRoom() {
		List<String> list = new ArrayList<String>();
		list = dbfunc.showRooms();
		for (int j = 0; j <= list.size() - 4; j += 4) {
			dataRoom.add(new RoomGrid(list.get(j), list.get(j + 1), list.get(j + 2), list.get(j + 3), "", "", ""));
		}
	}

	public HBox addHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");

		Button btnEmpList = new Button("Employee list");
		btnEmpList.setMinWidth(150);

		Button btnNewEmp = new Button("New Employee");
		btnNewEmp.setMinWidth(150);

		Button btnRoomList = new Button("Room list");
		btnRoomList.setMinWidth(150);

		Button btnNewRoom = new Button("New Room");
		btnNewRoom.setMinWidth(150);
		hbox.getChildren().addAll(btnEmpList, btnRoomList, btnNewEmp, btnNewRoom);

		class MessageBarHandler implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				border.setTop(hbox);
				border.setCenter(addGridPaneEmployee());

			}
		}
		btnNewEmp.setOnAction(new MessageBarHandler());

		class MessageBarHandlerNewRoom implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				border.setTop(hbox);
				border.setCenter(addGridPaneNewRoom());
			}
		}
		btnNewRoom.setOnAction(new MessageBarHandlerNewRoom());

		class MessageBarHandlerEmpList implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				border.setTop(hbox);
				border.setCenter(grid);

			}
		}
		btnEmpList.setOnAction(new MessageBarHandlerEmpList());

		class MessageBarHandlerRoomList implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				border.setTop(hbox);
				if (firstTime) {
					border.setCenter(addGridPaneRoomList());
					firstTime = false;
				} else
					border.setCenter(gridRoom);

			}
		}
		btnRoomList.setOnAction(new MessageBarHandlerRoomList());

		return hbox;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private GridPane addGridPaneNewRoom() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		Text lblRoomNumber = new Text("Room number");
		lblRoomNumber.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblRoomNumber, 5, 5);

		TextField txtRoomNumber = new TextField();
		grid.add(txtRoomNumber, 7, 5);

		Text lblRoomType = new Text("Room type");
		lblRoomType.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblRoomType, 5, 7);
		
		ComboBox cmbRoomType = new ComboBox(roomTypeOpt);
		cmbRoomType.setMinWidth(165);
		grid.add(cmbRoomType, 7, 7);

		Text lblRoomState = new Text("Room state");
		lblRoomState.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblRoomState, 5, 9);

		ComboBox cmbRoomState = new ComboBox(roomStateOpt);
		cmbRoomState.setMinWidth(165);
		grid.add(cmbRoomState, 7, 9);

		Text lblPrice = new Text("Price");
		lblPrice.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblPrice, 5, 11);

		TextField txtPrice = new TextField();
		grid.add(txtPrice, 7, 11);

		Button btnSave = new Button();
		btnSave.setText("Save");
		btnSave.setOnAction(new EventHandler() {
			public void handle(Event t) {
			int roomNo = Integer.parseInt(txtRoomNumber.getText());
			String type = cmbRoomType.getValue().toString();
			String state = cmbRoomState.getValue().toString();
			double price= Double.parseDouble(txtPrice.getText());
			dbfunc.addRoom(roomNo, type, state, price);		

			}
		});
		grid.add(btnSave, 7, 14);
		return grid;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private GridPane addGridPaneEmployee() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		Text lblEmployeeID = new Text("Employee ID");
		lblEmployeeID.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblEmployeeID, 5, 5);

		TextField txtEmployeeID = new TextField();
		grid.add(txtEmployeeID, 7, 5);
		
		Text lblUserName = new Text("User name");
		lblUserName.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblUserName, 5, 7);

		TextField txtUserName = new TextField();
		grid.add(txtUserName, 7, 7);
		
		Text lblPassword = new Text("Password");
		lblPassword.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblPassword, 5, 9);

		PasswordField txtPassword = new PasswordField();
		grid.add(txtPassword, 7, 9);
		
		Text lblPosition = new Text("Position");
		lblPosition.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblPosition, 5, 11);

		ComboBox cmbPosition = new ComboBox(positionOption);
		cmbPosition.getSelectionModel().selectFirst();
		cmbPosition.setMinWidth(165);
		grid.add(cmbPosition, 7, 11);

		Text lblEmployeeName = new Text("First name");
		lblEmployeeName.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblEmployeeName, 5, 13);

		TextField txtFirstName = new TextField();
		grid.add(txtFirstName, 7, 13);
		
		Text lblLastName = new Text("Last name");
		lblLastName.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblLastName, 5, 15);

		TextField txtLastName = new TextField();
		grid.add(txtLastName, 7, 15);

		Text lblAddress = new Text("Address");
		lblAddress.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblAddress, 5, 17);

		TextField txtAddress = new TextField();
		grid.add(txtAddress, 7, 17);

		Text lblSSN = new Text("SSN");
		lblSSN.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblSSN, 5, 19);

		TextField txtSSN = new TextField();
		grid.add(txtSSN, 7, 19);

		Button btnSave = new Button();
		btnSave.setText("Save");
		btnSave.setOnAction(new EventHandler() {
			public void handle(Event t) {
					int id = Integer.parseInt(txtEmployeeID.getText());	
					String usr= txtUserName.getText();
					String pwd = txtPassword.getText();
					int role = 2;
					String curRole = cmbPosition.getValue().toString();
					if(curRole.equals("Manager"))
						role = 3;
					if(curRole.equals("Administrator"))
						role = 1;
					if(curRole.equals("Receptionist"))
						role = 2;
					String first = txtFirstName.getText();
					String last = txtLastName.getText();
					String adr = txtAddress.getText();
					String ssn = txtSSN.getText();
					dbfunc.addEmployee( id,  usr,  pwd,  role,  first,  last,  adr,
					 ssn);
			}
		});
		grid.add(btnSave, 7, 22);

		return grid;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private GridPane addGridPaneEmpList() {

		HBox labelHbox = new HBox(10);
		labelHbox.setAlignment(Pos.CENTER);

		TableColumn col1 = new TableColumn("FirstName");
		col1.setMinWidth(150);
		col1.setCellValueFactory(new PropertyValueFactory<EmployeeListGrid, String>("FirstName"));

		TableColumn col2 = new TableColumn("LastName");
		col2.setMinWidth(150);
		col2.setCellValueFactory(new PropertyValueFactory<EmployeeListGrid, String>("LastName"));

		TableColumn col3 = new TableColumn("Position");
		col3.setMinWidth(150);
		col3.setCellValueFactory(new PropertyValueFactory<EmployeeListGrid, String>("Position"));

		TableColumn col4 = new TableColumn("SSN");
		col4.setMinWidth(150);
		col4.setCellValueFactory(new PropertyValueFactory<EmployeeListGrid, String>("SSN"));

		TableColumn col5 = new TableColumn("Address");
		col5.setMinWidth(150);
		col5.setCellValueFactory(new PropertyValueFactory<EmployeeListGrid, String>("Address"));

		TableColumn col6 = new TableColumn("Username");
		col6.setMinWidth(150);
		col6.setCellValueFactory(new PropertyValueFactory<EmployeeListGrid, String>("userName"));

		table.setItems(data);
		table.getColumns().addAll(col1, col2, col3, col4, col5, col6);

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
	private GridPane addGridPaneRoomList() {

		// GridPane grid = new GridPane();
		HBox labelHbox = new HBox(10);
		labelHbox.setAlignment(Pos.CENTER);

		TableColumn col1 = new TableColumn("Room number");
		col1.setMinWidth(150);
		col1.setCellValueFactory(new PropertyValueFactory<RoomGrid, String>("Name"));
	
		TableColumn col2 = new TableColumn("Room type");
		col2.setMinWidth(150);
		col2.setCellValueFactory(new PropertyValueFactory<RoomGrid, String>("roomType"));

		TableColumn col3 = new TableColumn("Room state");
		col3.setMinWidth(150);
		col3.setCellValueFactory(new PropertyValueFactory<RoomGrid, String>("State"));

		TableColumn col4 = new TableColumn("Price per day");
		col4.setMinWidth(150);
		col4.setCellValueFactory(new PropertyValueFactory<RoomGrid, String>("PricePerDay"));

		tableRoom.setItems(dataRoom);
		tableRoom.getColumns().addAll(col1, col2, col3, col4);

		gridRoom.setAlignment(Pos.CENTER);
		gridRoom.setVgap(10);
		gridRoom.setHgap(10);
		gridRoom.add(labelHbox, 0, 0);
		gridRoom.add(tableRoom, 0, 1);

		HBox btnBox = new HBox(10);
		btnBox.setAlignment(Pos.CENTER);
		gridRoom.add(btnBox, 0, 4);
		gridRoom.add(new HBox(10), 0, 5);
		return gridRoom;
	}
}

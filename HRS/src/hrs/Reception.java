package hrs;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import connection.DBFunctionQuery;
import dataPool.EmployeeListGrid;
import dataPool.RegistrationGrid;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
import room.DeluxeRoom;
import room.DoubleRoom;
import room.Room;
import room.SingleRoom;
import util.Util;

public class Reception extends Stage implements Employee {
	Stage primaryStage;
	boolean firstTime = true;
	BorderPane border = new BorderPane();
	Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();
	private static ObservableList<RoomGrid> data = FXCollections.observableArrayList();
	private static ObservableList<RegistrationGrid> dataBook = FXCollections.observableArrayList();
	private static final ObservableList<RoomCombo> roomNumbers = FXCollections.observableArrayList();
	private static final ObservableList<String> payment_option = FXCollections.observableArrayList("Cash",
			"Credit card", "Check");
	DBFunctionQuery dbfunc = new DBFunctionQuery();

	GridPane grid = new GridPane();
	GridPane gridNew = new GridPane();
	Room roomObj = new SingleRoom();
	Room roomObjDouble = new DoubleRoom();
	Room roomObjDeluxe = new DeluxeRoom();
	private TableView<RoomGrid> table = new TableView<RoomGrid>();

	private TableView<RegistrationGrid> tableBook = new TableView<RegistrationGrid>();

	private LocalDate checkin;
	private LocalDate checkout;
	private String custName;
	private String roomNo;
	private long daysSpend;
	private double totalAmount = 0.0;
	private int custID;
	private int registrationID;
	private List<Integer> services = new ArrayList<Integer>();

	public Reception(Stage ps) {
		primaryStage = ps;
		setTitle("Receptionist page");

		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		border.setTop(addHBox());
		setData();

		tableBook.setRowFactory(tv -> {
			TableRow<RegistrationGrid> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					RegistrationGrid rowData = row.getItem();

					border.setCenter(addGridPaneBookRoom(rowData));
				}
			});
			return row;
		});

		border.setCenter(addGridPaneRoomList());
		Scene scene = new Scene(border, bounds.getWidth(), bounds.getHeight());
		setScene(scene);
	}

	public void setData() {
		List<String> list = new ArrayList<String>();
		list = dbfunc.RoomShow();
		for (int j = 0; j <= list.size() - 8; j += 8) {
			data.add(new RoomGrid(list.get(j), list.get(j + 1), list.get(j + 2), list.get(j + 3), list.get(j + 5),
					list.get(j + 6), list.get(j + 7)));
		}
		for (int i = 0; i <= list.size() - 8; i += 8) {
			roomNumbers.add(new RoomCombo(list.get(i))); // roomNumber
		}
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

	public HBox addHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");

		Button buttonRoomList = new Button("All rooms");
		buttonRoomList.setPrefSize(150, 20);

		Button buttonNewCust = new Button("New customer");
		buttonNewCust.setPrefSize(150, 20);

		Button buttonNewBook = new Button("Book room");
		buttonNewBook.setPrefSize(150, 20);

		Button buttonBookingList = new Button("Booking list");
		buttonBookingList.setPrefSize(150, 20);
		hbox.getChildren().addAll(buttonRoomList, buttonNewCust, buttonNewBook, buttonBookingList);

		class MessageBarHandler implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				border.setTop(hbox);
				// border.setCenter(null);
				border.setCenter(grid);
			}
		}
		buttonRoomList.setOnAction(new MessageBarHandler());

		class MessageBarHandlerNewCust implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				border.setTop(hbox);
				border.setCenter(addGridPaneNewCust());
			}
		}
		buttonNewCust.setOnAction(new MessageBarHandlerNewCust());

		class MessageBarHandlerNewBook implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				border.setTop(hbox);
				border.setCenter(addGridPaneBookRoom());
			}
		}
		buttonNewBook.setOnAction(new MessageBarHandlerNewBook());

		class MessageBarHandlerBookingList implements EventHandler<ActionEvent> {
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
		buttonBookingList.setOnAction(new MessageBarHandlerBookingList());

		return hbox;
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

		TableColumn col5 = new TableColumn("Customer");
		col5.setMinWidth(150);
		col5.setCellValueFactory(new PropertyValueFactory<RoomGrid, String>("customer"));

		TableColumn col6 = new TableColumn("Check-in");
		col6.setMinWidth(150);
		col6.setCellValueFactory(new PropertyValueFactory<RoomGrid, String>("checkin"));

		TableColumn col7 = new TableColumn("Check-out");
		col7.setMinWidth(150);
		col7.setCellValueFactory(new PropertyValueFactory<RoomGrid, String>("checkout"));

		table.setItems(data);
		table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);

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

	private GridPane addGridPaneNewCust() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		Text lblFirstName = new Text("First name");
		lblFirstName.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblFirstName, 5, 5);

		TextField txtFirstname = new TextField();
		txtFirstname.setMinWidth(150);
		grid.add(txtFirstname, 7, 5);

		Text lblLastName = new Text("Last name");
		lblLastName.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblLastName, 5, 7);

		TextField txtLastName = new TextField();
		txtLastName.setMinWidth(150);
		grid.add(txtLastName, 7, 7);

		Text lblAddress = new Text("Address");
		lblAddress.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblAddress, 5, 9);

		TextField txtAddress = new TextField();
		txtAddress.setMinWidth(150);
		grid.add(txtAddress, 7, 9);

		Text lblPhoneNo = new Text("Phone number");
		lblPhoneNo.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblPhoneNo, 5, 11);

		TextField txtPhoneNo = new TextField();
		txtPhoneNo.setMinWidth(150);
		grid.add(txtPhoneNo, 7, 11);

		Button btnSave = new Button();
		btnSave.setText("Save");
		grid.add(btnSave, 7, 15);

		class MessageBarHandlerSave implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				String first = txtFirstname.getText();
				String last = txtLastName.getText();
				String adr = txtAddress.getText();
				String phone = txtPhoneNo.getText();
				dbfunc.addCustomer(first, last, adr, phone);
			}
		}
		btnSave.setOnAction(new MessageBarHandlerSave());

		return grid;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private GridPane addGridPaneBookRoom() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		Text lblRoomNumber = new Text("Room number");
		lblRoomNumber.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblRoomNumber, 5, 5);

		ComboBox cmbRoomnumber = new ComboBox();
		List<String> lstAvailableRoom = dbfunc.getAvailableRoom();
		for (int i = 0; i < lstAvailableRoom.size(); i++) {
			cmbRoomnumber.getItems().add(lstAvailableRoom.get(i).toString());
		}

		cmbRoomnumber.setMinWidth(165);
		TextField txtRoomType = new TextField();
		TextField txtRoomState = new TextField();
		TextField txtPricePerDay = new TextField();
		cmbRoomnumber.setOnAction((e) -> {
			try {
				List<String> strList = fieldSetterNew(cmbRoomnumber.getValue().toString());
				for (int i = 0; i < strList.size(); i++) {
					txtRoomType.setText(strList.get(1).toString());
					txtRoomState.setText(strList.get(2).toString());
					txtPricePerDay.setText(strList.get(3).toString());
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		grid.add(cmbRoomnumber, 7, 5);

		Text lblRoomType = new Text("Room type");
		lblRoomType.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblRoomType, 5, 7);

		txtRoomType.setMinWidth(150);
		txtRoomType.setDisable(true);
		grid.add(txtRoomType, 7, 7);

		Text lblRoomState = new Text("Room state");
		lblRoomState.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblRoomState, 5, 9);

		txtRoomState.setMinWidth(150);
		txtRoomState.setDisable(true);
		grid.add(txtRoomState, 7, 9);

		Text lblPricePerDay = new Text("Price per day");
		lblPricePerDay.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblPricePerDay, 5, 11);

		txtPricePerDay.setMinWidth(150);
		txtPricePerDay.setDisable(true);
		grid.add(txtPricePerDay, 7, 11);

		Text lblTotalAmount = new Text("Total amount");
		lblTotalAmount.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblTotalAmount, 5, 13);

		TextField txtTotalAmount = new TextField();
		txtTotalAmount.setMinWidth(150);
		txtTotalAmount.setDisable(true);
		txtTotalAmount.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		grid.add(txtTotalAmount, 7, 13);

		Text lblPaymentType = new Text("Payment type");
		lblPaymentType.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblPaymentType, 5, 15);

		ComboBox txtPaymentType = new ComboBox(payment_option);
		txtPaymentType.getSelectionModel().selectFirst();
		txtPaymentType.setMinWidth(165);
		grid.add(txtPaymentType, 7, 15);

		Text lblCustomerName = new Text("Customer name");
		lblCustomerName.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblCustomerName, 17, 5);

		ComboBox cmbCustID = new ComboBox();
		cmbCustID.setMinWidth(170);
		List<String> listCust = new ArrayList<String>();
		listCust = dbfunc.getListOfCustomer();
		for (int i = 0; i <= listCust.size() - 3; i += 3)
			cmbCustID.getItems().add(listCust.get(i + 1) + ", " + listCust.get(i + 2));
		cmbCustID.getSelectionModel().selectFirst();
		grid.add(cmbCustID, 18, 5);

		Text lblServices = new Text("Services");
		lblServices.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblServices, 17, 11);

		CheckBox cbBreakFast = new CheckBox();
		cbBreakFast.setText("Breakfast");
		grid.add(cbBreakFast, 18, 11);

		CheckBox cbHouseKeep = new CheckBox();
		cbHouseKeep.setText("House keeping");
		grid.add(cbHouseKeep, 18, 13);

		CheckBox cbBuffet = new CheckBox();
		cbBuffet.setText("Dinner Buffet");
		grid.add(cbBuffet, 18, 15);

		Text lblCheckIn = new Text("Check-in time");
		lblCheckIn.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblCheckIn, 17, 7);

		DatePicker dtCheckIn = new DatePicker();
		dtCheckIn.setMinWidth(150);
		dtCheckIn.setOnAction(new EventHandler() {
			public void handle(Event t) {
				roomNo = cmbRoomnumber.getValue().toString();
				checkin = dtCheckIn.getValue();
				// String checkinDate = Util.dateToString(checkin);
				checkout = dtCheckIn.getValue();
				// String checkoutDate = Util.dateToString(checkout);

				// String paymentType = txtPaymentType.getValue().toString();

				daysSpend = ChronoUnit.DAYS.between(checkin, checkout);
				String roomType = txtRoomType.getText();
				totalAmount = getTotalAmount(daysSpend, cmbRoomnumber.getValue().toString(), txtPricePerDay.getText(),
						roomType);
				txtTotalAmount.setText(Double.toString(totalAmount));

				custName = cmbCustID.getValue().toString();
				String custSplit[] = custName.split(",");
				String first = custSplit[0].trim();
				String last = custSplit[1].trim();
				custID = dbfunc.getCustomerID(first, last);
				// dbfunc.addRegistration(checkinDate, checkoutDate, roomNo, paymentType,
				// totalAmount, custID);
				// dbfunc.updateRoomStatus(roomNo, "Occupied");
				registrationID = dbfunc.getRegistrationID(custID);
				services = new ArrayList<>();
				System.out.println(cbHouseKeep.isSelected());
				if (cbHouseKeep.isSelected())
					services.add(1);
				if (cbBreakFast.isSelected())
					services.add(2);
				if (cbBuffet.isSelected())
					services.add(3);
				dbfunc.addServiceUsed(services, registrationID);

			}
		});
		grid.add(dtCheckIn, 18, 7);

		Text lblCheckOut = new Text("Check-out time");
		lblCheckOut.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblCheckOut, 17, 9);

		DatePicker dtCheckOut = new DatePicker();
		dtCheckOut.setMinWidth(150);
		dtCheckOut.setOnAction(new EventHandler() {
			public void handle(Event t) {

				roomNo = cmbRoomnumber.getValue().toString();

				checkin = dtCheckIn.getValue();
				// String checkinDate = Util.dateToString(checkin);
				checkout = dtCheckOut.getValue();
				// String checkoutDate = Util.dateToString(checkout);

				// String paymentType = txtPaymentType.getValue().toString();

				daysSpend = ChronoUnit.DAYS.between(checkin, checkout);
				String roomType = txtRoomType.getText();
				totalAmount = getTotalAmount(daysSpend, cmbRoomnumber.getValue().toString(), txtPricePerDay.getText(),
						roomType);
				txtTotalAmount.setText(Double.toString(totalAmount));

				custName = cmbCustID.getValue().toString();
				String custSplit[] = custName.split(",");
				String first = custSplit[0].trim();
				String last = custSplit[1].trim();
				custID = dbfunc.getCustomerID(first, last);
				// dbfunc.addRegistration(checkinDate, checkoutDate, roomNo, paymentType,
				// totalAmount, custID);
				// dbfunc.updateRoomStatus(roomNo, "Booked");
				registrationID = dbfunc.getRegistrationID(custID);
				services = new ArrayList<>();
				System.out.println(cbHouseKeep.isSelected());
				if (cbHouseKeep.isSelected())
					services.add(1);
				if (cbBreakFast.isSelected())
					services.add(2);
				if (cbBuffet.isSelected())
					services.add(3);
				dbfunc.addServiceUsed(services, registrationID);

			}
		});
		grid.add(dtCheckOut, 18, 9);

		Button btnSave = new Button();
		btnSave.setText("Book");
		btnSave.setMinWidth(100);
		btnSave.setOnAction(new EventHandler() {
			public void handle(Event t) {

				roomNo = cmbRoomnumber.getValue().toString();

				checkin = dtCheckIn.getValue();
				String checkinDate = Util.dateToString(checkin);
				checkout = dtCheckOut.getValue();
				String checkoutDate = Util.dateToString(checkout);

				String paymentType = txtPaymentType.getValue().toString();

				daysSpend = ChronoUnit.DAYS.between(checkin, checkout);
				String roomType = txtRoomType.getText();
				totalAmount = getTotalAmount(daysSpend, cmbRoomnumber.getValue().toString(), txtPricePerDay.getText(),
						roomType);
				txtTotalAmount.setText(Double.toString(totalAmount));

				custName = cmbCustID.getValue().toString();
				String custSplit[] = custName.split(",");
				String first = custSplit[0].trim();
				String last = custSplit[1].trim();
				custID = dbfunc.getCustomerID(first, last);

				dbfunc.addRegistration(checkinDate, checkoutDate, roomNo, paymentType, totalAmount, custID);
				dbfunc.updateRoomStatus(roomNo, "Booked");
				int roomID = dbfunc.getRoomID(roomNo);

				registrationID = dbfunc.getRegistrationID(custID);
				dbfunc.updateRegistration(registrationID, checkinDate, checkoutDate, roomID, paymentType, totalAmount,
						custID);
				services = new ArrayList<>();
				System.out.println(cbHouseKeep.isSelected());
				if (cbHouseKeep.isSelected())
					services.add(1);
				if (cbBreakFast.isSelected())
					services.add(2);
				if (cbBuffet.isSelected())
					services.add(3);
				dbfunc.addServiceUsed(services, registrationID);

			}
		});
		grid.add(btnSave, 18, 22);

		Button btnCheckin = new Button();
		btnCheckin.setText("Check-in");
		btnCheckin.setMinWidth(100);
		btnCheckin.setOnAction(new EventHandler() {
			public void handle(Event t) {

				roomNo = cmbRoomnumber.getValue().toString();

				checkin = dtCheckIn.getValue();
				String checkinDate = Util.dateToString(checkin);
				checkout = dtCheckOut.getValue();
				String checkoutDate = Util.dateToString(checkout);

				String paymentType = txtPaymentType.getValue().toString();

				daysSpend = ChronoUnit.DAYS.between(checkin, checkout);
				String roomType = txtRoomType.getText();
				totalAmount = getTotalAmount(daysSpend, cmbRoomnumber.getValue().toString(), txtPricePerDay.getText(),
						roomType);
				txtTotalAmount.setText(Double.toString(totalAmount));

				custName = cmbCustID.getValue().toString();
				String custSplit[] = custName.split(",");
				String first = custSplit[0].trim();
				String last = custSplit[1].trim();
				custID = dbfunc.getCustomerID(first, last);

				dbfunc.addRegistration(checkinDate, checkoutDate, roomNo, paymentType, totalAmount, custID);
				dbfunc.updateRoomStatus(roomNo, "Occupied");
				int roomID = dbfunc.getRoomID(roomNo);

				registrationID = dbfunc.getRegistrationID(custID);
				dbfunc.updateRegistration(registrationID, checkinDate, checkoutDate, roomID, paymentType, totalAmount,
						custID);
				services = new ArrayList<>();
				System.out.println(cbHouseKeep.isSelected());
				if (cbHouseKeep.isSelected())
					services.add(1);
				if (cbBreakFast.isSelected())
					services.add(2);
				if (cbBuffet.isSelected())
					services.add(3);
				dbfunc.addServiceUsed(services, registrationID);

			}
		});
		grid.add(btnCheckin, 19, 22);

		Button btnCheckOut = new Button();
		btnCheckOut.setText("Check-out");
		btnCheckOut.setMinWidth(100);
		btnCheckOut.setOnAction(new EventHandler() {
			public void handle(Event t) {

				roomNo = cmbRoomnumber.getValue().toString();

				checkin = dtCheckIn.getValue();
				String checkinDate = Util.dateToString(checkin);
				checkout = dtCheckOut.getValue();
				String checkoutDate = Util.dateToString(checkout);

				String paymentType = txtPaymentType.getValue().toString();

				daysSpend = ChronoUnit.DAYS.between(checkin, checkout);
				String roomType = txtRoomType.getText();
				totalAmount = getTotalAmount(daysSpend, cmbRoomnumber.getValue().toString(), txtPricePerDay.getText(),
						roomType);
				txtTotalAmount.setText(Double.toString(totalAmount));

				custName = cmbCustID.getValue().toString();
				String custSplit[] = custName.split(",");
				String first = custSplit[0].trim();
				String last = custSplit[1].trim();
				custID = dbfunc.getCustomerID(first, last);
				dbfunc.addRegistration(checkinDate, checkoutDate, roomNo, paymentType, totalAmount, custID);
				int roomID = dbfunc.getRoomID(roomNo);

				registrationID = dbfunc.getRegistrationID(custID);
				dbfunc.updateRegistration(registrationID, checkinDate, checkoutDate, roomID, paymentType, totalAmount,
						custID);
				dbfunc.updateRoomStatus(roomNo, "Available");
				registrationID = dbfunc.getRegistrationID(custID);
				dbfunc.updateRegistration(registrationID, checkinDate, checkoutDate, roomID, paymentType, totalAmount,
						custID);
				services = new ArrayList<>();
				System.out.println(cbHouseKeep.isSelected());
				if (cbHouseKeep.isSelected())
					services.add(1);
				if (cbBreakFast.isSelected())
					services.add(2);
				if (cbBuffet.isSelected())
					services.add(3);
				dbfunc.addServiceUsed(services, registrationID);

			}
		});
		grid.add(btnCheckOut, 20, 22);

		return grid;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private GridPane addGridPaneBookRoom(RegistrationGrid rowData) {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 10, 0, 10));

		Text lblRoomNumber = new Text("Room number");
		lblRoomNumber.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblRoomNumber, 5, 5);

		ComboBox cmbRoomnumber = new ComboBox();
		for (int i = 0; i < roomNumbers.size(); i++) {
			cmbRoomnumber.getItems().add(roomNumbers.get(i).getName());
		}
		cmbRoomnumber.setMinWidth(165);
		TextField txtRoomType = new TextField();
		TextField txtRoomState = new TextField();
		TextField txtPricePerDay = new TextField();
		cmbRoomnumber.setValue(rowData.getRoomNumber());
		cmbRoomnumber.setOnAction((e) -> {
			try {
				List<String> strList = fieldSetterNew(cmbRoomnumber.getValue().toString());
				for (int i = 0; i < strList.size(); i++) {
					txtRoomType.setText(strList.get(1).toString());
					txtRoomState.setText(strList.get(2).toString());
					txtPricePerDay.setText(strList.get(3).toString());
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		grid.add(cmbRoomnumber, 7, 5);

		Text lblRoomType = new Text("Room type");
		lblRoomType.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblRoomType, 5, 7);

		txtRoomType.setMinWidth(150);
		txtRoomType.setDisable(true);
		txtRoomType.setText(rowData.getType());
		grid.add(txtRoomType, 7, 7);

		Text lblRoomState = new Text("Room state");
		lblRoomState.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblRoomState, 5, 9);

		txtRoomState.setMinWidth(150);
		txtRoomState.setDisable(true);
		txtRoomState.setText(rowData.getState());
		grid.add(txtRoomState, 7, 9);

		Text lblPricePerDay = new Text("Price per day");
		lblPricePerDay.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblPricePerDay, 5, 11);

		txtPricePerDay.setMinWidth(150);
		txtPricePerDay.setDisable(true);
		txtPricePerDay.setText(rowData.getPricePerDay());
		grid.add(txtPricePerDay, 7, 11);

		Text lblTotalAmount = new Text("Total amount");
		lblTotalAmount.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblTotalAmount, 5, 13);

		TextField txtTotalAmount = new TextField();
		txtTotalAmount.setMinWidth(150);
		txtTotalAmount.setDisable(true);
		txtTotalAmount.setFont(Font.font("Arial", FontWeight.BOLD, 15));
		txtTotalAmount.setText(rowData.getTotalPayment());
		grid.add(txtTotalAmount, 7, 13);

//		Text lblNoOfCustomer = new Text("Number of Customer");
//		lblNoOfCustomer.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
//		grid.add(lblNoOfCustomer, 5, 15);
//
//		TextField txtNoOfCustomer = new TextField();
//		txtNoOfCustomer.setMinWidth(150);
//		// txtNoOfCustomer.setText(rowData.ge);
//		grid.add(txtNoOfCustomer, 7, 15);

		Text lblPaymentType = new Text("Payment type");
		lblPaymentType.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblPaymentType, 5, 15);

		ComboBox txtPaymentType = new ComboBox(payment_option);
		txtPaymentType.setMinWidth(165);
		txtPaymentType.setValue(rowData.getPayment());
		grid.add(txtPaymentType, 7, 15);

		Text lblCustomerName = new Text("Customer name");
		lblCustomerName.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblCustomerName, 17, 5);

		ComboBox cmbCustID = new ComboBox();
		cmbCustID.setMinWidth(170);
		// cmbCustID.setValue(rowData.getFirstName());
		List<String> listCust = new ArrayList<String>();
		listCust = dbfunc.getListOfCustomer();
		for (int i = 0; i <= listCust.size() - 3; i += 3)
			cmbCustID.getItems().add(listCust.get(i + 1) + ", " + listCust.get(i + 2));
		cmbCustID.getSelectionModel().selectFirst();

		grid.add(cmbCustID, 18, 5);

		Text lblServices = new Text("Services");
		lblServices.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblServices, 17, 11);

		CheckBox cbBreakFast = new CheckBox();
		cbBreakFast.setText("Breakfast");
		grid.add(cbBreakFast, 18, 11);

		CheckBox cbHouseKeep = new CheckBox();
		cbHouseKeep.setText("House keeping");
		grid.add(cbHouseKeep, 18, 13);

		CheckBox cbBuffet = new CheckBox();
		cbBuffet.setText("Dinner Buffet");
		grid.add(cbBuffet, 18, 15);

		Text lblCheckIn = new Text("Check-in time");
		lblCheckIn.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblCheckIn, 17, 7);

		DatePicker dtCheckIn = new DatePicker();
		dtCheckIn.setMinWidth(150);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String monthTemp = rowData.getCheckinDate().substring(0, 2);
		String dayTemp = rowData.getCheckinDate().substring(3, 5);
		String yearTemp = rowData.getCheckinDate().substring(6, 10);
		String dateTemp = monthTemp + "/" + dayTemp + "/" + yearTemp;
		dtCheckIn.setValue(LocalDate.parse(dateTemp, formatter));
		dtCheckIn.setOnAction(new EventHandler() {
			public void handle(Event t) {
				roomNo = cmbRoomnumber.getValue().toString();
				checkin = dtCheckIn.getValue();
				// String checkinDate = Util.dateToString(checkin);
				checkout = dtCheckIn.getValue();
				// String checkoutDate = Util.dateToString(checkout);

				// String paymentType = txtPaymentType.getValue().toString();

				daysSpend = ChronoUnit.DAYS.between(checkin, checkout);
				String roomType = txtRoomType.getText();
				totalAmount = getTotalAmount(daysSpend, cmbRoomnumber.getValue().toString(), txtPricePerDay.getText(),
						roomType);
				txtTotalAmount.setText(Double.toString(totalAmount));

				custName = cmbCustID.getValue().toString();
				String custSplit[] = custName.split(",");
				String first = custSplit[0].trim();
				String last = custSplit[1].trim();
				custID = dbfunc.getCustomerID(first, last);
				// dbfunc.addRegistration(checkinDate, checkoutDate, roomNo, paymentType,
				// totalAmount, custID);
				// dbfunc.updateRoomStatus(roomNo, "Occupied");
				registrationID = dbfunc.getRegistrationID(custID);
				services = new ArrayList<>();
				System.out.println(cbHouseKeep.isSelected());
				if (cbHouseKeep.isSelected())
					services.add(1);
				if (cbBreakFast.isSelected())
					services.add(2);
				if (cbBuffet.isSelected())
					services.add(3);
				dbfunc.addServiceUsed(services, registrationID);

			}
		});
		grid.add(dtCheckIn, 18, 7);

		Text lblCheckOut = new Text("Check-out time");
		lblCheckOut.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
		grid.add(lblCheckOut, 17, 9);

		DatePicker dtCheckOut = new DatePicker();
		dtCheckOut.setMinWidth(150);
		monthTemp = rowData.getCheckoutDate().substring(0, 2);
		dayTemp = rowData.getCheckoutDate().substring(3, 5);
		yearTemp = rowData.getCheckoutDate().substring(6, 10);
		dateTemp = monthTemp + "/" + dayTemp + "/" + yearTemp;
		dtCheckOut.setValue(LocalDate.parse(dateTemp, formatter));
		dtCheckOut.setOnAction(new EventHandler() {
			public void handle(Event t) {
				roomNo = cmbRoomnumber.getValue().toString();
				checkin = dtCheckIn.getValue();
				// String checkinDate = Util.dateToString(checkin);
				checkout = dtCheckOut.getValue();
				// String checkoutDate = Util.dateToString(checkout);

				// String paymentType = txtPaymentType.getValue().toString();

				daysSpend = ChronoUnit.DAYS.between(checkin, checkout);
				String roomType = txtRoomType.getText();
				totalAmount = getTotalAmount(daysSpend, cmbRoomnumber.getValue().toString(), txtPricePerDay.getText(),
						roomType);
				txtTotalAmount.setText(Double.toString(totalAmount));

				custName = cmbCustID.getValue().toString();
				String custSplit[] = custName.split(",");
				String first = custSplit[0].trim();
				String last = custSplit[1].trim();
				custID = dbfunc.getCustomerID(first, last);
				// dbfunc.addRegistration(checkinDate, checkoutDate, roomNo, paymentType,
				// totalAmount, custID);
				// dbfunc.updateRoomStatus(roomNo, "Booked");
				registrationID = dbfunc.getRegistrationID(custID);
				services = new ArrayList<>();
				System.out.println(cbHouseKeep.isSelected());
				if (cbHouseKeep.isSelected())
					services.add(1);
				if (cbBreakFast.isSelected())
					services.add(2);
				if (cbBuffet.isSelected())
					services.add(3);
				dbfunc.addServiceUsed(services, registrationID);

			}
		});
		grid.add(dtCheckOut, 18, 9);

		Button btnSave = new Button();
		btnSave.setText("Book");
		btnSave.setMinWidth(100);
		btnSave.setOnAction(new EventHandler() {
			public void handle(Event t) {

				roomNo = cmbRoomnumber.getValue().toString();

				checkin = dtCheckIn.getValue();
				String checkinDate = Util.dateToString(checkin);
				checkout = dtCheckOut.getValue();
				String checkoutDate = Util.dateToString(checkout);

				String paymentType = txtPaymentType.getValue().toString();

				daysSpend = ChronoUnit.DAYS.between(checkin, checkout);
				String roomType = txtRoomType.getText();
				totalAmount = getTotalAmount(daysSpend, cmbRoomnumber.getValue().toString(), txtPricePerDay.getText(),
						roomType);
				txtTotalAmount.setText(Double.toString(totalAmount));

				custName = cmbCustID.getValue().toString();
				String custSplit[] = custName.split(",");
				String first = custSplit[0].trim();
				String last = custSplit[1].trim();
				custID = dbfunc.getCustomerID(first, last);

				dbfunc.addRegistration(checkinDate, checkoutDate, roomNo, paymentType, totalAmount, custID);
				dbfunc.updateRoomStatus(roomNo, "Booked");
				int roomID = dbfunc.getRoomID(roomNo);

				registrationID = dbfunc.getRegistrationID(custID);
				dbfunc.updateRegistration(registrationID, checkinDate, checkoutDate, roomID, paymentType, totalAmount,
						custID);
				services = new ArrayList<>();
				System.out.println(cbHouseKeep.isSelected());
				if (cbHouseKeep.isSelected())
					services.add(1);
				if (cbBreakFast.isSelected())
					services.add(2);
				if (cbBuffet.isSelected())
					services.add(3);
				dbfunc.addServiceUsed(services, registrationID);

			}
		});
		grid.add(btnSave, 18, 22);

		Button btnCheckin = new Button();
		btnCheckin.setText("Check-in");
		btnCheckin.setMinWidth(100);
		btnCheckin.setOnAction(new EventHandler() {
			public void handle(Event t) {

				roomNo = cmbRoomnumber.getValue().toString();

				checkin = dtCheckIn.getValue();
				String checkinDate = Util.dateToString(checkin);
				checkout = dtCheckOut.getValue();
				String checkoutDate = Util.dateToString(checkout);

				String paymentType = txtPaymentType.getValue().toString();

				daysSpend = ChronoUnit.DAYS.between(checkin, checkout);
				String roomType = txtRoomType.getText();
				totalAmount = getTotalAmount(daysSpend, cmbRoomnumber.getValue().toString(), txtPricePerDay.getText(),
						roomType);
				txtTotalAmount.setText(Double.toString(totalAmount));

				custName = cmbCustID.getValue().toString();
				String custSplit[] = custName.split(",");
				String first = custSplit[0].trim();
				String last = custSplit[1].trim();
				custID = dbfunc.getCustomerID(first, last);

				dbfunc.addRegistration(checkinDate, checkoutDate, roomNo, paymentType, totalAmount, custID);
				dbfunc.updateRoomStatus(roomNo, "Occupied");
				int roomID = dbfunc.getRoomID(roomNo);

				registrationID = dbfunc.getRegistrationID(custID);
				dbfunc.updateRegistration(registrationID, checkinDate, checkoutDate, roomID, paymentType, totalAmount,
						custID);
				services = new ArrayList<>();
				System.out.println(cbHouseKeep.isSelected());
				if (cbHouseKeep.isSelected())
					services.add(1);
				if (cbBreakFast.isSelected())
					services.add(2);
				if (cbBuffet.isSelected())
					services.add(3);
				dbfunc.addServiceUsed(services, registrationID);

			}
		});
		grid.add(btnCheckin, 19, 22);

		Button btnCheckOut = new Button();
		btnCheckOut.setText("Check-out");
		btnCheckOut.setMinWidth(100);
		btnCheckOut.setOnAction(new EventHandler() {
			public void handle(Event t) {

				roomNo = cmbRoomnumber.getValue().toString();

				checkin = dtCheckIn.getValue();
				String checkinDate = Util.dateToString(checkin);
				checkout = dtCheckOut.getValue();
				String checkoutDate = Util.dateToString(checkout);

				String paymentType = txtPaymentType.getValue().toString();

				daysSpend = ChronoUnit.DAYS.between(checkin, checkout);
				String roomType = txtRoomType.getText();
				totalAmount = getTotalAmount(daysSpend, cmbRoomnumber.getValue().toString(), txtPricePerDay.getText(),
						roomType);
				txtTotalAmount.setText(Double.toString(totalAmount));

				custName = cmbCustID.getValue().toString();
				String custSplit[] = custName.split(",");
				String first = custSplit[0].trim();
				String last = custSplit[1].trim();
				custID = dbfunc.getCustomerID(first, last);

				dbfunc.addRegistration(checkinDate, checkoutDate, roomNo, paymentType, totalAmount, custID);
				dbfunc.updateRoomStatus(roomNo, "Available");
				int roomID = dbfunc.getRoomID(roomNo);

				registrationID = dbfunc.getRegistrationID(custID);
				dbfunc.updateRegistration(registrationID, checkinDate, checkoutDate, roomID, paymentType, totalAmount,
						custID);
				services = new ArrayList<>();
				System.out.println(cbHouseKeep.isSelected());
				if (cbHouseKeep.isSelected())
					services.add(1);
				if (cbBreakFast.isSelected())
					services.add(2);
				if (cbBuffet.isSelected())
					services.add(3);
				dbfunc.addServiceUsed(services, registrationID);

			}
		});
		grid.add(btnCheckOut, 20, 22);

		return grid;
	}

	public List<String> fieldSetterNew(String el) throws SQLException {
		List<String> strList = new ArrayList<>();
		strList = dbfunc.getRoomAttributesByRoomNo(el);
		return strList;
	}

	// delegation usage
	public double getTotalAmount(long spendDays, String roomNo, String price, String roomType) {
		double total = 0;
		if (roomType.equalsIgnoreCase("Single"))
			total = roomObj.getTotalPayableAmount(spendDays, roomNo, price);
		else if (roomType.equalsIgnoreCase("Double"))
			total = roomObjDouble.getTotalPayableAmount(spendDays, roomNo, price);
		else
			total = roomObjDeluxe.getTotalPayableAmount(spendDays, roomNo, price);

		return total;
	}

}

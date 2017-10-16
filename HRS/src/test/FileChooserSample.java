package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.MysqlConnection;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FileChooserSample extends Application {

	private TableView<Person> table = new TableView<Person>();
	private static final ObservableList<Person> data = FXCollections.observableArrayList();


	final HBox hb = new HBox();

	public void addFields(String name, String pass, String role) throws SQLException {
		MysqlConnection db;
		db = MysqlConnection.getDbCon();

		String query = "INSERT INTO employee(ID, Username, Password, Role, LastName, Address, SSN, FistName)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		 PreparedStatement preparedStatement = db.conn.prepareStatement(query);
		 preparedStatement.setString(1, "101");
		 preparedStatement.setString(2, "Joker");
		 preparedStatement.setString(3, "123");
		 preparedStatement.setString(4, "1");
		 preparedStatement.setString(5, "Batman");
		 preparedStatement.setString(6, "Fairfield, IA");
		 preparedStatement.setString(7, "1238456");
		 preparedStatement.setString(8, "Joker");
		 
		 preparedStatement.executeUpdate();

	}

	public void dbCon() throws SQLException {
		MysqlConnection db;
		db = MysqlConnection.getDbCon();
		ResultSet rs = db.query("select * from employee");
		while (rs.next()) {
			data.add(new Person(rs.getString(2), rs.getString(3), rs.getString(4)));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(Stage stage) throws SQLException {
		Scene scene = new Scene(new Group());
		stage.setTitle("Table View Sample");
		stage.setWidth(450);
		stage.setHeight(550);

		final Label label = new Label("Address Book");
		label.setFont(new Font("Arial", 20));

		table.setEditable(true);

		TableColumn firstNameCol = new TableColumn("First Name");
		firstNameCol.setMinWidth(100);
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

		TableColumn lastNameCol = new TableColumn("Last Name");
		lastNameCol.setMinWidth(100);
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

		TableColumn emailCol = new TableColumn("Email");
		emailCol.setMinWidth(200);
		emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));

		table.setItems(data);
		table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

		final TextField addFirstName = new TextField();
		addFirstName.setPromptText("First Name");
		addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
		final TextField addLastName = new TextField();
		addLastName.setMaxWidth(lastNameCol.getPrefWidth());
		addLastName.setPromptText("Last Name");
		final TextField addEmail = new TextField();
		addEmail.setMaxWidth(emailCol.getPrefWidth());
		addEmail.setPromptText("Email");

		final Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				data.add(new Person(addFirstName.getText(), addLastName.getText(), addEmail.getText()));
				addFirstName.clear();
				addLastName.clear();
				addEmail.clear();

				try {
					addFields(addFirstName.getText(), addLastName.getText(), addEmail.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		hb.getChildren().addAll(addFirstName, addLastName, addEmail, addButton);
		hb.setSpacing(3);
		dbCon();
		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table, hb);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();
	}

	public static class Person {

		private final SimpleStringProperty firstName;
		private final SimpleStringProperty lastName;
		private final SimpleStringProperty email;

		private Person(String fName, String lName, String email) {
			this.firstName = new SimpleStringProperty(fName);
			this.lastName = new SimpleStringProperty(lName);
			this.email = new SimpleStringProperty(email);
		}

		public String getFirstName() {
			return firstName.get();
		}

		public void setFirstName(String fName) {
			firstName.set(fName);
		}

		public String getLastName() {
			return lastName.get();
		}

		public void setLastName(String fName) {
			lastName.set(fName);
		}

		public String getEmail() {
			return email.get();
		}

		public void setEmail(String fName) {
			email.set(fName);
		}
	}
}

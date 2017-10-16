package hrs;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import connection.MysqlConnection;

public class Login extends Application {

	public static Screen screen = Screen.getPrimary();
	public static Rectangle2D bounds = screen.getVisualBounds();
	Label lblUserName = new Label();
	Label lblPassword = new Label();
	TextField txtUserName = new TextField();
	PasswordField txtPassword = new PasswordField();

	public static void main(String[] args) {
		launch(args);

	}

	GridPane grid1 = null;
	Text messageBar = new Text();

	ToggleButton login = new ToggleButton("Login");
	Stage primaryStage;
	Stage adminWindow;
	Stage managerWindow;
	Stage receptionWindow;

	private void createAdminWindow() {
		adminWindow = new Admin(primaryStage);
		// messageBar.setText("");
	}

	private void createManagerWindow() throws SQLException {
		managerWindow = new Manager(primaryStage);
	}

	private void createReceptionWindow() {
		receptionWindow = new Reception(primaryStage);
	}

	@Override
	public void start(Stage primaryStage) throws SQLException {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("The Hotel Reservation System");

		messageBar.setFill(Color.RED);

		class MessageBarHandler implements EventHandler<ActionEvent> {
			@Override
			public void handle(ActionEvent e) {
				// messageBar.setText("");
				String str = "";
				try {
					str = checkValidity();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				switch (str) {
				case "0":
					createReceptionWindow();
					primaryStage.hide();
					receptionWindow.show();
					break;
				case "1":
					createAdminWindow();
					primaryStage.hide();
					adminWindow.show();
					break;
				case "2":
					try {
						createManagerWindow();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					primaryStage.hide();
					managerWindow.show();
					break;
				case "3":
					createReceptionWindow();
					primaryStage.hide();
					receptionWindow.show();
					break;
				default:
					messageBar.setText("Wrong username/password");
				}

			}
		}

		login.setOnAction(new MessageBarHandler());

		lblUserName.setText("Username");

		lblPassword.setText("Password");

		messageBar.setText("");

		// Arrange buttons in a 2x1 grid
		grid1 = new GridPane();
		grid1.setAlignment(Pos.CENTER);
		grid1.setVgap(10); // sets vertical gap between buttons
		grid1.add(lblUserName, 0, 0);
		grid1.add(txtUserName, 1, 0);
		grid1.add(lblPassword, 0, 1);
		grid1.add(txtPassword, 1, 1);

		login.setAlignment(Pos.BASELINE_LEFT);

		grid1.add(login, 1, 2);
		grid1.add(messageBar, 1, 3);

		Scene scene = new Scene(grid1, bounds.getWidth(), bounds.getHeight());

		positionSceneInStage(primaryStage, scene);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public String checkValidity() throws SQLException {

		MysqlConnection db;
		db = MysqlConnection.getDbCon();
		String ret = "";
		ResultSet rs = db.query("select employee.username,employee.password,employee.role from employee");

		while (rs.next()) {
			if (txtUserName.getText().toString().equals(rs.getString(1))
					&& txtPassword.getText().toString().equals(rs.getString(2))) {
				ret = rs.getString(3);
			}
		}
		return ret;
	}

	void positionSceneInStage(Stage stage, Scene scene) {
		stage.setX(200);
		stage.setY(200);
	}

	public void init() {
		// System.out.println("Starting...");
	}

	public void stop() {
		// System.out.println("Stopping...");
	}
}

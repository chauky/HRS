package dataPool;

import javafx.beans.property.SimpleStringProperty;

public class EmployeeListGrid {
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	private SimpleStringProperty position;
	private SimpleStringProperty ssn;
	private SimpleStringProperty address;
	private SimpleStringProperty userName;

	public EmployeeListGrid() {

	}

	public EmployeeListGrid(String firstName, String lastName, String position, String ssn, String address,
			String userName) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.position = new SimpleStringProperty(position);
		this.ssn = new SimpleStringProperty(ssn);
		this.address = new SimpleStringProperty(address);
		this.userName = new SimpleStringProperty(userName);
	}

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public String getPosition() {
		return position.get();
	}

	public void setPosition(String position) {
		this.position.set(position);
	}

	public String getSSN() {
		return ssn.get();
	}

	public void setSSN(String ssn) {
		this.ssn.set(ssn);
	}

	public String getAddress() {
		return address.get();
	}

	public void setAddress(String address) {
		this.address.set(address);
	}

	public String getUserName() {
		return userName.get();
	}

	public void setUserName(String userName) {
		this.userName.set(userName);
	}
}

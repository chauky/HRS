package dataPool;

import javafx.beans.property.SimpleStringProperty;

public class RegistrationGrid {
	private SimpleStringProperty checkinDate;
	private SimpleStringProperty checkoutDate;
	private SimpleStringProperty payment;
	private SimpleStringProperty totalPayment;
	private SimpleStringProperty roomNumber;
	private SimpleStringProperty type;
	private SimpleStringProperty state;
	private SimpleStringProperty pricePerDay;
	private SimpleStringProperty firstName;
	private SimpleStringProperty lastName;
	private SimpleStringProperty address;
	private SimpleStringProperty phoneNumber;

	public RegistrationGrid() {

	}

	public RegistrationGrid(String checkinDate, String checkoutDate, String payment, String totalPayment,
			String roomNumber, String type, String state, String pricePerDay, String firstName, String lastName,
			String address, String phoneNumber) {
		this.checkinDate = new SimpleStringProperty(checkinDate);
		this.checkoutDate = new SimpleStringProperty(checkoutDate);
		this.payment = new SimpleStringProperty(payment);
		this.totalPayment = new SimpleStringProperty(totalPayment);
		this.roomNumber = new SimpleStringProperty(roomNumber);
		this.type = new SimpleStringProperty(type);
		this.state = new SimpleStringProperty(state);
		this.pricePerDay = new SimpleStringProperty(pricePerDay);
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.address = new SimpleStringProperty(address);
		this.phoneNumber = new SimpleStringProperty(phoneNumber);
	}
	
	public String getCheckinDate() {
		return checkinDate.get();
	}

	public void setCheckinDate(String checkinDate) {
		this.checkinDate.set(checkinDate);
	}

	public String getCheckoutDate() {
		return checkoutDate.get();
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate.set(checkoutDate);
	}
	
	public String getPayment() {
		return payment.get();
	}

	public void setPayment(String payment) {
		this.payment.set(payment);
	}
	
	public String getTotalPayment() {
		return totalPayment.get();
	}

	public void setTotalPayment(String totalPayment) {
		this.totalPayment.set(totalPayment);
	}
	
	public String getRoomNumber() {
		return roomNumber.get();
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber.set(roomNumber);
	}
	
	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}
	
	public String getState() {
		return state.get();
	}

	public void setState(String state) {
		this.state.set(state);
	}
	
	public String getPricePerDay() {
		return pricePerDay.get();
	}

	public void setPricePerDay(String pricePerDay) {
		this.pricePerDay.set(pricePerDay);
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
	
	public String getAddress() {
		return address.get();
	}

	public void setAddress(String address) {
		this.address.set(address);
	}
	
	public String getPhoneNumber() {
		return phoneNumber.get();
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}
}

package dataPool;

import javafx.beans.property.SimpleStringProperty;

public class RoomGrid {
	// remove final for variables
	private SimpleStringProperty name;
	private SimpleStringProperty roomType;
	private SimpleStringProperty state;
	private SimpleStringProperty pricePerDay;
	private SimpleStringProperty customer;
	private SimpleStringProperty checkin;
	private SimpleStringProperty checkout;

	public RoomGrid() {

	}

	public RoomGrid(String name, String type, String state, String pricePerDay, String customer, String checkin,
			String checkout) {
		this.name = new SimpleStringProperty(name);
		this.roomType = new SimpleStringProperty(type);
		this.state = new SimpleStringProperty(state);
		this.pricePerDay = new SimpleStringProperty(pricePerDay);
		this.customer = new SimpleStringProperty(customer);
		this.checkin = new SimpleStringProperty(checkin);
		this.checkout = new SimpleStringProperty(checkout);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getRoomType() {
		return roomType.get();
	}

	public void setRoomType(String roomType) {
		this.roomType.set(roomType);
	}

	public String getState() {
		return this.state.get();
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

	public String getCustomer() {
		return customer.get();
	}

	public void setCustomer(String customer) {
		this.customer.set(customer);
	}

	public String getCheckin() {
		return checkin.get();
	}

	public void setCheckin(String checkin) {
		this.checkin.set(checkin);
	}

	public String getCheckout() {
		return checkout.get();
	}

	public void setCheckout(String checkout) {
		this.checkout.set(checkout);
	}

}
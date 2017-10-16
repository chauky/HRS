package dataPool;

import javafx.beans.property.SimpleStringProperty;

public class RoomCombo {
	private SimpleStringProperty name;

	public RoomCombo(String name) {
		this.name = new SimpleStringProperty(name);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
}
package room;

import hrs.Registration;

public class DeluxeRoom extends Room {
	Registration regObj = new Registration();

	public double getTotalPayableAmount(long spendDays, String roomNo, String price) {
		double total = 0.0;
		double discount = 0.0;
		if (spendDays > 5) {
			discount = 0;
			discount = (Double.parseDouble(price) * 5 * spendDays) / 100;
			total = regObj.getServicesIncluded() + spendDays * Double.parseDouble(price) - discount;
		} else {
			total = regObj.getServicesIncluded() + spendDays * Double.parseDouble(price);
		}

		return total;
	}
}

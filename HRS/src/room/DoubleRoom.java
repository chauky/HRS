package room;

import hrs.Registration;

public class DoubleRoom extends Room {
	Registration regObj = new Registration();

	public double getTotalPayableAmount(long spendDays, String roomNo, String price) {
		double total = 0.0;
		double discount = 0.0;
		if (spendDays > 7) {
			discount = (Double.parseDouble(price) * spendDays * 5) / 100;
			total = regObj.getServicesIncluded() + spendDays * Double.parseDouble(price) - discount;
		} else {
			total = regObj.getServicesIncluded() + spendDays * Double.parseDouble(price);
		}
		return total;
	}
}

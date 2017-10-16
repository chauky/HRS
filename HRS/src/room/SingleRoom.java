package room;

import hrs.Registration;

// delegation original
public class SingleRoom extends Room {
	Registration regObj = new Registration();

	public double getTotalPayableAmount(long spendDays, String roomNo, String price) {
		double total = 0.0;
		total = regObj.getServicesIncluded() + spendDays * Double.parseDouble(price);
		return total;
	}
}

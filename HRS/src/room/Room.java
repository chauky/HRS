package room;

public abstract class Room {
	double totalPayment = 0.0;
	public abstract double getTotalPayableAmount(long spendDays,String roomNo, String price);
}

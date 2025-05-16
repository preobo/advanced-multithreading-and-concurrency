package tradingSystem;

public class Order {
	private String traderId;
	private boolean isBuy;
	private String symbol;
	private int quantity;
	private double price;

	public Order(String traderId, boolean isBuy, String symbol, int quantity, double price) {
		this.traderId  = traderId;
		this.isBuy = isBuy;
		this.symbol = symbol;
		this.quantity = quantity;
		this.price = price;
	}
	public void reduceQuantity(int amount) {
	    if (amount > quantity) {
	        throw new IllegalArgumentException("Cannot reduce more than available");
	    }
	    quantity -= amount;
	}

	public String getTraderId() {
		return traderId;
	}
	public boolean getIsBuy() {
		return isBuy;
	}
	public String getSymbol() {
		return symbol;
	}
	public int getQuantity() {
		return quantity;
	}
	public double getPrice() {
		return price;
	}
	public String toString() {
		return String.format("Order[%s %s %d %s @$%.2f]", traderId, isBuy ? "BUY" : "SELL", quantity, symbol,price);
		
	}

}



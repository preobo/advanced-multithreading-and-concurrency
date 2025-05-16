package tradingSystem;

public class Stock {
	private String symbol;
	private double price;
	private int availableQuantity;

	Stock(String symbol, double price, int quantity){
		this.symbol = symbol;
		this.price = price;
		this.availableQuantity = quantity;
	}
		
	public synchronized boolean buy(int quantity) {
		if(quantity<= availableQuantity) {
			availableQuantity -= quantity;
			return true;
		}else {
			return false; //not enough stocks 
		}
		
	}
	public synchronized void sell(int quantity) {
		availableQuantity += quantity;
		
	}
	public String getSymbol() {
		return symbol;
	}
	public double getPrice() {
		return price;
	}
	public int getAvailableQuantity() {
		return availableQuantity;
	}
}


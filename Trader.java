package tradingSystem;

import java.util.Random;

public class Trader implements Runnable {
	private String traderId;
	private StockExchange stockExchange;
	
	Trader(String traderId, StockExchange stockExchange){
		this.traderId = traderId;
		this.stockExchange = stockExchange;
	}
	

	@Override
	public void run() {
		// Creates a random number generator to be used throughout the loop
		Random random = new Random();
		String[] stockSymbols = {"AAPL", "GOOGL","TSLA","AMZN"};
		while(true) {
			try {
				//randomly decided if this is a BUY or SELL
				boolean isBuy = random.nextBoolean(); // true = BUY, false = SELL
				
				//Picking a random stock symbol
				String symbol = stockSymbols[random.nextInt(stockSymbols.length)];
				
				//Picking a random quantity (e.g., 1 - 100)
				int quantity = random.nextInt(100) + 1 ;
				
				//Pick a random price (between 50 and 150)
				double price = 50 +(100 * random.nextDouble());
				
				//New order object
				Order order = new Order(traderId,isBuy,symbol,quantity,price);
				
				//Place the order through the StockExchange class
				stockExchange.placeOrder(order);
				
				//1-4 seconds simulating time between trades 
				Thread.sleep(random.nextInt(3000) + 1000);
				
				//Catches an exception when thread is interrupted.
			} catch (InterruptedException e) {
				System.out.println("Trader " + traderId + " was interrupted.");
				
				//Restoring interrupted status 
				Thread.currentThread().interrupt(); 
				break;
			}
		
		}
			
	}
		
}

	


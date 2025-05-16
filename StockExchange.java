package tradingSystem;
import java.util.HashMap;
import java.util.PriorityQueue;

public class StockExchange {
	//Tracking all the stocks 
	private HashMap<String, Stock> stocks = new HashMap<>();
	//Tracking buy orders per stock
	private HashMap<String,PriorityQueue<Order>> buyOrders = new HashMap<>();
	//Tracking sell orders per stock 
	private HashMap<String, PriorityQueue<Order>> sellOrders = new HashMap<>();

	public void placeOrder(Order order) {
		String symbol = order.getSymbol();
		//Checking if stock is available to purchase or not 
		if(!stocks.containsKey(symbol)){
			System.out.println("Invalid stock symbol: " + symbol);
			return;
		}
		synchronized (stocks.get(symbol)){
			if(order.getIsBuy()) { //Checking if it's a buy order 
				PriorityQueue<Order> sellQueue = sellOrders.get(symbol);//If so, find someone who is selling
				
				while(order.getQuantity()> 0 && //Getting the lowest price and checking if its less or equal to what the buyer is willing to pay 
						!sellQueue.isEmpty() && sellQueue.peek().getPrice() <= order.getPrice()) {
					//Grabs the first order in the sell queue
					Order bestSell = sellQueue.peek();
					//Checks how many shares can actually be traded
					int tradedQty = Math.min(order.getQuantity(), bestSell.getQuantity());
					//Using the sellers price to complete the trade
					double tradePrice = bestSell.getPrice();
					
					System.out.printf("TRADE: %s sells %d shares of %s to %s at $%.2f%n", order.getTraderId(), tradedQty, symbol, bestSell.getTraderId(), tradePrice);
					
					order.reduceQuantity(tradedQty);
					bestSell.reduceQuantity(tradedQty);
					
					if (bestSell.getQuantity() == 0) {
						sellQueue.poll();
					}
				}
				
				if (order.getQuantity() > 0) {
					buyOrders.get(symbol).add(order);
				}
				
			} else {
				PriorityQueue<Order> buyQueue = buyOrders.get(symbol);
				
				while (order.getQuantity() > 0 && !buyQueue.isEmpty() && buyQueue.peek().getPrice() >= order.getPrice()) {
					Order bestBuy = buyQueue.peek();
					int tradedQty = Math.min(order.getQuantity(), bestBuy.getQuantity());
					double tradePrice = bestBuy.getPrice();
					
					System.out.printf("TRADE: %s sells %d shares of %s to %s at $%.2f%n", order.getTraderId(), tradedQty, symbol, bestBuy.getTraderId(), tradePrice);
					
					order.reduceQuantity(tradedQty);
					bestBuy.reduceQuantity(tradedQty);
					
					if (bestBuy.getQuantity() == 0) {
						buyQueue.poll();
					}
				}
				if (order.getQuantity() > 0) {
					sellOrders.get(symbol).add(order);
				}
			}
			
		}
	}
	public void addStock(Stock stock) {
		String symbol = stock.getSymbol();
		stocks.put(symbol, stock); //Storing the stock in the HashMap
		
		//Setting buy queue: high price first
		//Putting buyOrders in the queue and comparing (.compare) the prices.
		
		buyOrders.put(symbol,  new PriorityQueue<>((o1, o2) -> Double.compare(o2.getPrice(), o1.getPrice())));
		sellOrders.put(symbol, new PriorityQueue<>((o1, o2)-> Double.compare(o1.getPrice(), o2.getPrice())));
	}
	

}



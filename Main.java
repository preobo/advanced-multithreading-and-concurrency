package tradingSystem;

public class Main {
    public static void main(String[] args) {
        
        StockExchange exchange = new StockExchange();

        
        exchange.addStock(new Stock("AAPL", 150.0, 1000));
        exchange.addStock(new Stock("GOOGL", 2800.0, 500));
        exchange.addStock(new Stock("TSLA", 700.0, 300));
        exchange.addStock(new Stock("AMZN", 3200.0, 400));


        
        Thread trader1 = new Thread(new Trader("Trader1", exchange));
        Thread trader2 = new Thread(new Trader("Trader2", exchange));
        Thread trader3 = new Thread(new Trader("Trader3", exchange));
        Thread trader4 = new Thread(new Trader("Trader4", exchange));

        trader1.start();
        trader2.start();
        trader3.start();
        trader4.start();

        
        try {
            Thread.sleep(10000); // 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

                System.out.println("Simulation ended.");
    }
}

import java.util.*;

public class StockTradingPlatform {
    private static final Map<String, Double> marketData = new HashMap<>();
    private static final Map<String, Integer> portfolio = new HashMap<>();
    private static final List<String> transactionHistory = new ArrayList<>();
    private static double cashBalance = 10000.0;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            initializeMarketData();
            boolean continueTrading = true;
            
            while (continueTrading) {
                System.out.println("\nWelcome to the Stock Trading Platform!");
                System.out.println("1. View Market Data");
                System.out.println("2. Buy Stocks");
                System.out.println("3. Sell Stocks");
                System.out.println("4. View Portfolio");
                System.out.println("5. Update Market Data");
                System.out.println("6. View Transaction History");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1 -> viewMarketData();
                    case 2 -> buyStocks(scanner);
                    case 3 -> sellStocks(scanner);
                    case 4 -> viewPortfolio();
                    case 5 -> updateMarketData(scanner);
                    case 6 -> viewTransactionHistory();
                    case 7 -> continueTrading = false;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static void initializeMarketData() {
        marketData.put("APPLE", 150.00);
        marketData.put("GOOGLE", 2800.00);
        marketData.put("AMAZON", 3500.00);
        marketData.put("MICROSOFT", 300.00);
        marketData.put("TESLA", 700.00);
    }

    private static void viewMarketData() {
        System.out.println("Market Data:");
        for (Map.Entry<String, Double> entry : marketData.entrySet()) {
            System.out.println(entry.getKey() + ": $" + entry.getValue());
        }
    }

    private static void buyStocks(Scanner scanner) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next().toUpperCase();
        if (marketData.containsKey(symbol)) {
            System.out.print("Enter quantity to buy: ");
            int quantity = scanner.nextInt();
            double cost = marketData.get(symbol) * quantity;
            if (cost <= cashBalance) {
                cashBalance -= cost;
                portfolio.put(symbol, portfolio.getOrDefault(symbol, 0) + quantity);
                transactionHistory.add("Bought " + quantity + " shares of " + symbol + " at $" + marketData.get(symbol));
                System.out.println("Bought " + quantity + " shares of " + symbol);
                System.out.println("Remaining cash balance: $" + cashBalance);
            } else {
                System.out.println("Insufficient funds to buy " + quantity + " shares of " + symbol);
            }
        } else {
            System.out.println("Invalid stock symbol.");
        }
    }

    private static void sellStocks(Scanner scanner) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next().toUpperCase();
        if (portfolio.containsKey(symbol)) {
            System.out.print("Enter quantity to sell: ");
            int quantity = scanner.nextInt();
            int currentQuantity = portfolio.get(symbol);
            if (quantity <= currentQuantity) {
                double proceeds = marketData.get(symbol) * quantity;
                cashBalance += proceeds;
                if (quantity == currentQuantity) {
                    portfolio.remove(symbol);
                } else {
                    portfolio.put(symbol, currentQuantity - quantity);
                }
                transactionHistory.add("Sold " + quantity + " shares of " + symbol + " at $" + marketData.get(symbol));
                System.out.println("Sold " + quantity + " shares of " + symbol);
                System.out.println("New cash balance: $" + cashBalance);
            } else {
                System.out.println("You do not own enough shares of " + symbol);
            }
        } else {
            System.out.println("You do not own any shares of " + symbol);
        }
    }

    private static void viewPortfolio() {
        System.out.println("Portfolio:");
        double portfolioValue = cashBalance;
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            double stockValue = marketData.get(entry.getKey()) * entry.getValue();
            portfolioValue += stockValue;
            System.out.println(entry.getKey() + ": " + entry.getValue() + " shares ($" + stockValue + ")");
        }
        System.out.println("Cash balance: $" + cashBalance);
        System.out.println("Total portfolio value: $" + portfolioValue);
    }

    private static void updateMarketData(Scanner scanner) {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next().toUpperCase();
        if (marketData.containsKey(symbol)) {
            System.out.print("Enter new price: ");
            double newPrice = scanner.nextDouble();
            marketData.put(symbol, newPrice);
            System.out.println("Updated price of " + symbol + " to $" + newPrice);
        } else {
            System.out.println("Invalid stock symbol.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}
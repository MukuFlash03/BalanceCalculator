package src;

import java.io.File;

public class AppMain {

    public static void main(String[] args) {
		long startTime = System.nanoTime(); 
        String filePath = args[0];

		File transactionFile = new File(filePath);
        ParseTransaction parser = new ParseTransaction();
        parser.parseTransactionFile(transactionFile);

		Evaluator evaluator = new Evaluator();
		evaluator.computeBalances();

        ParseBalance writeBalance = new ParseBalance();
        writeBalance.writeToFile();

		long finishTime = System.nanoTime();
        double executionTime = (double)(finishTime - startTime) / 1000000000.0;
		System.out.println("Execution time: " + executionTime + " seconds");
    }

}
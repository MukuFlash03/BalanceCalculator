package src;

import java.io.File;
import java.text.ParseException;

public class AppMain {

    public static void main(String[] args) throws ParseException {
		long startTime = System.nanoTime(); 
        String filePath = args[0];

		File transactionFile = new File(filePath);
        ParseTransaction parser = new ParseTransaction();
        parser.parseTransactionFile(transactionFile);

		Evaluator evaluator = new Evaluator();
		evaluator.computeBalances();
        evaluator.parseDates();


		long finishTime = System.nanoTime();
        double executionTime = (double)(finishTime - startTime) / 1000000000.0;
		System.out.println("Execution time: " + executionTime + " seconds");
    }

}
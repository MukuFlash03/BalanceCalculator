package src;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

public class ParseTransaction {

    private Scanner myReader;
    private Blackboard blackboard;
    
    public ParseTransaction() {
        this.blackboard = Blackboard.getInstance();
    }

    
    /** 
     * Parses the user inputted student roster file 
     * and stores data into blackboard
     * @param file File to be parsed
     */
    public void parseTransactionFile(File file) throws ParseException {
        String data;
        String[] tokens;
        
		try{
            myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				data = myReader.nextLine();
				tokens = data.split(",");
                if (tokens.length == 0) {   // Handle empty line in input data
                    continue;
                }
                Transaction txn = new Transaction(tokens);
                blackboard.addTransaction(txn);
                blackboard.addCustID(tokens[0]);
			}
            myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error occurred in finding roster file: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error occurred in parsing roster data: " + e);
			e.printStackTrace();
		}

        // printData();

        // blackboard.sortTransactions();
        // blackboard.groupTransactions();
        // blackboard.reorderTransactions();

        System.out.println("\nFormatted Transactions\n");
        blackboard.formatTransactions();
        // printData();
        blackboard.addCustDate();

        // System.out.println("\nSwapped Transactions\n");
        // blackboard.swapTransactions();
        // printData();

        System.out.println("\nReordered Transactions\n");
        blackboard.reorderTransactions();
        // printData();

        blackboard.combineTransactions();
        blackboard.printCombinedTransactions();

        blackboard.listifyTransactions();
	}

    public void writeToFile() {

        final String DELIMITER = ",";
        final String SEPARATOR = "\n";
        
        FileWriter file = null;
      
        try {
            file = new FileWriter("Balances.csv");

            for (Map.Entry<String, Map<String, List<String>>> entry : blackboard.getOutputBals().entrySet()) {

                for (Map.Entry<String, List<String>> entry2 : entry.getValue().entrySet()) {
                    
                    file.append(entry.getKey());
                    file.append(DELIMITER);
                    file.append(entry2.getKey());
                    file.append(DELIMITER);

                    Iterator it = entry2.getValue().iterator();

                    while(it.hasNext()) {
                        file.append((String)it.next());
                        if (it.hasNext())
                            file.append(DELIMITER);
                    }

                    file.append(SEPARATOR);
                }
            }
            file.append(SEPARATOR);
            file.close();
        }
        catch(Exception e) {
          e.printStackTrace();
        }
    }

    public void printData() throws ParseException {
        for (Transaction txn : blackboard.getTransactions()) {
            System.out.println(txn.getCustID() 
                        + "\t" + txn.formatDateMonth(txn.getDate()) 
                        + "\t" + txn.convertDateToString(txn.getDate()) 
                        + "\t" + txn.getAmount());
        }

        System.out.println();

        /*
        for (String custID : blackboard.getCustIDs())
            System.out.println(custID);
        */
    }
}

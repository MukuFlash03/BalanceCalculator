package src;

import java.util.List;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Date;
import java.util.Collections;
import java.util.stream.Collectors;

public class Blackboard extends Observable {
    private static volatile Blackboard INSTANCE;

    private final List<Transaction> transactions;
    private final Set<String> custIDs;
    private Map<String, Set<String>> custDates;
    private Map<String, List<Transaction>> groupedTransactions;
    private Map<String, Map<String, List<String>>> outputBals;
    private Map<String, Map<String, List<Integer>>> mergedAmts;

    // Map<String, Map<DateStr, List<Integer>>> output = new HashMap<>();
    // A: All days; List of transactions per day
    // B: All months; List of balances

    private Blackboard() {
        this.transactions = new ArrayList<Transaction>();
        this.custIDs = new HashSet<String>();
        this.groupedTransactions = new HashMap<String, List<Transaction>>();
        this.custDates = new HashMap<String, Set<String>>();
        this.outputBals = new HashMap<String, Map<String, List<String>>>();
        this.mergedAmts = new HashMap<String, Map<String, List<Integer>>>();
    }

    
    /** 
     * Returns instantiated object of this class
     */
    public static Blackboard getInstance() {
        if (INSTANCE == null) {
            synchronized (Blackboard.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Blackboard();
                }
            }
        }
        return INSTANCE;
    }

    public void formatTransactions() {
        sortTransactions();
        groupTransactions();
        // reorderTransactions();
    }

    public void sortTransactions() {
        Collections.sort(transactions, new DateSorter());
    }

    public void groupTransactions() {
        groupedTransactions = transactions.stream().collect(Collectors.groupingBy(o -> o.getCustID()));

        /*
        for (Map.Entry<String, List<Transaction>> entry : groupedTransactions.entrySet()) {
            System.out.println("ID = " + entry.getKey());
            for (Transaction txn : entry.getValue())
                System.out.println(txn.getCustID() + "\t" + txn.getDate() + "\t" + txn.getAmount());
        }
        */
    }

    public void reorderTransactions() {

        transactions.clear();

        List<Transaction> trans2 = new ArrayList<Transaction>();
        for (Map.Entry<String, List<Transaction>> entry : groupedTransactions.entrySet()) {
            for (Transaction txn : entry.getValue()) {
                transactions.add(txn);
                trans2.add(txn);
            }
        }

        /*
        System.out.println("Trans2");
        for (Transaction txn : trans2)
            System.out.println(txn.getCustID() + "\t" + txn.getDate() + "\t" + txn.getAmount());
        */

        /*
        System.out.println("\nTransactions");
        for (Transaction txn : transactions)
            System.out.println(txn.getCustID() + "\t" + txn.convertDateToString(txn.getDate())  + "\t" + txn.getAmount());
        */
    }

    public void listifyTransactions() throws ParseException {

        List<Transaction> trans2 = new ArrayList<Transaction>();
        for (Map.Entry<String, Map<String, List<Integer>>> entry : mergedAmts.entrySet()) {
            for (Map.Entry<String, List<Integer>> entry2 : entry.getValue().entrySet()) {
                for (Integer amount : entry2.getValue()) {
                    String[] data = {entry.getKey(), entry2.getKey(), Integer.toString(amount)};
                    Transaction txn = new Transaction(data);
                    // transactions.add(txn);
                    trans2.add(txn);
                }
            }
        }

        System.out.println("\nTrans2");
        for (Transaction txn : trans2)
            System.out.println(txn.getCustID() + "\t" + txn.getDate() + "\t" + txn.getAmount());
    }

    public void combineTransactions() {
        
        String prevID = transactions.get(0).getCustID();
        String currID = "";
        String dateStr = "";
        int amount = 0;

        Map<String, List<Integer>> dateAmts = new TreeMap<>();
        List<Integer> amountsList = new ArrayList<>();

        for (Transaction txn : transactions) {
            currID = txn.getCustID();
            amount = txn.getAmount();
            dateStr = txn.convertDateToString(txn.getDate());

            if (!prevID.equals(currID)) {
                mergedAmts.put(prevID, dateAmts);
                prevID = currID;
                dateAmts = new TreeMap<>();
                amountsList = new ArrayList<>();
                amountsList.add(amount);
            }
            else {
                
                if (!dateAmts.containsKey(dateStr))
                    amountsList = new ArrayList<>();

                if (amount < 0)
                    amountsList.add(amount);
                else
                    amountsList.add(0, amount);
            }
            dateAmts.put(dateStr, amountsList);
        }
        mergedAmts.put(prevID, dateAmts);
    }

    public void printCombinedTransactions() {
        for (Map.Entry<String, Map<String, List<Integer>>> entry : mergedAmts.entrySet()) {
            System.out.println("ID = " + entry.getKey());

            for (Map.Entry<String, List<Integer>> entry2 : entry.getValue().entrySet()) 
                System.out.println(entry2.getKey() + "\t" + entry2.getValue());
        }
    }

    public void addCustDate() throws ParseException {
        String cusID = "", dateStr = "";

        for (String id : custIDs) {
            Set<String> dates = new HashSet<String>();
            for (Transaction txn : transactions) {
                cusID = txn.getCustID();

                if (cusID.equals(id)) {
                    dateStr = txn.formatDateMonth(txn.getDate());
                    // dt = txn.convertStringToDate2(dateStr);

                    if (!dates.contains(dateStr))
                        dates.add(dateStr);
                    else
                        continue;
                }
                else
                    custDates.put(id, dates);
            }
        }
    }

    public void addBalance(String id, Map<String, List<String>> dateBals) {
        outputBals.put(id, dateBals);
    }

    public void addCustID(String id) {
        if (!custIDs.contains(id))
            custIDs.add(id);
    }

    public void addTransaction(Transaction txn) {
        transactions.add(txn);
    }

    public int getTransactionCount() {
        return transactions.size();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Set<String> getCustIDs() {
        return custIDs;
    }

    public Map<String, Set<String>> getCustDates() {
        return custDates;
    }

    public Map<String, Map<String, List<String>>> getOutputBals() {
        return outputBals;
    }

    public void printCustDates() {
        for (Map.Entry<String,Set<String>> entry : custDates.entrySet()) 
            System.out.println("ID = " + entry.getKey() +
                             ", Dates = " + entry.getValue());
    }
}

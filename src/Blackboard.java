package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Collections;
import java.util.stream.Collectors;

public class Blackboard {
    private static volatile Blackboard INSTANCE;

    private final List<Transaction> transactions;
    private final Set<String> custIDs;
    private Map<String, Set<String>> custDates;
    private Map<String, List<Transaction>> groupedTransactions;
    private Map<String, Map<String, List<String>>> outputBals;
    private Map<String, Map<String, List<Integer>>> mergedAmts;

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
        addCustDate();
        reorderTransactions();
        combineTransactions();
        listifyTransactions();
    }

    public void sortTransactions() {
        Collections.sort(transactions, new DateSorter());
    }

    public void groupTransactions() {
        groupedTransactions = transactions.stream().collect(Collectors.groupingBy(o -> o.getCustID()));
    }

    public void addCustDate() {
        String cusID = "", dateStr = "";

        for (String id : custIDs) {
            Set<String> dates = new HashSet<String>();
            for (Transaction txn : transactions) {
                cusID = txn.getCustID();

                if (cusID.equals(id)) {
                    dateStr = txn.formatDateMonth(txn.getDate());

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

    public void reorderTransactions() {

        transactions.clear();

        List<Transaction> trans2 = new ArrayList<Transaction>();
        for (Map.Entry<String, List<Transaction>> entry : groupedTransactions.entrySet()) {
            for (Transaction txn : entry.getValue()) {
                transactions.add(txn);
                trans2.add(txn);
            }
        }
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

    public void listifyTransactions() {

        List<Transaction> trans2 = new ArrayList<Transaction>();
        transactions.clear();
        for (Map.Entry<String, Map<String, List<Integer>>> entry : mergedAmts.entrySet()) {
            for (Map.Entry<String, List<Integer>> entry2 : entry.getValue().entrySet()) {
                for (Integer amount : entry2.getValue()) {
                    String[] data = {entry.getKey(), entry2.getKey(), Integer.toString(amount)};
                    Transaction txn = new Transaction(data);
                    transactions.add(txn);
                    trans2.add(txn);
                }
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

    public Map<String, Map<String, List<Integer>>> getMergedAmts() {
        return mergedAmts;
    }

    public void printCustDates() {
        for (Map.Entry<String,Set<String>> entry : custDates.entrySet()) 
            System.out.println("ID = " + entry.getKey() +
                             ", Dates = " + entry.getValue());
    }
}

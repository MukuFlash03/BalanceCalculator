package src;

import java.util.List;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

public class Blackboard extends Observable {
    private static volatile Blackboard INSTANCE;

    private final List<Transaction> transactions;
    private final Set<String> custIDs;
    private Map<String, Set<String>> custDates;
    private Map<String, List<String>> balances;

    // Map<String, Map<DateStr, List<Integer>>> output = new HashMap<>();
    // A: All days; List of transactions per day
    // B: All months; List of balances

    private Blackboard() {
        this.transactions = new ArrayList<Transaction>();
        this.custIDs = new HashSet<String>();
        this.balances = new HashMap<String, List<String>>();
        this.custDates = new HashMap<String, Set<String>>();
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

    public void addBalance(String id, List<String> balance) {
        balances.put(id, balance);

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

    public Map<String, List<String>> getBalances() {
        return balances;
    }

    public void printCustDates() {
        for (Map.Entry<String,Set<String>> entry : custDates.entrySet()) 
            System.out.println("ID = " + entry.getKey() +
                             ", Dates = " + entry.getValue());
    }

}

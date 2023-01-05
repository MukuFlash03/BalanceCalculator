package src;

import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.util.*;

public class Evaluator {

    private Blackboard blackboard;

    public Evaluator() {
        this.blackboard = Blackboard.getInstance();
    }

    /*
    public void orderDateTransactions() {
        List<Integer> dateTxns = new ArrayList<>();
        int num;

        for (String id : blackboard.getCustIDs()) {
            Set<Date> dates = new HashSet<>();
            int dateCount = 0;
            for (Transaction txn : blackboard.getTransactions()) {
                if (!txn.getCustID().equals(id))   // use index of last reached txns to skip previous ids
                    continue;

                if (!dates.contains(txn.getDate())) {
                    dates.add(txn.getDate());
                    dateCount++;
                }
                num = txn.getAmount();
                if (num < 0)
                    dateTxns.add(num);
                else
                    dateTxns.add(0,num);

                
            }

            for (Date date : )
        }
    }
    */

    public void computeBalances() {

        for(String id : blackboard.getCustIDs())
            blackboard.addBalance(id, getBalances(id));

        for (List<String> balances : blackboard.getBalances().values()) {
            System.out.println("Min: " + balances.get(0) + "\t" + "Max: " + balances.get(1) + "\t" + "End: "+ balances.get(2));
        }

    }

    public List<String> getBalances(String id) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, end = 0;
        List<String> balance = new ArrayList<String>();

        for (Transaction txn : blackboard.getTransactions()) {
            // System.out.println(txn.getCustID() + "\t" + txn.getDate() + "\t" + txn.getAmount());
            if (!txn.getCustID().equals(id)) // use index of last reached txns to skip previous ids
                continue;
            end += txn.getAmount();
            min = Math.min(min,end);
            max = Math.max(max,end);
        }

        // System.out.println(min + "\t" + max + "\t" + end);
        balance.add(Integer.toString(min));
        balance.add(Integer.toString(max));
        balance.add(Integer.toString(end));

        return balance;
    }

    public void parseDates() throws ParseException {
        blackboard.addCustDate();
        blackboard.printCustDates();
    }
    
}

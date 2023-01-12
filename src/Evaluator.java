package src;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Evaluator {

    private Blackboard blackboard;

    public Evaluator() {
        this.blackboard = Blackboard.getInstance();
    }

    public void computeBalances() {
        for(String id : blackboard.getCustIDs())
            blackboard.addBalance(id, getBalances(id));
    }

    public Map<String, List<String>> getBalances(String id) {
        
        Map<String, List<String>> dateBals = new HashMap<>();

        for (String dateStr : blackboard.getCustDates().get(id)) {            
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, end = 0;
            List<String> balance = new ArrayList<String>();

            for (Transaction txn : blackboard.getTransactions()) {
                String strDate = txn.formatDateMonth(txn.getDate());

                if (!txn.getCustID().equals(id))
                    continue;
                if (!dateStr.equals(strDate))
                    continue;
                end += txn.getAmount();
                min = Math.min(min,end);
                max = Math.max(max,end);
            }

            balance.add(Integer.toString(min));
            balance.add(Integer.toString(max));
            balance.add(Integer.toString(end));
            dateBals.put(dateStr, balance);
        }
        return dateBals;
    }

    public void parseDates() {
        blackboard.addCustDate();
    }
    
}

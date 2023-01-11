package src;

import java.util.Comparator;

public class DateSorter implements Comparator<Transaction> {
 
    // Method of this class
    // @Override
    public int compare(Transaction a, Transaction b)
    {
        return a.getDate().compareTo(b.getDate());
    }
}
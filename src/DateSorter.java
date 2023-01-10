package src;

import java.util.Comparator;

public class DateSorter implements Comparator<Transaction> {
 
    // Method of this class
    // @Override
    public int compare(Transaction a, Transaction b)
    {
 
        // Returning the value after comparing the objects
        // this will sort the data in Ascending order
        return a.getDate().compareTo(b.getDate());
    }
}
package src;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Transaction {
    
    private final String custID;
    private final Date date;
    private final int amount;

    public Transaction(String[] data) throws ParseException {
        this.custID = data[0];
        this.date = convertStringToDate(data[1]);
        this.amount = Integer.parseInt(data[2]);
    }

    public String convertDateToString(Date date) {
        SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
        return dt1.format(date);
    }

    public Date convertStringToDate(String dateStr) throws ParseException {
        return new SimpleDateFormat("MM/dd/yyyy").parse(dateStr);
    }

    public String formatDateMonth(Date date) throws ParseException {
        String dateStr = new SimpleDateFormat("MM/yyyy").format(date);
        Date newDate = new SimpleDateFormat("MM/yyyy").parse(dateStr);
        return dateStr;
    }

    public Date convertStringToDate2(String dateStr) throws ParseException {
        return new SimpleDateFormat("MM/yyyy").parse(dateStr);
    }

    
    /** 
     * Returns customer ID
     */
    public String getCustID() {
        return custID;
    }

    
    /** 
     * Returns transaction date
     */
    public Date getDate() {
        return date;
    }

    
    /** 
     * Returns transaction amount
     */
    public int getAmount() {
        return amount;
    }
}

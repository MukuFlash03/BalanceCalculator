import java.io.*;
import java.util.*;

public class Test {
    public static void main(String arg[]) throws IOException {
        // Map<String, Map<DateStr, List<Integer>>> output = new HashMap<>();
        // A: All days; List of transactions per day
        // B: All months; List of balances

        int[] nums = {-5, 10, 5, -10, -2, 4};
        List<Integer> txns = new ArrayList<>();

        for (int num : nums) {
            if (num < 0)
                txns.add(num);
            else
                txns.add(0,num);
        }

        for (int txn : txns) {
            System.out.print(txn + "\t");
        }
        System.out.println();
        System.out.println(txns);
    }
}

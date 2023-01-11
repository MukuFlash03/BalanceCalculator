package src;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

public class ParseBalance {

    private Blackboard blackboard;
    
    public ParseBalance() {
        this.blackboard = Blackboard.getInstance();
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
}

import java.util.*;
import java.util.stream.Collectors;

class Dog {
    int id;
    String breed;
  
    Dog(int id, String breed) {
      this.id = id;
      this.breed = breed;
    }
    int getId() { return this.id; }
    String getBreed() { return this.breed; }
}
  


public class Test_Group {
    public static void main(String[] args) {
        List<Dog> lst = new ArrayList<Dog>();
        lst.add(new Dog(1, "corgi"));
        lst.add(new Dog(2, "shih tzu"));
        lst.add(new Dog(3, "corgi"));
        lst.add(new Dog(4, "corgi"));
        lst.add(new Dog(5, "husky"));
        lst.add(new Dog(6, "shih tzu"));


        Map<String, List<Dog>> grouped = lst.stream().collect(Collectors.groupingBy(o -> o.breed));

        for (Map.Entry<String, List<Dog>> entry : grouped.entrySet()) 
            System.out.println("ID = " + entry.getKey() +
                         ", Dogs = " + entry.getValue());

    }
}


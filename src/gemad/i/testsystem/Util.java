package gemad.i.testsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by 4 on 30.04.2016.
 */
public class Util {

    public static ArrayList<Integer> shuffleSmall(int size) {
        ArrayList<Integer> order = new ArrayList<>();
        ArrayList<Integer> numbers = formArray(size);
        int index;
        for (int i = 0; i < size; i++) {
            index = (int) (Math.random() * (numbers.size()));
            order.add(numbers.get(index));
            numbers.remove(index);
        }
        return order;
    }

    public static ArrayList<Integer> shuffleBig(int size) {
        ArrayList<Integer> order = new ArrayList<>();
        HashMap<Double, Integer> linkMap = new HashMap<>();
        ArrayList<Double> sortList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sortList.add(Math.random());
            linkMap.put(sortList.get(i), i);
        }
        Collections.sort(sortList);
        for (int i = 0; i < size; i++) {
            order.add(linkMap.get(sortList.get(i).doubleValue()));
        }
        return order;
    }

    public static ArrayList<Integer> formArray(int size) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            numbers.add(i);
        }
        return numbers;
    }
}

package gemad.i.testsystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * Created by 4 on 30.04.2016.
 */
public class Temp {
    private final static String setPlainText = "\033[0;0m";
    private final static String setBoldText = "\033[0;1m";

    public static void main(String[] args) {
        System.out.println (setPlainText + "Prompt>" + setBoldText + "Prompt");
    }

}

package gemad.i.testsystem;


import gemad.i.testsystem.Utils.Translator;

import java.io.*;
import java.util.ArrayList;

public class Configuration {
    public final static String CONFIG_FILENAME = "config.ini";
    private final static Configuration configuration = new Configuration(CONFIG_FILENAME);
    private final static int DEFAULT_LANGUAGE = Translator.RUSSIAN;
    private final static String DEFAULT_CHARSET = "windows-1251";
    private final static String TEMPLATE_LANGUAGE = "LANG";
    private final static String TEMPLATE_TEST = "TEST";
    private final static String TEMPLATE_CHARSET = "CHARSET";

    private int language;
    private ArrayList<String> tests;
    private String charset;

    private Configuration(String filename){
        language = DEFAULT_LANGUAGE;
        charset = DEFAULT_CHARSET;
        tests = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find the configuration file");
            return;
        }
        String line = "";
        try {
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
                if (line.startsWith(TEMPLATE_LANGUAGE)) {
                    String lang = getValue(line, TEMPLATE_LANGUAGE);
                    try {
                        int temp = Integer.valueOf(lang);
                        if (isLanguageCorrect(temp))
                            this.language = temp;
                        else
                            System.out.println("Wrong language configuration");
                    } catch (NumberFormatException ex) {
                        System.out.println("Language value is not an integer value");
                    }
                } else if (line.startsWith(TEMPLATE_CHARSET)) {
                    String temp = getValue(line, TEMPLATE_CHARSET);
                    if (isCharsetCorrect(temp))
                        this.charset = temp;
                    else
                        System.out.println("Wrong charset configuration");
                } else if (line.startsWith(TEMPLATE_TEST)) {
                    String temp = getValue(line, TEMPLATE_TEST);
                    if (isTestpathCorrect(temp))
                        tests.add(temp);
                }
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isTestpathCorrect(String temp) {
        return temp.endsWith(".txt") && temp.length() > 4;
    }

    private static boolean isCharsetCorrect(String charset){
        return (charset.equals("windows-1251") || charset.equals("UTF-8"));
    }

    private static boolean isLanguageCorrect(int language){
        return (language == Translator.ENGLISH || language == Translator.RUSSIAN);
    }

    private String getValue(String line, String template){
        line = line.trim();
//            if (line.startsWith(template)) {
                line = line.substring(template.length() + 1, line.length()); // substract template & "="
//            }
        return line;
    }
    
    public void addTest(String test){
        this.tests.add(test);
    }
    
    public void removeTest(String test){
        for (int i = 0; i < this.tests.size(); i++) {
            if (test.equals(this.tests.get(i))) {
                this.tests.remove(i);
                return;
            }
        }
    }

    public void saveConfiguration(String filename){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false)); // overwriting current config
            bw.write(TEMPLATE_LANGUAGE + "="+ this.language);
            bw.newLine();
            bw.write(TEMPLATE_CHARSET + "=" + this.charset);
            bw.newLine();
            for (String test : tests) {
                bw.write(TEMPLATE_TEST + "=" + test);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e1) {
            System.out.println("Cannot create or write to the configuration file");
        }
    }
    
    public static Configuration getInstance(){
        return configuration;
    }

    public ArrayList<String> getTests() {
        return tests;
    }

    public int getLanguage() {
        return language;
    }

    public String getCharset() {
        return charset;
    }
}

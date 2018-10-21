package gemad.i.testsystem;

import gemad.i.testsystem.Data.Question;
import gemad.i.testsystem.Data.TestList;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Reader {

    /* Test file format:
    :Test name

    ?Question name
    .answer
    !right answer
     */

    //private static final Logger log = LogManager.getLogger(Reader.class);
    public static TestList readTest(String filename) {
        int type, questionNumber = 0;
        TestList test = new TestList("");// plug in case test isn't initialized
        File file = new File(filename);
        BufferedReader br;
        try {
            br = new BufferedReader( new InputStreamReader(new FileInputStream(filename), "windows-1251"));
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        String questionName = "";
        ArrayList<String> questionOptions = new ArrayList<>();
        int questionAnswer = 0;
        Question question;
        String line;
        try {
            line = br.readLine();
            if (line == null || !line.startsWith(":")){
                System.out.println("Not a test file!");
                return null; // Если файл не начинается с : , метод возвращает вместо теста null
            }
            int optionsCounter = 0; //points at the current question option
            while (line != null) {
                type = defineLine(line.trim());
                switch (type) {
                    case 1:
                        test = new TestList(line.substring(1, line.length())); // gets test name without special character
                        break;
                    case 2:
                        if (!questionOptions.isEmpty()) {
                            question = new Question(questionName, questionOptions, questionNumber, questionAnswer, test.getName());
                            test.addQuestion(question);
                            questionOptions.clear();
                            questionNumber++;
                        }
                        questionName = processQuestionName(line);
                        optionsCounter = 0;
//                        if (log.isDebugEnabled())
//                            log.debug("Read question name: " + questionName);
                        break;
                    case 3:
                        questionOptions.add(processOption(line));
                        optionsCounter++;
//                        if (log.isDebugEnabled())
//                            log.debug("Read question option: " + questionOptions.get(questionOptions.size() - 1));
                        break;
                    case 4:
                        questionOptions.add(processOption(line));
                        questionAnswer = optionsCounter;
                        optionsCounter++;
                        break;
                    default:
                        break;
                }
                line = br.readLine();
            }
            question = new Question(questionName, questionOptions, questionNumber, questionAnswer, test.getName());
            test.addQuestion(question);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return test;
    }

    //returns 1 if string is a test name,
    // 2 if question name,
    //3 if it is an option,
    //4 if it is an answer,
    //0 if neither
    private static int defineLine(String line) {
        final char testName = ':', questionName = '?', option = '.', answer = '!';
        if (line == null || line.isEmpty())
            return 0;
        switch (line.charAt(0)){
            case testName:
                return 1;
            case questionName:
                return 2;
            case option:
                return 3;
            case answer:
                return 4;
            default:
                return 0;
        }
    }

    private static String processOption(String line) {
        line = formatLine(line);
//        int beginIndex = line.indexOf(')');
//        beginIndex = (beginIndex == - 1) ? 0 : beginIndex + 1;
//        int endIndex = line.length();
//        boolean endsWithSymbol = (line.charAt(line.length() - 1) == ';' || line.charAt(line.length() - 1) == '.'
//                || line.charAt(line.length() - 1) == ',');
//        if (endsWithSymbol) {
//            endIndex = line.length() - 1;
//        }
//        line = line.substring(beginIndex, endIndex);
//        return line;
        return processLine(line);
    }

    private static String processQuestionName(String line) {
        return processLine(line);
    }

    private static String processTestName(String line){
        return processLine(line);
    }

    // function for all types of lines in test file
    private static String processLine(String line){
        if (line == null || line.isEmpty())
            return line;
        return line.substring(1, line.length());
    }

    private static String formatLine(String line) {
        line = line.trim();
        StringBuffer newLine = new StringBuffer();
        int endOfLine, beginningOfLine = 0;
        if (line.length() > 90) {
            for (int i = 90; i < line.length(); i = endOfLine + 90) {
                endOfLine = line.indexOf(' ', i);
                if (endOfLine != -1) {
                    newLine.append(line.substring(beginningOfLine, endOfLine)).append("\n");
                    if ((endOfLine + 90) > line.length()) {
                        newLine.append(line.substring(endOfLine));
                    }
                } else {
                    newLine.append(line.substring(beginningOfLine));
                    break;
                }
                beginningOfLine = endOfLine;
            }
        } else {
            return line;
        }
        return newLine.toString();
    }
}

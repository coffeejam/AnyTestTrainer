package gemad.i.testsystem;

import gemad.i.testsystem.Data.Question;
import gemad.i.testsystem.Data.TestList;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by 4 on 20.04.2016.
 */
public class Reader {

    //private static final Logger log = LogManager.getLogger(Reader.class);
    public static TestList readTest(String filename, String testName, int[] answers, String testNum) throws IOException {
        int type = 0, questionNumber = 0;
        TestList test = new TestList(testName);
        File file = new File(filename);
        BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8));
        String questionName = new String();
        ArrayList<String> questionOptions = new ArrayList<>();
        Question question;
        String line;
        try {
            line = br.readLine();
            while (line != null) {
                type = defineLine(line.trim());
                switch (type) {
                    case 1:
                        if (!questionOptions.isEmpty()) {
                            question = new Question(questionName, questionOptions, questionNumber, answers[questionNumber], testNum);
                            test.addQuestion(question);
                            questionOptions.clear();
                            questionNumber++;
                        }
                        questionName = processQuestionName(line);
//                        if (log.isDebugEnabled())
//                            log.debug("Read question name: " + questionName);
                        break;
                    case 2:
                        questionOptions.add(processOption(line));
//                        if (log.isDebugEnabled())
//                            log.debug("Read question option: " + questionOptions.get(questionOptions.size() - 1));
                        break;
                    default:
                        break;
                }
                line = br.readLine();
            }
            question = new Question(questionName, questionOptions, questionNumber, answers[questionNumber], testNum);
            test.addQuestion(question);
        } catch (IOException e) {
            e.printStackTrace();
        }
        br.close();
        return test;
    }

    //returns 1 if string is a question name,
    //2 if it is an option,
    //0 if neither
    private static int defineLine(String line) {
        String questionName = "\\d\\.\\d*.*";
        String option = "[а-я]\\).*";
        int result = 0;
        if (line.matches(questionName)) {
            result = 1;
        } else if (line.matches(option)) {
            result = 2;
        }
        return result;
    }

    private static String processOption(String line) {
        line = formatLine(line);
        int beginIndex = line.indexOf(')');
        beginIndex = (beginIndex == - 1) ? 0 : beginIndex + 1;
        int endIndex = line.length();
        boolean endsWithSymbol = (line.charAt(line.length() - 1) == ';' || line.charAt(line.length() - 1) == '.'
                || line.charAt(line.length() - 1) == ',');
        if (endsWithSymbol) {
            endIndex = line.length() - 1;
        }
        line = line.substring(beginIndex, endIndex);
        return line;
    }

    private static String processQuestionName(String line) {
        //line = formatLine(line);
        int beginIndex = line.indexOf(' ');
        beginIndex = (beginIndex == - 1) ? 0 : beginIndex + 1;
        line = line.substring(beginIndex);
        return line;
    }

    private static String formatLine(String line) {
        line = line.trim();
        StringBuffer newLine = new StringBuffer();
        int endOfLine = 0, beginningOfLine = 0;
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

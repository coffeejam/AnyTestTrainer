package gemad.i.testsystem;

import gemad.i.testsystem.Data.Question;
import gemad.i.testsystem.Data.TestList;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class Reader {

    final static int MAX_IMAGE_WIDTH = 600;
    final static int MAX_IMAGE_HEIGHT = 400;
    private String charset;

    /* Test file format:
    :Test name

    ?Question name
    .answer
    !right answer
     */
    public Reader(){
        this.charset = Configuration.getInstance().getCharset();
    }

    //private static final Logger log = LogManager.getLogger(Reader.class);
    public TestList readTest(String filename) {
        int type, questionNumber = 0;
        TestList test = new TestList("");// plug in case test isn't initialized
        File file = new File(filename);
        String imageName = "";
        BufferedReader br;
        try {
            br = new BufferedReader( new InputStreamReader(new FileInputStream(filename), charset));
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
                            question = new Question(questionName, questionOptions, questionNumber, questionAnswer, test.getName(), readImage(imageName), imageName);
                            test.addQuestion(question);
                            questionOptions.clear();
                            questionNumber++;
                        }
                        String questionString = processQuestionName(line);
                        if (questionString.split(" ")[0].endsWith(".jpg")) {
                            imageName = questionString.split(" ")[0];
                            questionName = questionString.substring(imageName.length());
                        }
                            else {
                            imageName = "";
                            questionName = questionString;
                        }
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
            question = new Question(questionName, questionOptions, questionNumber, questionAnswer, test.getName(), readImage(imageName), imageName);
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

    public static ImageIcon readImage(String imageName) {
        float height, width;
        int newHeight, newWidth;
        BufferedImage img;
        if (imageName == null || imageName.isEmpty())
            return null;
        try {
            img = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        height = img.getHeight();
        width = img.getWidth();
        float divider =1.0f;
        if (width > MAX_IMAGE_WIDTH || height > MAX_IMAGE_HEIGHT)
        if (width > height) {
            divider = width/((float) MAX_IMAGE_WIDTH);
        } else {
            divider = height/((float) MAX_IMAGE_HEIGHT);
        }
        newWidth = (int) (width / divider);
        newHeight = (int) (height / divider);
        Image dimg = img.getScaledInstance(newWidth, newHeight,
                Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }
}

package gemad.i.testsystem;

import java.io.IOException;


public class Runner {


    public static void main(String[] args) throws IOException {
        Controller c = new Controller(args);
        Runtime.getRuntime().addShutdownHook(new saveThread());
    }

    static class saveThread extends Thread{
        public void run(){
            Configuration.getInstance().saveConfiguration(Configuration.CONFIG_FILENAME);
        }
    }
}

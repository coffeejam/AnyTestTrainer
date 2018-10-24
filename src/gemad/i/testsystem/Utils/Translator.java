package gemad.i.testsystem.Utils;


public class Translator {
    private final static Translator translator = new Translator(Translator.RUSSIAN);
    final static int ENGLISH = 0;
    final static int RUSSIAN = 1;
    private int language;

    private Translator(int language) {
        setLanguage(language);
    }

    public void setLanguage(int language) {
        if (language < 0 || language > RUSSIAN)
            this.language = 0;
        else
            this.language = language;
    }

    public int getLanguage() {
        return language;
    }

    //translates to currently set language
    public String translate(String[] textConstant){
        if (language >= textConstant.length)
            return textConstant[0];
        return textConstant[language];
    }

    public static Translator getInstance(){
        return translator;
    }
}

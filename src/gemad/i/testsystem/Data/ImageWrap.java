package gemad.i.testsystem.Data;

import javax.swing.*;

public class ImageWrap {
    private ImageIcon image;
    private String name;
    private String path;

    public ImageWrap(ImageIcon image, String path, String name){
        this.image = image;
        this.path = path;
        this.name = name;
    }

    public ImageWrap(ImageIcon image, String path){
        this(image, path, path.split(System.lineSeparator())[path.split(System.lineSeparator()).length-1]);
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}

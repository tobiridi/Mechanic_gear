package be.jadoulle.mechanical_gear.POJO;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class SignalType implements Serializable {
    //ATTRIBUTES
    private String text;
    private ArrayList<ImageView> images;

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<ImageView> getImages() {
        return images;
    }
    public void setImages(ArrayList<ImageView> images) {
        this.images = images;
    }

    //CONSTRUCTOR
    public SignalType(String text, ArrayList<ImageView> images) {
        this.text = text;
        this.images = images;
    }

    //METHODS
    public void addImage(ImageView img) {
        this.images.add(img);
    }

    @Override
    public String toString() {
        return "SignalType{" +
                "text='" + text + '\'' +
                '}';
    }
}

package data;

import java.io.Serializable;

/**
 * Created by asthasharma017 on 11/6/2017.
 */

public class Word implements Serializable {
    private String phrase;
    private String meaning;
    private String description;
    private String category;

    public Word(String word, String meaning, String description, String category) {
        this.phrase = word;
        this.meaning = meaning;
        this.description = description;
        this.category = category;
    }

    public void setPhrase(String phrase){
        this.phrase = phrase;
    }

    public String getPhrase(){
        return phrase;
    }

    public void setMeaning(String meaning){
        this.meaning = meaning;
    }

    public String getMeaning(){
        return meaning;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    @Override
    public String toString(){
        return phrase + ", " + meaning + ", " + description + ", " + category;
    }
}

package data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asthasharma017 on 11/8/2017.
 */

public class Dictionary implements Serializable {
    private List<Word> words = new ArrayList<Word>();

    public void setWords(List<Word> words){
        this.words = words;
    }
    public List<Word> getWords(){
        return words;
    }

    public Dictionary(){

    }

}

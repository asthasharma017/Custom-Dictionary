package com.example.asthasharma017.espaoldiccionario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

import constant.Constants;
import data.Dictionary;
import data.Word;
import util.Util;

public class WordDescriptionActivity extends AppCompatActivity {

    private Button btnIKnow, btnIDoNotKnow;
    private TextView tvPhrase, tvMeaning, tvDescription;
    private Word word;
    private Dictionary dictionary;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_description);

        setValues();
        setDataToDisplay();

        btnIKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.setCategory(Constants.STR_MASTERED);
                setDataToDisplay();
            }
        });

        btnIDoNotKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDataToDisplay();
            }
        });
    }

    private void setRandomWord() {
        Random r = new Random();
        int lowerBound = 0;
        int upperBound = dictionary.getWords().size();
        for(int j=0;;){
            int i = r.nextInt(upperBound-lowerBound) + lowerBound;
            word = dictionary.getWords().get(i);
            if(word.getCategory() != null && word.getCategory().equals(category)) {
                break;
            }else{
                j++;
                if (j==50){
                    onBackPressed();
                }
            }
        }
    }

    private void setDataToDisplay(){
        setRandomWord();
        tvPhrase.setText(word.getPhrase());
        tvMeaning.setText(word.getMeaning());
        tvDescription.setText(word.getDescription());
    }

    private void setValues(){
        Bundle b = this.getIntent().getExtras();
        if (b != null){
            category = b.getString(Constants.STR_CATEGORY);
            dictionary = (Dictionary) getIntent().getSerializableExtra(Constants.DICTIONARY_INSTANCE);
        }
        btnIKnow = (Button) findViewById(R.id.btnIKnow);
        btnIDoNotKnow = (Button) findViewById(R.id.btnIDoNotKnow);
        tvPhrase = (TextView) findViewById(R.id.tvPhrase);
        tvMeaning = (TextView) findViewById(R.id.tvMeaning);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_front, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Word>>() {}.getType();
        String json = gson.toJson(dictionary.getWords(), type);
        Boolean success = Util.saveWordJson(json, WordDescriptionActivity.this);
        if(!success){
            Toast.makeText(WordDescriptionActivity.this, Constants.SAVING_ERROR, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}

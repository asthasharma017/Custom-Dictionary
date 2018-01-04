package com.example.asthasharma017.espaoldiccionario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import constant.Constants;
import data.Dictionary;
import data.Word;

public class FrontActivity extends AppCompatActivity {

    private Button btnHardWords, btnIntermediateWords, btnEasyWords, btnMasteredWords;
    private FloatingActionButton buttonAddWord;
    private TextView tvWord, tvMeaning, tvDescription;
    private Dictionary dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        setValues();
        String json = getWordJson();
        setWordList(json);

        buttonAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrontActivity.this, AddWordActivity.class);
                Bundle b = new Bundle();
                intent.putExtras(b);
                intent.putExtra(Constants.DICTIONARY_INSTANCE, dictionary);
                startActivityForResult(intent, 1);
            }
        });

        btnHardWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity(WordDescriptionActivity.class, Constants.STR_HARD);
            }
        });

        btnIntermediateWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity(WordDescriptionActivity.class, Constants.STR_INTERMEDIATE);
            }
        });

        btnEasyWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity(WordDescriptionActivity.class, Constants.STR_EASY);
            }
        });

        btnMasteredWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity(WordDescriptionActivity.class, Constants.STR_MASTERED);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Log.d("Astha", "RESULT_OK2");
                dictionary = (Dictionary) data.getSerializableExtra(Constants.DICTIONARY_INSTANCE);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    private void goToNextActivity(Class myClass, String category){
        if(dictionary.getWords().size() > 0){
            Intent intent = new Intent(FrontActivity.this, myClass);
            Bundle b = new Bundle();
            b.putString(Constants.STR_CATEGORY, category);
            //comment
            intent.putExtras(b);
            intent.putExtra(Constants.DICTIONARY_INSTANCE, dictionary);
            startActivity(intent);
        } else {
            Toast.makeText(FrontActivity.this, Constants.STR_NO_WORD_ADDED, Toast.LENGTH_SHORT).show();
        }
    }

    private void setWordList(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<List<Word>>() {}.getType();
        List<Word> words = gson.fromJson(json, type);
        if(words != null) {
            dictionary.getWords().addAll(words);
            for (Word word : words) {
                System.out.println(word);
            }
        }
    }

    private String getWordJson(){
        FileInputStream fis = null;
        String json = "";
        try {
            fis = openFileInput(Constants.FILE_NAME);
            BufferedReader buf = new BufferedReader(new InputStreamReader(fis));
            String line = buf.readLine();
            StringBuilder sb = new StringBuilder();
            json = sb.toString();
            fis.close();
        }catch (FileNotFoundException ex) {
            Log.e("Error", "FileNotFoundException");
        }catch (IOException ex) {
            Log.e("Error", "IOException");
        }
        return json;
    }

    private void setValues(){
        dictionary = new Dictionary();
        buttonAddWord = (FloatingActionButton) findViewById(R.id.add);
        btnHardWords = (Button) findViewById(R.id.btnHardWords);
        btnIntermediateWords = (Button) findViewById(R.id.btnIntermediateWords);
        btnEasyWords = (Button) findViewById(R.id.btnEasyWords);
        btnMasteredWords = (Button) findViewById(R.id.btnMasteredWords);
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

}

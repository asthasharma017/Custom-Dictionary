package com.example.asthasharma017.espaoldiccionario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import constant.Constants;
import data.Dictionary;
import data.Word;
import util.Util;

public class AddWordActivity extends AppCompatActivity {

    private Button btnSave;
    private EditText etPhrase, etMeaning, etDescription;
    private Spinner categorySpinner;
    private Dictionary dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        setValues();

        List<String> list = new ArrayList<String>();
        list.add(Constants.STR_HARD);
        list.add(Constants.STR_INTERMEDIATE);
        list.add(Constants.STR_EASY);
        list.add(Constants.STR_MASTERED);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(dataAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySpinner.setSelection(position, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = getUserInput();
                List<Word> words = dictionary.getWords();
                words.add(word);
                if (word!=null) {
                    dictionary.setWords(words);
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<Word>>() {}.getType();
                    String json = gson.toJson(words, type);
                    System.out.println(json);
                    boolean success = Util.saveWordJson(json, AddWordActivity.this);
                    if (success) {
                        Intent returnIntent = new Intent();
                        Bundle b = new Bundle();
                        returnIntent.putExtra(Constants.DICTIONARY_INSTANCE, dictionary);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), Constants.SAVING_ERROR, Toast.LENGTH_LONG);
                    }
                }
            }
        });

    }

    private Word getUserInput(){
        String phrase = etPhrase.getText().toString();
        String meaning = etMeaning.getText().toString();
        String description = etDescription.getText().toString();
        String catagory = categorySpinner.getSelectedItem().toString();
        if(phrase != null && meaning != null){
            if(catagory == null) {
                catagory = Constants.STR_HARD;
            }
            if (description == null) {
                description = "";
            }
            return new Word(phrase, meaning, description, catagory);
        }
        Toast.makeText(getApplicationContext(),Constants.STR_ENTER_ALL_FIELDS,
                Toast.LENGTH_LONG).show();
        return  null;
    }

    private void setValues(){
        Bundle b = this.getIntent().getExtras();
        if (b != null){
            dictionary = (Dictionary) getIntent().getSerializableExtra(Constants.DICTIONARY_INSTANCE);
        }
        btnSave = (Button)findViewById(R.id.btnSave);
        etPhrase = (EditText) findViewById(R.id.etPhrase);
        etMeaning = (EditText) findViewById(R.id.etMeaning);
        etDescription = (EditText) findViewById(R.id.etDescription);
        categorySpinner = (Spinner) findViewById(R.id.catagorySpinner);
    }

    /*private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }*/

    /*private void createFileInAppFolder()
    {
        try {
//         outputStream.write(bitmapStream.toByteArray());
            outputStream.write(text.getBytes());
        }
        catch (IOException e1) {
            Log.i("Error", "Unable to write file contents.");
        }


        MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
                .setMimeType("text/txt").setTitle("testfile.txt").build();

        IntentSender intentSender = Drive.DriveApi
                .newCreateFileActivityBuilder()
                .setInitialMetadata(metadataChangeSet)
                .setInitialDriveContents(result.getDriveContents())
                .build(mGoogleApiClient);
        try {
            startIntentSenderForResult(intentSender, REQUEST_CODE_CREATOR, null, 0, 0, 0);
        }
        catch (IntentSender.SendIntentException e) {
            Log.i("Error", "Failed to launch file chooser.");
        }
    }*/

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

package util;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import constant.Constants;

/**
 * Created by asthasharma017 on 11/8/2017.
 */

public class Util {
    public static boolean saveWordJson(String json, Context context){
        //FileOutputStream fos = null;
        try {
            /*fos = context.openFileOutput(Constants.FILE_NAME, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();*/

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(Constants.FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(json);
            outputStreamWriter.close();


        }catch (FileNotFoundException ex) {
            Log.e("Error", "FileNotFoundException");
            return false;
        }catch (IOException ex) {
            Log.e("Error", "IOException");
            return false;
        }
        return true;
    }
}

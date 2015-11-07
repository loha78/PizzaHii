package projectapp.com.pizzahii.Controller;

import android.app.Activity;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadServerText extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String outPut = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;

            while ((line = reader.readLine()) != null) {

                outPut += line;
            }
            reader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return outPut ;
    }

    protected void onProgressUpdate() {

    }

    protected void onPostExecute(String output) {
        System.out.println(output);
    }
}

package projectapp.com.pizzahii.Controller;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by NAJI on 05/11/2015.
 */
public class ReadMessages extends AsyncTask<Void, String, Void> {
    public BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    @Override
    protected Void doInBackground(Void... v) {
        while (!isCancelled()) {
            try {
                String message = reader.readLine();
                publishProgress(message);
            } catch (IOException e) {
                break;
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... messages) {
        retourneLigne(messages[0] + "\n");
    }

    private String retourneLigne(String string){
        return string;
    }

}
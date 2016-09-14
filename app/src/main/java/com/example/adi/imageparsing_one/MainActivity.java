package com.example.adi.imageparsing_one;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    public static final String URL="http://theopentutorials.com/totwp331/wp-content/uploads/totlogo.png";
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView=(ImageView) findViewById(R.id.imaegView);
        // Create an object for subclass of AsyncTask
        GetXMLTask task = new GetXMLTask();
        // Execute the task
        task.execute(new String[] { URL });
    }

    private class GetXMLTask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map=null;
            for (String url:urls){
                map=downloadImage(url);
            }
            return map;
        }
        //Set the Bitmap returned in doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
        //Created Bitmap from Input and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap=null;
            InputStream stream=null;
            BitmapFactory.Options bmOptions=new BitmapFactory.Options();
            bmOptions.inSampleSize=1;
                try{
                    stream=getHttpConnection(url);
                    bitmap=BitmapFactory.decodeStream(stream,null,bmOptions);
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return bitmap;
        }
    }

    // Makes HttpURLConnection and returns InputStream
    private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }
}

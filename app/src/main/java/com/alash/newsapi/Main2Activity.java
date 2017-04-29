package com.alash.newsapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {
    ImageView image ;
    TextView title;
    TextView content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        title = (TextView) findViewById(R.id.text_title);
        image= (ImageView) findViewById(R.id.main_backdrop);
        content = (TextView) findViewById(R.id.text_content);

        Intent intent = getIntent();
        int position  = intent.getIntExtra("position",0);

        try {
            JSONObject currentJsonObject = MainActivity.getCurrentJsonArray().getJSONObject(position);
            title.setText(currentJsonObject.getString("title"));
            content.setText(currentJsonObject.getString("description"));
            byte[] byteArray = getIntent().getByteArrayExtra("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            image.setImageBitmap(bmp);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}

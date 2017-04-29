package com.alash.newsapi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by Erbolat on 27.04.2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private JSONArray mDataset;
    public final static int IMAGE_DOWNLOADING = 2;
    public final static int IMAGE_DOWNLOADED = 1;
    public final static int IMAGE_FAILED = 3;

    Context context;




    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView content;
        public ImageView imageView;
        public View view;
        public int status = IMAGE_FAILED;
        public Bitmap image;
        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.text_title);
            content = (TextView) v.findViewById(R.id.text_content);
            imageView = (ImageView) v.findViewById(R.id.image);
            view = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(JSONArray myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_activity, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            JSONObject currentJsonObject = mDataset.getJSONObject(position);
            holder.title.setText(currentJsonObject.getString("title"));
            holder.content.setText(currentJsonObject.getString("description"));
            Picasso.with(context)
                    .load(currentJsonObject.getString("urlToImage"))
                    .into(new Target() {

                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            holder.status = IMAGE_DOWNLOADED;
                            holder.image = bitmap;
                            holder.imageView.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            holder.status = IMAGE_FAILED;
                            holder.imageView.setImageDrawable(errorDrawable);
                            Toast.makeText(context, "Internet no!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            holder.status = IMAGE_DOWNLOADING;
                        }
                    });
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (holder.status){
                        case IMAGE_DOWNLOADED:
                            Intent intent = new Intent(v.getContext(),Main2Activity.class);
                            intent.putExtra("position",position);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            holder.image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            intent.putExtra("image",byteArray);
                            v.getContext().startActivity(intent);
                            break;
                        case IMAGE_DOWNLOADING:
                            break;
                        case IMAGE_FAILED:
                            break;
                    }


                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }
}
package com.example.bipin.quotation.adapters;

import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipin.quotation.R;
import com.example.bipin.quotation.pojos.Quote;
import com.example.bipin.quotation.utility.Utility;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by bipin on 7/30/16.
 */
public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.MyViewHolder> {

    LayoutInflater inflater;
    Context context;
    ArrayList<Quote> data = new ArrayList<>();

    public QuoteAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public QuoteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_quote, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuoteAdapter.MyViewHolder holder, int position) {

        holder.contentTv.setText(data.get(position).getContent());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Quote> data) {
        this.data = data;
        notifyItemChanged(0, data.size());

    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView contentTv;
        ImageButton likeButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            contentTv = (TextView) itemView.findViewById(R.id.details);
            likeButton = (ImageButton) itemView.findViewById(R.id.like);
            likeButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.like) {

                SharedPreferences prefs = Utility.getPreferences(context);
                Quote quote = data.get(getAdapterPosition());
                String id=quote.getId();//it is easy to compare if the new_quote contains that id or not

                String new_quote = quote.getContent()+"-"+id;

                String previous_quote = prefs.getString(Utility.KEY, "");

                String hybrid_quote;
                if (previous_quote.contains(id)) {
                    v.setBackgroundResource(R.drawable.heart);
                   Toast.makeText(context,"It is already taken",Toast.LENGTH_LONG).show();
                } else {
                    hybrid_quote = new_quote + ";;" + previous_quote;//prv+new ==>>prev suru ma jailey khali nai huncha but not new
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(Utility.KEY, hybrid_quote).apply();
                }

            }


        }
    }

}

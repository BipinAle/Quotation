package com.example.bipin.quotation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bipin.quotation.R;
import com.example.bipin.quotation.pojos.Quote;

import java.util.ArrayList;

/**
 * Created by bipin on 7/30/16.
 */
public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.MyViewHolder> {

    LayoutInflater inflater;
    Context context;
    ArrayList<Quote> data=new ArrayList<>();

    public QuoteAdapter(Context context) {
       inflater=LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public QuoteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.single_quote,parent,false);
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
        this.data=data;
        notifyItemChanged(0,data.size());

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contentTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            contentTv= (TextView) itemView.findViewById(R.id.details);

        }
    }
}

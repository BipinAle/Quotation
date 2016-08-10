package com.example.bipin.quotation.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bipin.quotation.R;
import com.example.bipin.quotation.activities.QuoteActivity;
import com.example.bipin.quotation.pojos.Group;
import com.example.bipin.quotation.pojos.GroupImageColor;

import java.util.ArrayList;


/**
 * Created by bipin on 7/27/16.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<Group> data = new ArrayList<>();
    ArrayList<GroupImageColor> imagesColors = new ArrayList<>();

    public GroupAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        //        int [] bgColor={R.color.c1,R.color.c2,R.color.c3,R.color.c4};//it only gives the location of the color
//
//        for(int id:image_collection){
//
//            imagesColors.add(new GroupImageColor(id,id));
//        }
        int[] bgColor = {ContextCompat.getColor(context, R.color.c1),
                ContextCompat.getColor(context, R.color.c2), ContextCompat.getColor(context, R.color.c3),
                ContextCompat.getColor(context, R.color.c4)};
        int[] image_collection = {R.mipmap.ic_supervisor_account_black_48dp, R.mipmap.ic_mood_bad_black_48dp, R.mipmap.ic_swap_vert_black_48dp, R.mipmap.ic_tag_faces_black_48dp};

        for (int i = 0; i < image_collection.length; i++) {
            imagesColors.add(new GroupImageColor(image_collection[i], bgColor[i]));
        }

    }

    @Override
    public GroupAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_group, parent, false);
        return new MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(GroupAdapter.MyViewHolder holder, int position) {

        GroupImageColor image = imagesColors.get(position);
        Group group = data.get(position);

        holder.categoryTv.setText(group.getName());
        holder.categoryIv.setImageResource(image.getImage());

        holder.ll.setBackgroundColor(image.getColor());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(ArrayList<Group> parsedData) {
        data = parsedData;
        notifyItemChanged(0, data.size());
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView categoryTv;
        ImageView categoryIv;
        LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            categoryTv = (TextView) itemView.findViewById(R.id.categoryName);
            categoryIv = (ImageView) itemView.findViewById(R.id.categoryImage);
            ll = (LinearLayout) itemView.findViewById(R.id.ll);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, QuoteActivity.class);
            String code = data.get(getAdapterPosition()).getCode();
            intent.putExtra("code", code);
            context.startActivity(intent);


        }
    }
}

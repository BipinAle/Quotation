package com.example.bipin.quotation.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bipin.quotation.R;
import com.example.bipin.quotation.pojos.Favourite_item;
import com.example.bipin.quotation.utility.Utility;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by bipin on 8/3/16.
 */
public class
FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<Favourite_item> data = new ArrayList<>();

    public FavouriteAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        SharedPreferences prefs = Utility.getPreferences(context);
        String hybrid = prefs.getString(Utility.KEY, "");
        Log.e("hybrid", hybrid);


        if (!hybrid.equals("")) {
            String[] new_quote = hybrid.split(";;");

            for (String newQuote : new_quote) {

                newQuote = newQuote.substring(0, newQuote.indexOf('-'));
                data.add(new Favourite_item(newQuote));
            }
        }


//        SharedPreferences preferences=context.getSharedPreferences("favouriteData",context.MODE_PRIVATE);
//        String likedData=preferences.getString("likedData",null);
//        data.add(new Favourite_item(likedData));


//        String [] quotes={"this is test","this is test","this is test","this is test","this is test","this is test","this is test","this is test"};
//
//        for (String  quote:quotes)
//        {
//         data.add(new Favourite_item(quote));
//        }


    }

    @Override
    public FavouriteAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_favourite, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteAdapter.MyViewHolder holder, int position) {
        Favourite_item item = data.get(position);

        holder.textView.setText(item.getQuote());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        ImageButton likedButton;


        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.singleFav);
            likedButton = (ImageButton) itemView.findViewById(R.id.liked);
            likedButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.liked) {

                //to remove favourite from list
                data.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), data.size());
            }
//
//            Favourite_item favourite_item = data.get(getAdapterPosition());
//            String likedQuote = favourite_item.getQuote();


        }
    }
}

package com.example.lenovo.baking.Adapters;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.baking.Activities.ShowVideoActivity;
import com.example.lenovo.baking.Model.DetailsModel;
import com.example.lenovo.baking.Model.tabletModel;
import com.example.lenovo.baking.R;
import com.example.lenovo.baking.Fragments.VideoInTablet;

import java.util.ArrayList;

public class StepsItemsAdapter extends RecyclerView.Adapter<StepsItemsAdapter.ViewHolderClass> {

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView item_num, item_name;

        public ViewHolderClass(View itemView) {
            super(itemView);
            item_num = itemView.findViewById(R.id.item_number);
            item_name = itemView.findViewById(R.id.item_name);
        }
    }

    Context context;
    ArrayList<DetailsModel> item_list;
    tabletModel model = new tabletModel();


    public StepsItemsAdapter(Context context, ArrayList<DetailsModel> item_list) {
        this.context = context;
        this.item_list = item_list;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_recycler_item, null);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, final int position) {
        holder.item_num.setText("" + position);
        holder.item_name.setText(item_list.get(position).shortDescription);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Intent intent = new Intent(context, ShowVideoActivity.class);
                    intent.putExtra("Description", item_list.get(position).description);
                    intent.putExtra("VideoUrl", item_list.get(position).videoURL);
                    context.startActivity(intent);
                } else {
                    model.setDescription1(item_list.get(position).description);
                    model.setVideoURL1(item_list.get(position).videoURL);


                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    VideoInTablet myFragment = new VideoInTablet();
                    FragmentManager fragMan = activity.getFragmentManager();
                    FragmentTransaction fragTransaction = fragMan.beginTransaction();
                    fragTransaction.replace(R.id.container, myFragment);
                    fragTransaction.commit();

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }


}

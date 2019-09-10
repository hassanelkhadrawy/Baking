package com.example.lenovo.baking.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.baking.Activities.DetailsActivity;
import com.example.lenovo.baking.Model.IngredientsModel;
import com.example.lenovo.baking.R;

import java.util.ArrayList;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.viewholder> {

    public class viewholder extends RecyclerView.ViewHolder {

        TextView item_num, item_name;

        public viewholder(View itemView) {
            super(itemView);
            item_num = itemView.findViewById(R.id.item_number);
            item_name = itemView.findViewById(R.id.item_name);
        }
    }

    Context context;
    ArrayList<String> item_list;
    IngredientsModel model=new IngredientsModel();
    public MainRecyclerAdapter(Context context, ArrayList<String> item_list) {
        this.context = context;
        this.item_list = item_list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_recycler_item, null);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {
        holder.item_num.setText("" + position);
        holder.item_name.setText(item_list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("position", position);
                model.setId(position);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }


}

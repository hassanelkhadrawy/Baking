package com.example.lenovo.baking.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.baking.Model.IngredientsModel;
import com.example.lenovo.baking.R;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolderClass> {

    public class ViewHolderClass extends RecyclerView.ViewHolder{
TextView item_num,item_quantity,item_measure,ingredient;
        public ViewHolderClass(View itemView) {
            super(itemView);
            item_num = itemView.findViewById(R.id.ing_number);
            item_quantity = itemView.findViewById(R.id.ing_quantity);
            item_measure = itemView.findViewById(R.id.ing_measure);
            ingredient = itemView.findViewById(R.id.ingredient);

        }
    }
  Context context;
    ArrayList<IngredientsModel>list_Items;
    IngredientsModel model=new IngredientsModel();
    public IngredientAdapter(Context context, ArrayList<IngredientsModel> list_Items) {
        this.context = context;
        this.list_Items = list_Items;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.ingredient_item,null);

        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, final int position) {
        holder.item_num.setText(""+position);
        holder.item_quantity.append(list_Items.get(position).quantity);
        holder.item_measure.append(list_Items.get(position).measure);
        holder.ingredient.append(list_Items.get(position).ingredient);



    }

    @Override
    public int getItemCount() {
        return list_Items.size();
    }


}

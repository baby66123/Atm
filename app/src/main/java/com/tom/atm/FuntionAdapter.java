package com.tom.atm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FuntionAdapter extends RecyclerView.Adapter<FuntionAdapter.FunctionViewHolder>{
    private final String[] funtions;
    Context context;

    public FuntionAdapter(Context context) {
        this.context = context;
        funtions=context.getResources().getStringArray(R.array.funtions);
    }

    @NonNull
    @Override
    public FunctionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                android.R.layout.simple_list_item_1, viewGroup ,false);
        return new FunctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionViewHolder holder, int position) {
        holder.nameText.setText(funtions[position]);
    }

    @Override
    public int getItemCount() {
        return funtions.length;
    }

    public class FunctionViewHolder extends RecyclerView.ViewHolder{
        TextView nameText;
        public FunctionViewHolder(View itemView) {
            super(itemView);
            nameText=itemView.findViewById(android.R.id.text1);
        }
    }
}

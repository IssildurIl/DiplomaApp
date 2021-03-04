package ru.sfedu.diplomaapp.utils.RecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.sfedu.diplomaapp.R;

import java.util.ArrayList;

public class RecycleViewAdaptor extends RecyclerView.Adapter<RecycleViewAdaptor.CustomViewHolder> {

    private ArrayList<ExampleItem> mExampleList;
    Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public RecycleViewAdaptor(Context ct,ArrayList<ExampleItem> ExampleList){
        context=ct;
        mExampleList=ExampleList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleviewraw, parent, false);
        return new CustomViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);
        holder.tvName.setText(currentItem.getText());
        holder.tvRace.setText(currentItem.getText1());
        holder.tvLvl.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView tvName,tvRace,tvLvl;
        public CustomViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textName);
            tvRace = itemView.findViewById(R.id.textRace);
            tvLvl = itemView.findViewById(R.id.textLvl);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) listener.onItemClick(position);
                }
            });
        }
    }
}
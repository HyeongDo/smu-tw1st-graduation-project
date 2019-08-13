package com.smu.tw1st;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RcvAdapter extends RecyclerView.Adapter<RcvAdapter.ViewHolder> {

     private ArrayList<Data> mlist = null;
     private RcvClickListener mListener;

     public interface RcvClickListener{
         void onItemClicked(int position);
     }

     public void setOnClickListener(RcvClickListener listener){
         mListener = listener;
     }

     public class ViewHolder extends RecyclerView.ViewHolder{
         TextView tvName;
         TextView tvMoney;
         ImageView ivIcon;

         ViewHolder(View itemView){
             super(itemView);

             tvName = itemView.findViewById(R.id.tvIcon);
             tvMoney = itemView.findViewById(R.id.tvMoney);
             ivIcon = itemView.findViewById(R.id.ivIcon);

         }
     }

     RcvAdapter(ArrayList<Data> list){
         mlist = list;
     }


    @NonNull
    @Override
    public RcvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_result_item,parent,false);
         return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RcvAdapter.ViewHolder holder, final int position) {

         ViewHolder viewHolder = holder;

         viewHolder.ivIcon.setBackgroundResource(mlist.get(position).getIcon());
         viewHolder.tvName.setText(mlist.get(position).getName());
         viewHolder.tvMoney.setText(mlist.get(position).getMoney());

         if(mListener != null){
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     mListener.onItemClicked(position);
                 }
             });
         }
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}

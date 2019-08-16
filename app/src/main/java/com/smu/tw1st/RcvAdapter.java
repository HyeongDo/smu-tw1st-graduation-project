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

    public interface RcvClickListener {
        void onItemClicked(ViewHolder holder, View view, int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvMoney;
        ImageView ivIcon;

        ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvIcon);
            tvMoney = itemView.findViewById(R.id.tvMoney);
            ivIcon = itemView.findViewById(R.id.ivIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (mListener != null) {
                        mListener.onItemClicked(ViewHolder.this, view, position);
                    }
                }
            });
        }


        public void setOnItemClickListener(RcvClickListener listener) {
            mListener = listener;
        }
    }

    public RcvAdapter(ArrayList<Data> list) {
        mlist = list;
    }


    @NonNull
    @Override
    public RcvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_result_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RcvAdapter.ViewHolder holder, final int position) {

        holder.ivIcon.setImageResource(mlist.get(position).getIcon());
        holder.tvName.setText(mlist.get(position).getName());
        holder.tvMoney.setText(mlist.get(position).getMoney());

        holder.setOnItemClickListener(mListener);
    }

    public void setOnClickListener(RcvClickListener listener){
        mListener = listener;
    }


    @Override
    public int getItemCount() {
        return mlist.size();
    }
}

package com.sqlserverclient;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context mContext;
    private List<com.sqlserverclient.PolyModelClass> mData;
    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public Adaptery(Context mContext, List<com.sqlserverclient.PolyModelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.item_polyclinic, parent, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(mData.get(position).getName());
        holder.address.setText(mData.get(position).getAddress());
        holder.department.setText(mData.get(position).getDepartment());
        holder.detail.setText(mData.get(position).getDetail());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView address;
        TextView department;
        TextView detail;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            department = itemView.findViewById(R.id.department);
            detail = itemView.findViewById(R.id.detail);
//            description.setMovementMethod(new ScrollingMovementMethod());
//            image = itemView.findViewById(R.id.f_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}

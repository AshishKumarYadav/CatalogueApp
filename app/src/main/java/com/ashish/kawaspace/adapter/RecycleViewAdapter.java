package com.ashish.kawaspace.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.ashish.kawaspace.R;
import com.ashish.kawaspace.model.Result;
import com.ashish.kawaspace.model.UserInfoModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.EmployeeListViewHolder> {
    String TAG= RecycleViewAdapter.class.getSimpleName();
    private Context mCtx;
    List<Result> userInfoModel;
    onSelectedCardListener onSelectedCardListener;
    int selectedIndex=-1;
    public RecycleViewAdapter(Context mCtx, List<Result> userInfoModelList,onSelectedCardListener selectedCardListener) {
        this.mCtx = mCtx;
        this.userInfoModel = userInfoModelList;
        this.onSelectedCardListener =selectedCardListener;


    }


    @Override
    public RecycleViewAdapter.EmployeeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.user_list, parent, false);
        return new RecycleViewAdapter.EmployeeListViewHolder(view);
    }

    public interface onSelectedCardListener{

        public void onItemSelected(int position,Result result);
    }


    @Override
    public void onBindViewHolder(RecycleViewAdapter.EmployeeListViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.genderTv.setText(userInfoModel.get(position).getGender().substring(0, 1).toUpperCase()+userInfoModel.get(position).getGender().substring(1).toLowerCase()+"."+
                userInfoModel.get(position).getNat());
        holder.nameTv.setText(userInfoModel.get(position).name.getTitle()+" "+userInfoModel.get(position).name.getFirst()+" "+userInfoModel.get(position).name.getLast());
        holder.emailTv.setText(userInfoModel.get(position).getEmail());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedIndex= holder.getAdapterPosition();
                onSelectedCardListener.onItemSelected(holder.getAdapterPosition(), userInfoModel.get(holder.getAdapterPosition()));
                notifyDataSetChanged();

            }
        });
        if (selectedIndex==-1&&holder.getAdapterPosition()==0){
            selectedIndex=0;
            onSelectedCardListener.onItemSelected(position,userInfoModel.get(holder.getAdapterPosition()));


        }

        if (selectedIndex== holder.getAdapterPosition()){

            holder.cardView.setCardBackgroundColor(mCtx.getResources().getColor(R.color.card_purple));
            holder.nameTv.setTextColor(mCtx.getResources().getColor(R.color.white));
            holder.genderTv.setTextColor(mCtx.getResources().getColor(R.color.white));
            holder.emailTv.setTextColor(mCtx.getResources().getColor(R.color.white));

        }else {
            holder.cardView.setCardBackgroundColor(mCtx.getResources().getColor(R.color.white));
            holder.nameTv.setTextColor(mCtx.getResources().getColor(R.color.black));
            holder.genderTv.setTextColor(mCtx.getResources().getColor(R.color.black));
            holder.emailTv.setTextColor(mCtx.getResources().getColor(R.color.red));
        }




    }

    @Override
    public int getItemCount() {
        return userInfoModel.size();
    }


    class EmployeeListViewHolder extends RecyclerView.ViewHolder {

        TextView nameTv,emailTv,genderTv;
        CardView cardView;

        public EmployeeListViewHolder(View itemView) {
            super(itemView);

                nameTv=itemView.findViewById(R.id.name);
                emailTv=itemView.findViewById(R.id.email_id);
                genderTv=itemView.findViewById(R.id.gender_text);
                cardView=itemView.findViewById(R.id.card_view);

        }


    }


}

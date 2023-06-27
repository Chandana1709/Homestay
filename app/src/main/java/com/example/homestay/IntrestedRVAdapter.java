package com.example.homestay;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;

public class IntrestedRVAdapter extends RecyclerView.Adapter<IntrestedRVAdapter.ViewHolder> {


    private Context context;
    private ArrayList<IntrestedModel> propertyModelArrayList;


    public IntrestedRVAdapter(Context context, ArrayList<IntrestedModel> propertyModelArrayList) {
        this.context = context;
        this.propertyModelArrayList = propertyModelArrayList;
    }

    public IntrestedRVAdapter(IntrestedViewActivity intrestedViewActivity, ArrayList<IntrestedModel> propertyModalArrayList) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.intrested_rv_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IntrestedModel property = propertyModelArrayList.get(position);

        holder.propertyName.setText(property.getPropertyName());
        holder.propertyAmount.setText(property.getPropertyAmount());
        holder.userEmail.setText(property.getEmail());


        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdatePropertyActivity.class);


                intent.putExtra("propertyName", property.getPropertyName());
                intent.putExtra("propertyAddress", property.getPropertyAmount());


                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return propertyModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView propertyName;
        private final TextView propertyAddress;
        private final TextView userEmail;
        public ImageSlider imageslider;
        public final Button update;
        public final TextView propertyAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            propertyName = itemView.findViewById(R.id.idpropertyName);
            propertyAddress = itemView.findViewById(R.id.idpropertyAddress);
            userEmail = itemView.findViewById(R.id.iduserEmail);
            propertyAmount = itemView.findViewById(R.id.idpropertyAmount);
            update=itemView.findViewById(R.id.update);
            update.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), UpdatePropertyActivity.class);
                IntrestedModel property = propertyModelArrayList.get(getAdapterPosition());
                intent.putExtra("propertyName", property.getPropertyName());
                intent.putExtra("email",property.getEmail());
                itemView.getContext().startActivity(intent);
            });

        }
    }
}

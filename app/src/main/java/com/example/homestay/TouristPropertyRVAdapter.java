package com.example.homestay;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class TouristPropertyRVAdapter extends RecyclerView.Adapter<TouristPropertyRVAdapter.ViewHolder> {


    private Context context;
    private ArrayList<TouristPropertyModel> propertyModelArrayList;


    public TouristPropertyRVAdapter(Context context, ArrayList<TouristPropertyModel> propertyModelArrayList) {
        this.context = context;
        this.propertyModelArrayList = propertyModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tourist_property_rv_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TouristPropertyModel property = propertyModelArrayList.get(position);

        holder.propertyName.setText(property.getPropertyName());
        holder.propertyAddress.setText(property.getPropertyAddress());
//        holder.userEmail.setText(property.getEmail());
        holder.ratingBar.setRating(property.getRating());

        List<SlideModel> images = property.getImages();
        if (images != null && !images.isEmpty()) {
            holder.imageslider.setImageList(images, ScaleTypes.CENTER_CROP);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdatePropertyActivity.class);


                intent.putExtra("propertyName", property.getPropertyName());

                intent.putExtra("propertyAddress", property.getPropertyAddress());
                intent.putExtra("propertyAmount", property.getPropertyAmount());
                intent.putExtra("propertyParking", property.getPropertyParking());
                intent.putExtra("propertyCooking", property.getPropertyCooking());

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
        public RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            propertyName = itemView.findViewById(R.id.idpropertyName);
            propertyAddress = itemView.findViewById(R.id.idpropertyAddress);
            userEmail = itemView.findViewById(R.id.iduserEmail);
            imageslider= itemView.findViewById(R.id.image_slider);
            update=itemView.findViewById(R.id.update);
            ratingBar = itemView.findViewById(R.id.rbStars);
            update.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(),TouristPropertyDetailesActivity.class);
                TouristPropertyModel property = propertyModelArrayList.get(getAdapterPosition());
                intent.putExtra("propertyName", property.getPropertyName());
                intent.putExtra("email",property.getEmail());
                itemView.getContext().startActivity(intent);
            });

        }
    }
}


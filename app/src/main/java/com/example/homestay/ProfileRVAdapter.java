//package com.example.homestay;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.denzcoskun.imageslider.ImageSlider;
//import com.denzcoskun.imageslider.constants.ScaleTypes;
//import com.denzcoskun.imageslider.models.SlideModel;
//import com.parse.ParseException;
//import com.parse.ParseFile;
//
//import java.text.CollationElementIterator;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ProfileRVAdapter {
//
//    private Context context;
//    private ArrayList<ProfileModel> profileModelArrayList;
//
//    public ProfileRVAdapter(Context context, ArrayList<ProfileModel> profileModelArrayList) {
//        this.context = context;
//        this.profileModelArrayList = profileModelArrayList;
//    }
//
//    @NonNull
//    @Override
//    public PropertyRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.activity_profile_view, parent, false);
//        return new ProfileRVAdapter.ViewHolder(view);
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(@NonNull ProfileRVAdapter.ViewHolder holder, int position) {
//        ProfileModel profile = profileModelArrayList.get(position);
//
//        holder.userName.setText(profile.getUserName());
//       // holder.propertyAddress.setText(profile.getPropertyAddress());
//        holder.userEmail.setText(profile.getEmail());
//
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, UpdateProfileActivity.class);
////                ParseFile imageByteArray = (ParseFile) profile.getImages();
////                if (imageByteArray != null) {
////                    try {
////                        intent.putExtra("images", imageByteArray.getData());
////                    } catch (ParseException ex) {
////                        ex.printStackTrace();
////                    }
////                }
//
//                intent.putExtra("userName", profile.getUserName());
//
//                context.startActivity(intent);
//            }
//        });
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return profileModelArrayList.size();
//    }
//
//    //public class ViewHolder extends RecyclerView.ViewHolder {
//        public class ViewHolder  {
//        // creating variables for our text views.
//        private final TextView userName;
//        private final TextView userEmail;
//
//
//        public ViewHolder(@NonNull View itemView) {
//            super();
//            // initializing our text views.
//            userName = itemView.findViewById(R.id.iduserName);
//            userEmail = itemView.findViewById(R.id.iduserEmail);
//
//        }
//    }
//
//}

//package com.elton.s6android7.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.elton.s6android7.R;
//
//import java.util.List;
//import java.util.Map;
//
//public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
//    private List<Map<String, String>> locationList;
//    public ImageView mediaImageView;
//    public TextView titleTextView;
//    public TextView secondaryTextView;
//    public TextView supportingTextView;
//    public LocationAdapter(List<Map<String, String>> locationList) {
//        this.locationList = locationList;
//    }
//
//    public LocationAdapter() {
//
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Map<String, String> location = locationList.get(position);
//
//        // Bind the location data to the card layout
//        holder.titleTextView.setText(location.get("name"));
//        holder.secondaryTextView.setText(location.get("description"));
//
//        // Set other data as needed
//
//
//        // Set click listeners or any other custom logic for the card
//    }
//
//    @Override
//    public int getItemCount() {
//        return locationList != null ? locationList.size() : 0;
//    }
//
//    public void setLocationList(List<Map<String, String>> locationList) {
//        this.locationList = locationList;
//        notifyDataSetChanged();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView titleTextView;
//        public TextView secondaryTextView;
//        // Add other views as needed
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mediaImageView = itemView.findViewById(R.id.media_image);
//            titleTextView = itemView.findViewById(R.id.title);
//            secondaryTextView = itemView.findViewById(R.id.desc);
//            supportingTextView = itemView.findViewById(R.id.city);
//        }
//    }
//}

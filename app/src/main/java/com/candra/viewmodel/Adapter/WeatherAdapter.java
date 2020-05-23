package com.candra.viewmodel.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.candra.viewmodel.R;
import com.candra.viewmodel.WeatherItem;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private ArrayList<WeatherItem> candra = new ArrayList<>();


    public void setCandra(ArrayList<WeatherItem> candra1){
            candra.clear();
            candra.addAll(candra1);
            notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather,parent,false);
        return  new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.WeatherViewHolder holder, int position) {
        holder.bind(candra.get(position));
    }

    @Override
    public int getItemCount() {
        return candra.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCity,textViewTemperature,textViewDescription;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCity = itemView.findViewById(R.id.textCity);
            textViewDescription = itemView.findViewById(R.id.textDesc);
            textViewTemperature = itemView.findViewById(R.id.textTemp);
        }
        void bind(WeatherItem WeatherItem){
            textViewCity.setText(WeatherItem.getName());
            textViewTemperature.setText(WeatherItem.getTemperature());
            textViewDescription.setText(WeatherItem.getDeskripsi());
        }
    }
}

package com.yatham.earthquakemonitor;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yatham.earthquakemonitor.dao.Quake;

import java.util.List;

public class EarthquakesAdapter extends RecyclerView.Adapter<EarthquakesAdapter.ViewHolder> {
    private List<Quake> quakes;
    private OnItemClickListener onItemClickListener;

    interface OnItemClickListener {
        void onItemClicked(Quake quake);
    }

    public EarthquakesAdapter(List<Quake> quakes, OnItemClickListener onItemClickListener) {
        this.quakes = quakes;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_earthquake, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(quakes.get(position));
    }

    @Override
    public int getItemCount() {
        return quakes == null ? 0 : quakes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPlaceName;
        TextView textViewMagnitude;
        View quakeItemContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPlaceName = itemView.findViewById(R.id.textViewPlaceName);
            textViewMagnitude = itemView.findViewById(R.id.textViewMagnitude);
            quakeItemContainer = itemView.findViewById(R.id.quakeContainer);
        }

        public void bind(final Quake quake) {
            textViewPlaceName.setText(quake.properties.place);
            textViewMagnitude.setText(String.valueOf(quake.properties.mag));
            quakeItemContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClicked(quake);
                }
            });
            if (quake.properties.mag >= 0 && quake.properties.mag < 1) {
                quakeItemContainer.setBackgroundColor(Color.parseColor("#00FF00"));
            } else if (quake.properties.mag >= 0.9 && quake.properties.mag < 2) {

            } else if (quake.properties.mag >= 9 && quake.properties.mag < 10) {
                quakeItemContainer.setBackgroundColor(Color.parseColor("#FF0000"));
            }
        }
    }
}

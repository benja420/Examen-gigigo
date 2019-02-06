package com.monolabs.benja.examengigigo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 02/02/2019.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Locations> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public Adapter(List<Locations> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_location_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Locations locations = moviesList.get(position);
        holder.title.setText(locations.getLugar());
        holder.genre.setText(locations.getCiudad());
        holder.year.setText(locations.getOordenadas());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}

package com.prakashgelani.newupcomingmovies.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prakashgelani.newupcomingmovies.MovieDetailsActivity;
import com.prakashgelani.newupcomingmovies.MovieListActivity;
import com.prakashgelani.newupcomingmovies.R;
import com.prakashgelani.newupcomingmovies.modal.ListItemModal;

import java.util.ArrayList;


public class AdapterMovieList extends RecyclerView.Adapter<AdapterMovieList.MyViewHolder> {


    private ArrayList<ListItemModal> arrayListMovies;
    private MovieListActivity activity;

    public AdapterMovieList(MovieListActivity activity, ArrayList<ListItemModal> arrayListMovies) {
        this.activity = activity;
        this.arrayListMovies = arrayListMovies;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_movie_list, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.txt_movie_name.setText(arrayListMovies.get(position).getStrTitle());
        holder.txt_movie_date.setText(arrayListMovies.get(position).getStrReleaseDate());
        if (arrayListMovies.get(position).getStrAdult().equalsIgnoreCase("false")) {
            holder.txt_movie_adult.setText("(U/A)");
        } else {
            holder.txt_movie_adult.setText("(A)");
        }
        holder.rl_view_item.setTag(position);
        holder.rl_view_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MovieDetailsActivity.class);
                i.putExtra("arrayMovie", arrayListMovies);
                i.putExtra("arrayPos", position);
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListMovies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private RelativeLayout rl_view_item;
        private TextView txt_movie_name, txt_movie_date, txt_movie_adult;
        private ImageView img_movie;

        public MyViewHolder(View view) {
            super(view);
            img_movie = view.findViewById(R.id.img_movie);
            txt_movie_name = view.findViewById(R.id.txt_movie_name);
            txt_movie_date = view.findViewById(R.id.txt_movie_date);
            txt_movie_adult = view.findViewById(R.id.txt_movie_adult);
            rl_view_item = view.findViewById(R.id.rl_view_item);

        }
    }

}

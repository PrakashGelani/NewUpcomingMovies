package com.prakashgelani.newupcomingmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prakashgelani.newupcomingmovies.modal.ListItemModal;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private MovieDetailsActivity activity;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        activity = MovieDetailsActivity.this;
        init();
    }

    private void init() {
        ArrayList<ListItemModal> arrayMovieItem = this.getIntent().getExtras().getParcelableArrayList("arrayMovie");
        int arrayPos = getIntent().getExtras().getInt("arrayPos");

        final ArrayList<Integer> arrayImg = new ArrayList<>();
        arrayImg.add(R.mipmap.icon_movies);
        arrayImg.add(R.mipmap.icon_movies);
        arrayImg.add(R.mipmap.icon_movies);
        arrayImg.add(R.mipmap.icon_movies);
        arrayImg.add(R.mipmap.icon_movies);
        final ViewPager mImageViewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mImageViewPager, true);
        PageAdapter adapter = new PageAdapter(activity, arrayImg);
        mImageViewPager.setAdapter(adapter);

/*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public int currentPage;

            public void run() {
                if (currentPage == arrayImg.size()) {
                    currentPage = 0;
                }
                mImageViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, 500, 3000);

        TextView txt_movie_name = findViewById(R.id.txt_movie_name);
        txt_movie_name.setText(arrayMovieItem.get(arrayPos).getStrTitle());
        TextView txt_movie_name1 = findViewById(R.id.txt_movie_name1);
        txt_movie_name1.setText(arrayMovieItem.get(arrayPos).getStrTitle());
        TextView txt_movie_over_view = findViewById(R.id.txt_movie_over_view);
        txt_movie_over_view.setText(arrayMovieItem.get(arrayPos).getStrOverview());

        RatingBar ratingbar = findViewById(R.id.ratingBar);
        float value = Float.parseFloat(arrayMovieItem.get(arrayPos).getStrVoteAverage());
        float value1 = value / 2;
        ratingbar.setRating(value1);

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    public class PageAdapter extends PagerAdapter {
        private ArrayList<Integer> arrayList;
        // Declare Variables
        Context context;
        LayoutInflater inflater;

        public PageAdapter(Context context, ArrayList<Integer> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.view_pager_items, container, false);

            ImageView mImageView = (ImageView) itemView.findViewById(R.id.img_pager);
            ((ViewPager) container).addView(itemView);

//            Glide.with(activity).load(arrayList.get(position))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(mImageView);
//            Picasso.with(context).load(arrayList.get(position))
//                    .placeholder(R.drawable.paaye_laddu)
//                    .error(R.drawable.paaye_laddu).into(mImageView);

            Glide.with(activity)
                    .load(arrayList.get(position))
                    .placeholder(R.mipmap.icon_movies).into(mImageView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);
        }
    }
}

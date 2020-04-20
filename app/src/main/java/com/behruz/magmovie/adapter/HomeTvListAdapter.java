package com.behruz.magmovie.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Wim on 28/01/19.
 */

public class HomeTvListAdapter extends RecyclerView.Adapter<HomeTvListAdapter.MovieViewHolder> {

    private List<BaseTvShow> movieDatas;
    private Context context;
    private HomeTvListner homeTvListner;
    private OnMovieItemSelectedListener onMovieItemSelectedListener;

    public HomeTvListAdapter(Context context,HomeTvListner listner) {
        this.context = context;
        this.homeTvListner = listner;
        movieDatas = new ArrayList<>();
    }

    private void add(BaseTvShow item) {
        movieDatas.add(item);
        notifyItemInserted(movieDatas.size() - 1);
    }

    public void addAll(List<BaseTvShow> movieDatas) {
        for (BaseTvShow movieData : movieDatas) {
            add(movieData);
        }
    }

    public void remove(BaseTvShow item) {
        int position = movieDatas.indexOf(item);
        if (position > -1) {
            movieDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public BaseTvShow getItem(int position) {
        return movieDatas.get(position);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        final MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final BaseTvShow movieData = movieDatas.get(position);
        holder.bind(movieData);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeTvListner.ItemClick(movieData,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieDatas.size();
    }

    public void setOnMovieItemSelectedListener(OnMovieItemSelectedListener onMovieItemSelectedListener) {
        this.onMovieItemSelectedListener = onMovieItemSelectedListener;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView img;
        TextView txtYear,txtTitle;
        AVLoadingIndicatorView progressBar;

        public MovieViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progreesbar_home_fragment);
            img = itemView.findViewById(R.id.imageView_latest_gridview_adapter);
            txtYear = itemView.findViewById(R.id.textView_title_latest_gridview_adapter);
            txtTitle = itemView.findViewById(R.id.textView_author_latest_gridview_adapter);
        }

        public void bind(BaseTvShow movieData) {
            Glide.with(context)
                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + movieData.poster_path)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .dontTransform())
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(img);
//            Picasso.with(context)
//                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + movieData.poster_path)
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .fit().centerCrop()
//                    .noFade()
//                    .error(R.drawable.ic_launcher_background)
//                    .into(img);

            txtTitle.setText(movieData.original_name);
            txtYear.setText(StringUtils.getYear(movieData.first_air_date));


        }
    }



    public interface OnMovieItemSelectedListener {
        void onItemClick(View v, int position);
    }

}

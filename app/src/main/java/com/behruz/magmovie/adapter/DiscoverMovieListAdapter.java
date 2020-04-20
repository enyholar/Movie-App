package com.behruz.magmovie.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.ui.activity.MovieFullDetailActivity;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.StringUtils;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Wim on 5/29/17.
 */

public class DiscoverMovieListAdapter extends RecyclerView.Adapter<DiscoverMovieListAdapter.MovieViewHolder> {

    private List<BaseMovie> movieDatas;
    private Context context;

    private OnMovieItemSelectedListener onMovieItemSelectedListener;

    public DiscoverMovieListAdapter(Context context) {
        this.context = context;
        movieDatas = new ArrayList<>();
    }

    private void add(BaseMovie item) {
        movieDatas.add(item);
        notifyItemInserted(movieDatas.size() - 1);
    }

    public void addAll(List<BaseMovie> movieDatas) {
        for (BaseMovie movieData : movieDatas) {
            add(movieData);
        }
    }

    public void remove(BaseMovie item) {
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

    public BaseMovie getItem(int position) {
        return movieDatas.get(position);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_movie, parent, false);
        final MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = movieViewHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (onMovieItemSelectedListener != null) {
                        onMovieItemSelectedListener.onItemClick(movieViewHolder.itemView, adapterPos);
                    }
                }
            }
        });

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final BaseMovie movieData = movieDatas.get(position);
        holder.bind(movieData);
    }

    @Override
    public int getItemCount() {
        return movieDatas.size();
    }

    public void setOnMovieItemSelectedListener(OnMovieItemSelectedListener onMovieItemSelectedListener) {
        this.onMovieItemSelectedListener = onMovieItemSelectedListener;
    }

//    public class MovieViewHolder extends RecyclerView.ViewHolder {
//        ImageView img;
//        TextView txtDetails;
//
//        public MovieViewHolder(View itemView) {
//            super(itemView);
//            img = (ImageView) itemView.findViewById(R.id.img_thumb);
//            txtDetails = itemView.findViewById(R.id.txt_details);
//        }
//
//        public void bind(BaseMovie movieData) {
//            if (movieData.poster_path != null && !movieData.poster_path.isEmpty()){
//                Picasso.with(context)
//                        .load(Constant.IMG_URL + movieData.poster_path)
//                        .into(img);
//            }else if (movieData.backdrop_path != null && !movieData.backdrop_path.isEmpty()){
//                Picasso.with(context)
//                        .load(Constant.IMG_URL + movieData.backdrop_path)
//                        .into(img);
//            }else {
//                img.setImageResource(R.mipmap.ic_launcher);
//            }
//
//            txtDetails.setText(movieData.overview);
//
//        }
//    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private final AVLoadingIndicatorView progressBar;
        RoundedImageView img;
        TextView txtYear, txtTitle;

        public MovieViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView_latest_gridview_adapter);
            progressBar = itemView.findViewById(R.id.progreesbar_home_fragment);
            txtYear = itemView.findViewById(R.id.textView_title_latest_gridview_adapter);
            txtTitle = itemView.findViewById(R.id.textView_author_latest_gridview_adapter);
        }

        public void bind(BaseMovie movieData) {
//            Picasso.with(context)
//                    .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + movieData.poster_path)
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .fit().centerCrop()
//                    .noFade()
//                    .error(R.drawable.ic_launcher_background)
//                    .into(img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieFullDetailActivity.class);
                    Gson gson = new Gson();
                    String json = gson.toJson(movieData);
                    intent.putExtra("baseMovie", json);
                    context.startActivity(intent);
                }
            });
            Picasso.with(context)
                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + movieData.poster_path)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(img, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(context)
                                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + movieData.poster_path)
                                    .error(R.mipmap.ic_launcher)
                                    .into(img, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            progressBar.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError() {
                                            progressBar.setVisibility(View.GONE);
                                            Log.v("Picasso", "Could not fetch image");
                                        }
                                    });
                        }
                    });

            txtTitle.setText(movieData.original_title);
            if (movieData.release_date != null) {
                txtYear.setText(StringUtils.getYear(movieData.release_date));
            }


        }
    }

    public interface OnMovieItemSelectedListener {
        void onItemClick(View v, int position);
    }

}

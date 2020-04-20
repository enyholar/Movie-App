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
import com.behruz.magmovie.ui.activity.TvFullDetailActivity;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.StringUtils;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.BaseTvShow;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Wim on 5/29/17.
 */

public class DiscoverTvListAdapter extends RecyclerView.Adapter<DiscoverTvListAdapter.MovieViewHolder> {

    private List<BaseTvShow> tvDatas;
    private Context context;

    private OnMovieItemSelectedListener onMovieItemSelectedListener;

    public DiscoverTvListAdapter(Context context) {
        this.context = context;
        tvDatas = new ArrayList<>();
    }

    private void add(BaseTvShow item) {
        tvDatas.add(item);
        notifyItemInserted(tvDatas.size() - 1);
    }

    public void addAll(List<BaseTvShow> movieDatas) {
        for (BaseTvShow movieData : movieDatas) {
            add(movieData);
        }
    }

    public void remove(BaseTvShow item) {
        int position = tvDatas.indexOf(item);
        if (position > -1) {
            tvDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public BaseTvShow getItem(int position) {
        return tvDatas.get(position);
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
        final BaseTvShow movieData = tvDatas.get(position);
        holder.bind(movieData);
    }

    @Override
    public int getItemCount() {
        return tvDatas.size();
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
//        public void bind(BaseTvShow movieData) {
////            if (movieData.poster_path != null && !movieData.poster_path.isEmpty()){
////                Picasso.with(context)
////                        .load(Constant.IMG_URL + movieData.poster_path)
////                        .into(img);
////            }else if (movieData.backdrop_path != null && !movieData.backdrop_path.isEmpty()){
////                Picasso.with(context)
////                        .load(Constant.IMG_URL + movieData.backdrop_path)
////                        .into(img);
////            }else {
////                img.setImageResource(R.mipmap.ic_launcher);
////            }
//
//            Picasso.with(context)
//                    .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + movieData.poster_path)
//                    .networkPolicy(NetworkPolicy.OFFLINE)
//                    .into(img, new Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//
//                        @Override
//                        public void onError() {
//                            //Try again online if cache failed
//                            Picasso.with(context)
//                                    .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + movieData.poster_path)
//                                    .error(R.mipmap.ic_launcher)
//                                    .into(img, new Callback() {
//                                        @Override
//                                        public void onSuccess() {
//
//                                        }
//
//                                        @Override
//                                        public void onError() {
//                                            Log.v("Picasso","Could not fetch image");
//                                        }
//                                    });
//                        }
//                    });
//
//            txtDetails.setText(movieData.overview);
//
//        }
//    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView img;
        TextView txtYear,txtTitle;
        AVLoadingIndicatorView progressBar;

        public MovieViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView_latest_gridview_adapter);
            progressBar = itemView.findViewById(R.id.progreesbar_home_fragment);
            txtYear = itemView.findViewById(R.id.textView_title_latest_gridview_adapter);
            txtTitle = itemView.findViewById(R.id.textView_author_latest_gridview_adapter);
        }

        public void bind(BaseTvShow movieData) {
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
                    Intent intent = new Intent(context, TvFullDetailActivity.class);
                    Gson gson = new Gson();
                    String json = gson.toJson(movieData);
                    intent.putExtra("baseMovie", json);
                    context.startActivity(intent);
                }
            });
            Picasso.with(context)
                    .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + movieData.poster_path)
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
                                    .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + movieData.poster_path)
                                    .error(R.mipmap.ic_launcher)
                                    .into(img, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            progressBar.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError() {
                                            progressBar.setVisibility(View.GONE);
                                            Log.v("Picasso","Could not fetch image");
                                        }
                                    });
                        }
                    });

            txtTitle.setText(movieData.original_name);
            if (movieData.first_air_date != null){
                txtYear.setText(StringUtils.getYear(movieData.first_air_date));
            }


        }
    }

    public interface OnMovieItemSelectedListener {
        void onItemClick(View v, int position);
    }

}

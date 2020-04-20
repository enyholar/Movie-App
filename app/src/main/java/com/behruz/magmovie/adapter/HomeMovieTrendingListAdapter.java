package com.behruz.magmovie.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.StringUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.BaseMovie;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Wim on 5/29/17.
 */

public class HomeMovieTrendingListAdapter extends RecyclerView.Adapter<HomeMovieTrendingListAdapter.MovieViewHolder> {

    private List<BaseMovie> movieDatas;
    private Context context;

    private OnMovieItemSelectedListener onMovieItemSelectedListener;

    public HomeMovieTrendingListAdapter(Context context) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
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

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView img;
        TextView txtYear,txtTitle;

        public MovieViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView_latest_gridview_adapter);
            txtYear = itemView.findViewById(R.id.textView_title_latest_gridview_adapter);
            txtTitle = itemView.findViewById(R.id.textView_author_latest_gridview_adapter);
        }

        public void bind(BaseMovie movieData) {
            Picasso.with(context)
                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + movieData.poster_path)
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit().centerCrop()
                    .noFade()
                    .error(R.drawable.ic_launcher_background)
                    .into(img);

            txtTitle.setText(movieData.original_title);
            txtYear.setText(StringUtils.getYear(movieData.release_date));

        }
    }


//    public class MovieViewHolder extends RecyclerView.ViewHolder {
//        ImageView img;
//        TextView txt_title;
//
//        public MovieViewHolder(View itemView) {
//            super(itemView);
//
//            img = (ImageView) itemView.findViewById(R.id.home_movie_poster);
//            txt_title = itemView.findViewById(R.id.home_move_title);
//        }
//
//        public void bind(BaseMovie movieData) {
//            Picasso.with(context)
//                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + movieData.poster_path)
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .fit().centerCrop()
//                    .noFade()
//                    .error(R.drawable.ic_launcher_background)
//                    .into(img);
//
//            txt_title.setText(movieData.original_title);
//
//        }
//    }

    public interface OnMovieItemSelectedListener {
        void onItemClick(View v, int position);
    }

}

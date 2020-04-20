package com.behruz.magmovie.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.utils.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.uwetrottmann.tmdb2.entities.Image;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Wim on 5/29/17.
 */

public class ImageMovieListAdapter extends RecyclerView.Adapter<ImageMovieListAdapter.MovieViewHolder> {

    private List<Image> imageData;
    private Context context;

    private OnMovieItemSelectedListener onMovieItemSelectedListener;

    public ImageMovieListAdapter(Context context) {
        this.context = context;
        imageData = new ArrayList<>();
    }

    private void add(Image item) {
        imageData.add(item);
        notifyItemInserted(imageData.size() - 1);
    }

    public void addAll(List<Image> movieDatas) {
        for (Image movieData : movieDatas) {
            add(movieData);
        }
    }

    public void remove(Image item) {
        int position = imageData.indexOf(item);
        if (position > -1) {
            imageData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Image getItem(int position) {
        return imageData.get(position);
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
        final Image movieData = imageData.get(position);
        holder.bind(movieData);
    }

    @Override
    public int getItemCount() {
        return imageData.size();
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
        }

        public void bind(Image movieData) {
            Glide.with(context)
                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + movieData.file_path)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .dontTransform())
                    .into(img);

        }
    }

    public interface OnMovieItemSelectedListener {
        void onItemClick(View v, int position);
    }

}

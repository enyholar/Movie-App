package com.behruz.magmovie.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.behruz.magmovie.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.uwetrottmann.tmdb2.entities.Videos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enny on 20/10/17.
 */

public class MovieTrailerAdapter
    extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {

  private List<Videos.Video> tvDatas;
  private TrailerListner mListener;

  private Context mContext;


  public MovieTrailerAdapter(Context context,TrailerListner listner) {
    this.mContext = context;
    tvDatas = new ArrayList<>();
    this.mListener = listner;
  }

  private void add(Videos.Video item) {
    tvDatas.add(item);
    notifyItemInserted(tvDatas.size() - 1);
  }

  public void addAll(List<Videos.Video> movieDatas) {
    for (Videos.Video movieData : movieDatas) {
      add(movieData);
    }
  }

  public void remove(Videos.Video item) {
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

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    View noteView;
    noteView = inflater.inflate(R.layout.list_item_trailer, parent, false);
    return new ViewHolder(noteView);
  }

//  @Override
//  public int getItemCount() {
//    return null == mGenresList ? 0 : mGenresList.size();
//  }
  @Override
  public int getItemCount() {
    return tvDatas.size();
  }

  public Videos.Video getItem(int position) {
    return tvDatas.get(position);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    final int p = position;
    final Videos.Video model = tvDatas.get(p);
      if (model.site.equalsIgnoreCase("youtube")) {

        Glide.with(mContext)
                .load("http://img.youtube.com/vi/" + model.key + "/default.jpg")
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .dontTransform())
                .into(holder.movieLogo);
//          Glide.with(mContext)
//                  .load("http://img.youtube.com/vi/" + model.key + "/default.jpg")
//                  .into(holder.movieLogo);
      }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mListener.ItemClick(model, p);
      }
    });


  }



  class ViewHolder extends RecyclerView.ViewHolder {
    ImageView movieName;
    RoundedImageView movieLogo;
    View mItemView;

    ViewHolder(View itemView) {
      super(itemView);
      mItemView = itemView;
      movieLogo =  itemView.findViewById(R.id.trailer_image);
      movieName =  itemView.findViewById(R.id.play_trailer);
    }

  }
}

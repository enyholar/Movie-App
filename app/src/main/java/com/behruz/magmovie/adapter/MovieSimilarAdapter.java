package com.behruz.magmovie.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.utils.Constant;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.BaseMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enny on 20/10/17.
 */

public class MovieSimilarAdapter
    extends RecyclerView.Adapter<MovieSimilarAdapter.ViewHolder> {

  private List<BaseMovie> movieData;

  private Context mContext;


  public MovieSimilarAdapter(Context context) {
    this.mContext = context;
    movieData = new ArrayList<>();
  }

  private void add(BaseMovie item) {
    movieData.add(item);
    notifyItemInserted(movieData.size() - 1);
  }

  public void addAll(List<BaseMovie> movieDatas) {
    for (BaseMovie movieData : movieDatas) {
      add(movieData);
    }
  }

  public void remove(BaseMovie item) {
    int position = movieData.indexOf(item);
    if (position > -1) {
      movieData.remove(position);
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
    noteView = inflater.inflate(R.layout.list_item_movie_similar, parent, false);
    return new ViewHolder(noteView);
  }

//  @Override
//  public int getItemCount() {
//    return null == mGenresList ? 0 : mGenresList.size();
//  }
  @Override
  public int getItemCount() {
    return movieData.size();
  }

  public BaseMovie getItem(int position) {
    return movieData.get(position);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    final int p = position;
    final BaseMovie movieData = this.movieData.get(p);

    Picasso.with(mContext)
            .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + movieData.poster_path)
            .placeholder(R.drawable.ic_launcher_background)
            .fit().centerCrop()
            .noFade()
            .error(R.drawable.ic_launcher_background)
            .into(holder.imgThumb);

    holder.title.setText(movieData.original_title);
//    if (movieData.poster_path != null && !movieData.poster_path.isEmpty()){
//      Glide.with(mContext)
//              .load(Constant.IMG_URL + movieData.poster_path)
//              .apply(new RequestOptions()
//                      .diskCacheStrategy(DiskCacheStrategy.ALL)
//                      .placeholder(R.mipmap.ic_launcher))
//              .into(holder.imgThumb);
//    }else if (movieData.backdrop_path != null && !movieData.backdrop_path.isEmpty()){
//      Glide.with(mContext)
//              .load(Constant.IMG_URL + movieData.backdrop_path)
//              .apply(new RequestOptions()
//                      .diskCacheStrategy(DiskCacheStrategy.ALL)
//                      .placeholder(R.mipmap.ic_launcher))
//              .into(holder.imgThumb);
//    }else {
//      holder.imgThumb.setImageResource(R.mipmap.ic_launcher);
//    }
  }



  class ViewHolder extends RecyclerView.ViewHolder {
      ImageView imgThumb;
      TextView title;
    View mItemView;

    ViewHolder(View itemView) {
      super(itemView);
      mItemView = itemView;

        imgThumb = (ImageView) itemView.findViewById(R.id.home_movie_poster);
        title = itemView.findViewById(R.id.home_move_title);
    }

  }
}

package com.behruz.magmovie.adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.TvSeason;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enny on 20/10/17.
 */

public class TvSeasonAdapter
    extends RecyclerView.Adapter<TvSeasonAdapter.ViewHolder> {

  private List<TvSeason> datas;
  private TvSeriesListner listner;
  private Context mContext;


  public TvSeasonAdapter(Context context, TvSeriesListner tvSeriesListner) {
    this.mContext = context;
    datas = new ArrayList<>();
    this.listner = tvSeriesListner;
  }

  private void add(TvSeason item) {
    datas.add(item);
    notifyItemInserted(datas.size() - 1);
  }

  public void addAll(List<TvSeason> movieDatas) {
    for (TvSeason movieData : movieDatas) {
      add(movieData);
    }
    notifyDataSetChanged();
  }

  public void remove(TvSeason item) {
    int position = datas.indexOf(item);
    if (position > -1) {
      datas.remove(position);
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
    noteView = inflater.inflate(R.layout.item_tv_season_series, parent, false);
    return new ViewHolder(noteView);
  }

//  @Override
//  public int getItemCount() {
//    return null == mGenresList ? 0 : mGenresList.size();
//  }
  @Override
  public int getItemCount() {
    return datas.size();
  }

  public TvSeason
  getItem(int position) {
    return datas.get(position);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    final int p = position;
    final TvSeason model = datas.get(p);
    holder.name.setText(model.name);
    if (model.air_date != null){
      holder.originalName.setText(StringUtils.getYear(model.air_date));
    }
    holder.mItemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listner.ItemClick(model,position);
        }
    });


    Picasso.with(mContext)
            .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + model.poster_path)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .fit().centerCrop()
            .into(holder.poster, new Callback() {
              @Override
              public void onSuccess() {
                holder.progressBar.setVisibility(View.GONE);
              }

              @Override
              public void onError() {
                //Try again online if cache failed
                Picasso.with(mContext)
                        .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + model.poster_path)
                        .error(R.mipmap.ic_launcher)
                        .into(holder.poster, new Callback() {
                          @Override
                          public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                          }

                          @Override
                          public void onError() {
                            holder.progressBar.setVisibility(View.GONE);
                            Log.v("Picasso", "Could not fetch image");
                          }
                        });
              }
            });
  }



  class ViewHolder extends RecyclerView.ViewHolder {
    ImageView poster;
      TextView name,originalName,episodeCount,seasonCount;
    AVLoadingIndicatorView progressBar;
    View mItemView;

    ViewHolder(View itemView) {
      super(itemView);
      mItemView = itemView;
      poster = itemView.findViewById(R.id.search_poster);
      name = itemView.findViewById(R.id.search_name);
     progressBar = itemView.findViewById(R.id.progreesbar_home_fragment);
      originalName = itemView.findViewById(R.id.tv_airdate);
//      episodeCount = itemView.findViewById(R.id.tv_episodecount);
//      seasonCount = itemView.findViewById(R.id.tv_season_count);
    }

  }
}

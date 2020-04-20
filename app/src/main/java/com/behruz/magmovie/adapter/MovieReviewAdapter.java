package com.behruz.magmovie.adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.behruz.magmovie.R;
import com.uwetrottmann.tmdb2.entities.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enny on 20/10/17.
 */

public class MovieReviewAdapter
    extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {

  private List<Review> tvDatas;

  private Context mContext;


  public MovieReviewAdapter(Context context) {
    this.mContext = context;
    tvDatas = new ArrayList<>();
  }

  private void add(Review item) {
    tvDatas.add(item);
    notifyItemInserted(tvDatas.size() - 1);
  }

  public void addAll(List<Review> movieDatas) {
    for (Review movieData : movieDatas) {
      add(movieData);
    }
  }

  public void remove(Review item) {
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
    noteView = inflater.inflate(R.layout.list_item_review, parent, false);
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

  public Review
  getItem(int position) {
    return tvDatas.get(position);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    final int p = position;
    final Review model = tvDatas.get(p);
    holder.movieContent.setText(model.content);
    holder.movieReviewer.setText(model.author);
  }



  class ViewHolder extends RecyclerView.ViewHolder {
    AppCompatTextView movieContent;
      AppCompatTextView movieReviewer;
    View mItemView;

    ViewHolder(View itemView) {
      super(itemView);
      mItemView = itemView;
      movieReviewer =  itemView.findViewById(R.id.reviewers);
      movieContent =  itemView.findViewById(R.id.content);
    }

  }
}

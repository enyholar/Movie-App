package com.behruz.magmovie.adapter;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.model.GenresModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Enny on 20/10/17.
 */

public class MovieGenresAdapter
    extends RecyclerView.Adapter<MovieGenresAdapter.ViewHolder> {

  private List<GenresModel> mGenresList;
  private MovieGenreListner mListener;

  private Context mContext;

  public MovieGenresAdapter(Context context, List<GenresModel> radios, MovieGenreListner listner) {
    mContext = context;
    this.mGenresList = radios;
    this.mListener = listner;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    View noteView;
    noteView = inflater.inflate(R.layout.item_movie_genres, parent, false);
    return new ViewHolder(noteView);
  }

  @Override
  public int getItemCount() {
    return null == mGenresList ? 0 : mGenresList.size();
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    final int p = position;
    final GenresModel model = mGenresList.get(p);
    holder.genresName.setText(model.name);
        Glide.with(mContext)
            .load(model.flag)
            .into(holder.genresLogo);

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mListener.ItemClick(model, p);
      }
    });


  }

  public void updateDataSet(List<GenresModel> arraylist) {
    this.mGenresList = arraylist;
    notifyDataSetChanged();
  }


  class ViewHolder extends RecyclerView.ViewHolder {
    TextView genresName;
    AppCompatImageView genresLogo;
    View mItemView;

    ViewHolder(View itemView) {
      super(itemView);
      mItemView = itemView;
      genresName =  itemView.findViewById(R.id.genres_name);
      genresLogo =  itemView.findViewById(R.id.genres_image);
    }

  }
}

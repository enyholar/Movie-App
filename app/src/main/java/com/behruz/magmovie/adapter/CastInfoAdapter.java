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
import com.behruz.magmovie.ui.activity.PersonDetailsActivity;
import com.behruz.magmovie.utils.Constant;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.CastMember;
import com.uwetrottmann.tmdb2.entities.CrewMember;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enny on 20/10/17.
 */

public class CastInfoAdapter
    extends RecyclerView.Adapter<CastInfoAdapter.ViewHolder> {

  private List<CastMember> castMemberDatas;
  private List<CrewMember> datas;
  private Context mContext;


  public CastInfoAdapter(Context context) {
    this.mContext = context;
    castMemberDatas = new ArrayList<>();
  }

  private void add(CastMember item) {
    castMemberDatas.add(item);
    notifyItemInserted(castMemberDatas.size() - 1);
  }

  public void addAll(List<CastMember> movieDatas) {
    for (CastMember movieData : movieDatas) {
      add(movieData);
    }
  }

  public void remove(CastMember item) {
    int position = castMemberDatas.indexOf(item);
    if (position > -1) {
      castMemberDatas.remove(position);
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
    noteView = inflater.inflate(R.layout.list_item_cast, parent, false);
    return new ViewHolder(noteView);
  }

//  @Override
//  public int getItemCount() {
//    return null == mGenresList ? 0 : mGenresList.size();
//  }
  @Override
  public int getItemCount() {
    return castMemberDatas.size();
  }

  public CastMember
  getItem(int position) {
    return castMemberDatas.get(position);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    final int p = position;
    final CastMember model = castMemberDatas.get(p);
    holder.castCharacter.setText(model.character);
    holder.castName.setText(model.name);
//      Picasso.with(mContext)
//              .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + model.profile_path)
//              .placeholder(R.drawable.ic_launcher_background)
//              .fit().centerCrop()
//              .noFade()
//              .error(R.drawable.ic_launcher_background)
//              .into(holder.castProfileImage);
    holder.mItemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent person = PersonDetailsActivity.newIntent(mContext, String.valueOf(model.id));
        mContext.startActivity(person);
      }
    });

    Picasso.with(mContext)
            .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + model.profile_path)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(holder.castProfileImage, new Callback() {
              @Override
              public void onSuccess() {

              }

              @Override
              public void onError() {
                //Try again online if cache failed
                Picasso.with(mContext)
                        .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + model.profile_path)
                        .error(R.mipmap.ic_launcher)
                        .into(holder.castProfileImage, new Callback() {
                          @Override
                          public void onSuccess() {

                          }

                          @Override
                          public void onError() {
                            Log.v("Picasso","Could not fetch image");
                          }
                        });
              }
            });
  }



  class ViewHolder extends RecyclerView.ViewHolder {
    RoundedImageView castProfileImage;
      TextView castCharacter,castName;
    View mItemView;

    ViewHolder(View itemView) {
      super(itemView);
      mItemView = itemView;
      castCharacter =  itemView.findViewById(R.id.cast_character);
      castName =  itemView.findViewById(R.id.cast_name);
      castProfileImage = itemView.findViewById(R.id.profile_image);
    }

  }
}

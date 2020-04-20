package com.behruz.magmovie.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
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

import java.util.List;

/**
 * Created by Enny on 20/10/17.
 */

public class CastCrewGuestAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    final int VIEW_TYPE_CASTMEMBER = 0;
    final int VIEW_TYPE_CREWMEMBER = 1;
    final int VIEW_TYPE_GUESTMEMBER = 2;
    private List<CastMember> castMemberDatas;
    private List<CrewMember> crewMemberDatas;
    private List<CastMember> guestMemberDatas;
    private Context mContext;


    public CastCrewGuestAdapter(Context context,List<CastMember> castMemberData,List<CrewMember> crewMemberData,List<CastMember> guestMemberData) {
        this.mContext = context;
        this.castMemberDatas = castMemberData;
        this.crewMemberDatas = crewMemberData;
        this.guestMemberDatas = guestMemberData;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView;
        if(viewType == VIEW_TYPE_CASTMEMBER){
            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cast_member,parent,false);
            return new ViewHolder(itemView);
        }

        if(viewType == VIEW_TYPE_CREWMEMBER){
            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crew_member,parent,false);
            return new CrewHolder(itemView);
        }

        if(viewType == VIEW_TYPE_GUESTMEMBER){
            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cast_guest,parent,false);
            return new GuestHolder(itemView);
        }

        return null;
    }

////  @Override
////  public int getItemCount() {
////    return null == mGenresList ? 0 : mGenresList.size();
////  }
//  @Override
//  public int getItemCount() {
//    return castMemberDatas.size();
//  }

    public CastMember
    getItem(int position) {
        return castMemberDatas.get(position);
    }


    @Override
    public int getItemCount() {

      //  return null == castMemberDatas ? 0 : countryRadioListFiltered.size();
//        if (castMemberDatas  == null){
//
//        }

        return castMemberDatas.size() + crewMemberDatas.size() + guestMemberDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < castMemberDatas.size()) {
            return VIEW_TYPE_CASTMEMBER;
        }

        if (position - castMemberDatas.size() < crewMemberDatas.size()) {
            return VIEW_TYPE_CREWMEMBER;
        }

        if (position - castMemberDatas.size() + crewMemberDatas.size() < guestMemberDatas.size()) {
            return VIEW_TYPE_GUESTMEMBER;
        }

        return -1;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
        if(viewHolder instanceof ViewHolder){
            ((ViewHolder) viewHolder).populate(castMemberDatas.get(position));
        }

        if(viewHolder instanceof CrewHolder){
            ((CrewHolder) viewHolder).populate(crewMemberDatas.get(position - castMemberDatas.size() + guestMemberDatas.size()));
        }

        if(viewHolder instanceof GuestHolder){
            ((GuestHolder) viewHolder).populate(guestMemberDatas.get(position - castMemberDatas.size() + crewMemberDatas.size()));
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView castProfileImage;
        TextView castCharacter, castName;
        View mItemView;

        ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            castCharacter = itemView.findViewById(R.id.cast_character);
            castName = itemView.findViewById(R.id.cast_name);
            castProfileImage = itemView.findViewById(R.id.profile_image);
        }

        public void populate(CastMember model){
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent person = PersonDetailsActivity.newIntent(mContext, String.valueOf(model.id));
                    mContext.startActivity(person);
                }
            });
            castCharacter.setText(model.character);
            castName.setText(model.name);
            Picasso.with(mContext)
                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + model.profile_path)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(castProfileImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(mContext)
                                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + model.profile_path)
                                    .error(R.mipmap.ic_launcher)
                                    .into(castProfileImage, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso", "Could not fetch image");
                                        }
                                    });
                        }
                    });
        }

    }


    class CrewHolder extends RecyclerView.ViewHolder {
        RoundedImageView castProfileImage;
        AppCompatTextView castCharacter, castName;
        View mItemView;

        CrewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            castCharacter = itemView.findViewById(R.id.cast_character);
            castName = itemView.findViewById(R.id.cast_name);
            castProfileImage = itemView.findViewById(R.id.profile_image);
        }

        public void populate(CrewMember model){
            castCharacter.setText(model.job);
            castName.setText(model.name);
            Picasso.with(mContext)
                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + model.profile_path)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(castProfileImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(mContext)
                                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + model.profile_path)
                                    .error(R.mipmap.ic_launcher)
                                    .into(castProfileImage, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso", "Could not fetch image");
                                        }
                                    });
                        }
                    });
        }

    }

    class GuestHolder extends RecyclerView.ViewHolder {
        RoundedImageView castProfileImage;
        AppCompatTextView castCharacter, castName;
        View mItemView;

        GuestHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            castCharacter = itemView.findViewById(R.id.cast_character);
            castName = itemView.findViewById(R.id.cast_name);
            castProfileImage = itemView.findViewById(R.id.profile_image);
        }

        public void populate(CastMember model){
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent person = PersonDetailsActivity.newIntent(mContext, String.valueOf(model.id));
                    mContext.startActivity(person);
                }
            });
            castCharacter.setText(model.character);
            castName.setText(model.name);
            Picasso.with(mContext)
                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + model.profile_path)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(castProfileImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(mContext)
                                    .load(Constant.IMG_URLS + Constant.POSTER_SIZE_W342 + model.profile_path)
                                    .error(R.mipmap.ic_launcher)
                                    .into(castProfileImage, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso", "Could not fetch image");
                                        }
                                    });
                        }
                    });
        }


    }
}

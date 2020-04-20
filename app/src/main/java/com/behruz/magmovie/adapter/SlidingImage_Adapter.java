package com.behruz.magmovie.adapter;

import android.content.Context;
import android.os.Parcelable;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.utils.Constant;
import com.behruz.magmovie.utils.StringUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.BaseMovie;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<BaseMovie> arrayList;
    private LayoutInflater inflater;
    private Context context;
    private HomeMovieListner movieListner;


    public SlidingImage_Adapter(Context context,HomeMovieListner listner) {
        this.context = context;
        this.arrayList = new ArrayList<>();
        this.movieListner = listner;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    private void add(BaseMovie item) {
        arrayList.add(item);
    //    notifyItemInserted(arrayList.size() - 1);
    }

    public void addAll(List<BaseMovie> movieDatas) {
//        if (movieDatas.size() < 5) {
//            arrayList.addAll(movieDatas);
//        } else {
//            for (int i = 0; i < 5; i++) {
//                BaseMovie movie = movieDatas.get(i);
//                add(movie);
//            }
//        }
        for (BaseMovie movieData : movieDatas) {
            add(movieData);
        }

        notifyDataSetChanged();
    }

      public void clear() {
        while (getCount() > 0) {
           arrayList.clear();
        }
          notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.item_home_movie_sliding, view, false);

        assert imageLayout != null;
        final RoundedImageView imageView = (RoundedImageView) imageLayout
                .findViewById(R.id.imageView_latest_gridview_adapter);
      TextView txtYear = imageLayout.findViewById(R.id.textView_title_latest_gridview_adapter);
      TextView  txtTitle = imageLayout.findViewById(R.id.textView_author_latest_gridview_adapter);
      AVLoadingIndicatorView progressBar = imageLayout.findViewById(R.id.progreesbar_home_fragment);
        final BaseMovie movieData = arrayList.get(position);

        view.addView(imageLayout, 0);
//        Picasso.with(context)
//                .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + movieData.poster_path)
//                .placeholder(R.drawable.ic_launcher_background)
//                .fit().centerCrop()
//                .noFade()
//                .error(R.drawable.ic_launcher_background)
//                .into(imageView);
        txtTitle.setText(movieData.original_title);
        txtYear.setText(StringUtils.getYear(movieData.release_date));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieListner.ItemClick(movieData,position);
            }
        });

        Picasso.with(context)
                .load(Constant.IMG_URLS +  Constant.POSTER_SIZE_W342 + movieData.poster_path)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit().centerCrop()
                .into(imageView, new Callback() {
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
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        progressBar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError() {
                                        Log.v("Picasso","Could not fetch image");
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                    }
                });

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
package com.behruz.magmovie.ui.activity.onBoarding;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.behruz.magmovie.R;

import java.util.ArrayList;


/**
 * Created by Jaison
 */


class OnBoard_Adapter extends PagerAdapter {

    private Context mContext;
    ArrayList<OnBoardItem> onBoardItems=new ArrayList<>();
    public static Button tv_content;
    private OnBoardListner listner;


    public OnBoard_Adapter(Context mContext, ArrayList<OnBoardItem> items, OnBoardListner onBoardListner) {
        this.mContext = mContext;
        this.onBoardItems = items;
        this.listner = onBoardListner;
    }

    @Override
    public int getCount() {
        return onBoardItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.onboard_item, container, false);

        OnBoardItem item=onBoardItems.get(position);

        ConstraintLayout imageView =  itemView.findViewById(R.id.constraint);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            imageView.setBackgroundDrawable(ContextCompat.getDrawable(mContext, item.imageID) );
        } else {
            imageView.setBackground(ContextCompat.getDrawable(mContext,item.imageID));
        }

        TextView tv_title=(TextView)itemView.findViewById(R.id.tv_header);
        tv_title.setText(item.getTitle());

         tv_content= itemView.findViewById(R.id.next);
        tv_content.setText(item.getDescription());
        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.ItemClick(item,position);
            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }

}

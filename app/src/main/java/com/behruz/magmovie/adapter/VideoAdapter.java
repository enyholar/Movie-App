package com.behruz.magmovie.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.behruz.magmovie.R;
import com.behruz.magmovie.ui.activity.VideoActivity;
import com.behruz.magmovie.utils.Constant;
import com.squareup.picasso.Picasso;
import com.uwetrottmann.tmdb2.entities.Videos;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private List<Videos.Video> mVideos;

    public VideoAdapter(List<Videos.Video> videos) {
        mVideos = videos;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_video, parent, false);

        return new VideoHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        Videos.Video video = mVideos.get(position);
        holder.bindVideo(video);
    }

    @Override
    public int getItemCount() {
        return (mVideos != null) ? mVideos.size() : 0;
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private Videos.Video mVideo;

        @BindView(R.id.video_preview)
        ImageView videoPreview;
        @BindView(R.id.video_name)
        TextView videoName;

        VideoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        void bindVideo(Videos.Video video) {
            mVideo = video;
            Picasso.with(mContext)
                    .load(String.format(Constant.YOUTUBE_THUMBNAIL_URL, video.key))
                    .fit().centerCrop()
                    .into(videoPreview);
            videoName.setText(video.name);
        }

        @OnClick(R.id.video_root)
        void openVideo() {
            String url = String.format(Constant.YOUTUBE_EMBED_VIDEO_URL, mVideo.key);
            Intent intent = VideoActivity.newIntent(mContext, url);
            mContext.startActivity(intent);
        }

        @OnLongClick(R.id.video_root)
        boolean openOnExternalApp() {
            String url = String.format(Constant.YOUTUBE_VIDEO_URL, mVideo.key);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mContext.startActivity(intent);
            return true;
        }
    }
}

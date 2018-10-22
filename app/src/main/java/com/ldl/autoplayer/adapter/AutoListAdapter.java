package com.ldl.autoplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ldl.autoplayer.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class AutoListAdapter extends BaseAdapter {

    int[] videoIndexs = {0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1};
    Context context;
    LayoutInflater mInflater;

    public AutoListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return videoIndexs.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //This is the point
        if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof VideoHolder) {
            ((VideoHolder) convertView.getTag()).jcVideoPlayer.release();
        }

        if (videoIndexs[position] == 1) {
            VideoHolder viewHolder;
            if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof VideoHolder) {
                viewHolder = (VideoHolder) convertView.getTag();
            } else {
                viewHolder = new VideoHolder();
                convertView = mInflater.inflate(R.layout.item_videoview, null);
                viewHolder.jcVideoPlayer = (JCVideoPlayerStandard) convertView.findViewById(R.id.videoplayer);
                convertView.setTag(viewHolder);
            }

            boolean setUp = viewHolder.jcVideoPlayer.setUp(
                    "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4", JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    "");
            if (setUp) {
                Glide.with(context).load("https://p2.ssl.qhimgs1.com/sdr/200_200_/t01366725db37aced42.jpg").into(viewHolder.jcVideoPlayer.thumbImageView);
            }
        } else {

            ImageViewHolder imageViewHolder;
            if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof ImageViewHolder) {
                imageViewHolder = (ImageViewHolder) convertView.getTag();
            } else {
                imageViewHolder = new ImageViewHolder();
                LayoutInflater mInflater = LayoutInflater.from(context);
                convertView = mInflater.inflate(R.layout.item_textview, null);
                imageViewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
                Glide.with(context).load("https://ps.ssl.qhmsg.com/t01c204fe4ed7d26c57.jpg").into(imageViewHolder.imageView);
                convertView.setTag(imageViewHolder);
            }

        }
        return convertView;
    }

    class VideoHolder {
        JCVideoPlayerStandard jcVideoPlayer;
    }

    class ImageViewHolder {
        ImageView imageView;
    }
}

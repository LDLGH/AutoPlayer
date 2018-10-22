package com.ldl.autoplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ldl.autoplayer.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class AutoRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String[] videoUrls = {"http://video.jiecao.fm/5/1/%E8%87%AA%E5%8F%96%E5%85%B6%E8%BE%B1.mp4",
            "http://gslb.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4"};
    String[] videoThumbs = {"http://img4.jiecaojingxuan.com/2016/5/1/3430ec64-e6a7-4d8e-b044-9d408e075b7c.jpg",
            "http://img4.jiecaojingxuan.com/2016/3/14/2204a578-609b-440e-8af7-a0ee17ff3aee.jpg"};
    String[] videoTitles = {"嫂子坐火车", "嫂子打游戏"};
    int[] videoIndexs = {0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1};
    private Context context;
    public static final String TAG = "AutoRVAdapter";

    public AutoRVAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        return videoIndexs[position];
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == 1) {
            holder = new VedioViewHolder(LayoutInflater.from(context).inflate(R.layout.item_videoview, parent,
                    false));
        } else {
            holder = new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_textview, parent,
                    false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VedioViewHolder) {
            boolean setUp = ((VedioViewHolder) holder).jcVideoPlayer.setUp(
                    videoUrls[videoIndexs[position]], JCVideoPlayer.SCREEN_LAYOUT_LIST,
                    videoTitles[videoIndexs[position]]);
            if (setUp) {
                Glide.with(context).load(videoThumbs[videoIndexs[position]]).into(((VedioViewHolder) holder).jcVideoPlayer.thumbImageView);
            }
        }else if(holder instanceof ImageViewHolder){
            Glide.with(context).load("https://ps.ssl.qhmsg.com/t01c204fe4ed7d26c57.jpg").into(((ImageViewHolder) holder).imageView);
        }
    }

    @Override
    public int getItemCount() {
        return videoIndexs.length;
    }

    class VedioViewHolder extends RecyclerView.ViewHolder {
        JCVideoPlayerStandard jcVideoPlayer;

        public VedioViewHolder(View itemView) {
            super(itemView);
            jcVideoPlayer = itemView.findViewById(R.id.videoplayer);
        }
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }

    }
}

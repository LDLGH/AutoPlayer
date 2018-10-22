package com.ldl.autoplayer;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 监听recycleView滑动状态，
 * 自动播放可见区域内的第一个视频
 */
public class AutoPlayScrollListener extends RecyclerView.OnScrollListener {

    private int firstVisibleItem = 0;
    private int lastVisibleItem = 0;
    private int visibleCount = 0;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE://滚动停止
                autoPlayVideo(recyclerView);
            default:
                break;
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // firstVisibleItem  当前第一个可见的item
        // visibleCount  当前可见的item个数
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            firstVisibleItem = linearManager.findFirstVisibleItemPosition();
            lastVisibleItem = linearManager.findLastVisibleItemPosition();
            visibleCount = lastVisibleItem - firstVisibleItem;
        }
    }

    /**
     * 自动播放视频，并且播放列表视频第一个
     * */
    private void autoPlayVideo(RecyclerView view) {
        for (int i = 0; i <= visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.videoplayer) != null) {
                JCVideoPlayerStandard videoPlayerStandard1 = view.getChildAt(i).findViewById(R.id.videoplayer);
                Rect rect = new Rect();
                //获取当前view 的 位置
                videoPlayerStandard1.getLocalVisibleRect(rect);
                int videoheight3 = videoPlayerStandard1.getHeight();
                //当播放器完全显示出来
                if (rect.top == 0 && rect.bottom == videoheight3) {
                    //播放状态为正常或错误状态去播放视频
                    if (videoPlayerStandard1.currentState == JCVideoPlayer.CURRENT_STATE_NORMAL || videoPlayerStandard1.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                        //主动去调用控件的点击事件播放视频
                        videoPlayerStandard1.startButton.performClick();
                    }
                    return;
                }
            }
        }
        //释放其他视频资源
        JCVideoPlayer.releaseAllVideos();
    }
}

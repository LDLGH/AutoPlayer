package com.ldl.autoplayer;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.ldl.autoplayer.adapter.AutoListAdapter;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class AutoListFragment extends Fragment {

    private ListView lv_auto_list;
    public int firstVisible = 0, visibleCount = 0, totalCount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auto_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View v) {
        lv_auto_list = v.findViewById(R.id.lv_auto_list);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        recyclerView.addOnScrollListener
        lv_auto_list.setAdapter(new AutoListAdapter(getContext()));
        lv_auto_list.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        Log.e("videoTest", "SCROLL_STATE_FLING");
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        Log.e("videoTest", "SCROLL_STATE_IDLE");
                        autoPlayVideo(view);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        Log.e("videoTest", "SCROLL_STATE_TOUCH_SCROLL");

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // firstVisibleItem   当前第一个可见的item
                // visibleItemCount   当前可见的item个数
                if (firstVisible == firstVisibleItem) {
                    return;
                }
                firstVisible = firstVisibleItem;
                visibleCount = visibleItemCount;
                totalCount = totalItemCount;
            }
        });
    }

    void autoPlayVideo(AbsListView view) {
        Log.e("videoTest", "firstVisiblePos  =  " + firstVisible + "visibleItemCount =  " + visibleCount);
        for (int i = 0; i < visibleCount; i++) {
            if (view != null && view.getChildAt(i) != null && view.getChildAt(i).findViewById(R.id.videoplayer) != null) {
                JCVideoPlayerStandard videoPlayerStandard1 = (JCVideoPlayerStandard) view.getChildAt(i).findViewById(R.id.videoplayer);
                Rect rect = new Rect();
                videoPlayerStandard1.getLocalVisibleRect(rect);
                int videoheight3 = videoPlayerStandard1.getHeight();
                Log.e("videoTest", "i=" + i + "===" + "videoheight3:" + videoheight3 + "===" + "rect.top:" + rect.top + "===" + "rect.bottom:" + rect.bottom);
                if (rect.top == 0 && rect.bottom == videoheight3) {
                    if (videoPlayerStandard1.currentState == JCVideoPlayer.CURRENT_STATE_NORMAL || videoPlayerStandard1.currentState == JCVideoPlayer.CURRENT_STATE_ERROR) {
                        Log.e("videoTest", videoPlayerStandard1.currentState + "======================performClick======================");
                        videoPlayerStandard1.startButton.performClick();
                        App.instance.VideoPlaying = videoPlayerStandard1;
                    }
                    return;
                }

            }
        }
        Log.e("videoTest", "======================releaseAllVideos=====================");
        JCVideoPlayer.releaseAllVideos();
        App.instance.VideoPlaying = null;
    }


}

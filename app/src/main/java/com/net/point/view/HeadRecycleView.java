package com.net.point.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.net.point.R;
import com.net.point.adapter.HeaderFooterWrapper;

import rx.functions.Action1;

public class HeadRecycleView extends RecyclerView {

    private boolean needFootView = true;//需要添加footview
    private int footViewCount = 0;//footview数量

    public HeadRecycleView(Context context) {
        this(context, null);
    }

    public HeadRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    private boolean autoLoadMore = true;
    private boolean loadMoreEnable = true;
    @Nullable
    private View footerView;
    @Nullable
    private ProgressBar footerProgressBar;
    @Nullable
    private TextView footerText;
    @Nullable
    private Action1 onLoadListener;

    @NonNull
    private String hasMoreHintText = "点击加载更多";

    @NonNull
    private String noMoreHintText = "没有更多数据了";

    @NonNull
    private String onLoadingHintText = "正在加载";

    @Override
    @SuppressLint("InflateParams")
    public void setAdapter(final Adapter adapter) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.recycler_view_footer, null);
        footerView = view;
        footerProgressBar = view.findViewById(R.id.mFooterProgressBar);
        footerText = view.findViewById(R.id.mFooterText);
        if (footerText != null) {
            footerText.setOnClickListener(v -> loadMore());
        }
        if (!(adapter instanceof HeaderFooterWrapper)) {
            HeaderFooterWrapper headerFooterWrapper = new HeaderFooterWrapper(adapter);
            if (needFootView) {
                headerFooterWrapper.addFootView(view);
                footViewCount++;
            }
        }
        super.setAdapter(adapter);
    }

    /**
     * 隐藏底部
     */
    public void hideFooterView() {
        needFootView = false;
        if (footViewCount > 0) {
            Adapter adapter = getAdapter();
            if (adapter instanceof HeaderFooterWrapper) {
                ((HeaderFooterWrapper) adapter).removeFootView(footerView);
                footViewCount--;
            }
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (autoLoadMore) {
            int visibleItemCount = getChildCount();
            LayoutManager layoutManager = getLayoutManager();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemCount = 0;
            if (layoutManager instanceof LinearLayoutManager) {
                firstVisibleItemCount = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] firstVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findFirstVisibleItemPositions(null);
                for (int itemPosition : firstVisibleItemPositions) {
                    firstVisibleItemCount += itemPosition;
                }
            }
            if ((totalItemCount - visibleItemCount) <= firstVisibleItemCount) {
                loadMore();
            }
        }
    }

    public void setOnLoadListener(@Nullable Action1 onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    public void hasMoreData() {
        loadMoreEnable = true;
        if (footerText != null) {
            footerText.setText(hasMoreHintText);
        }
        if (footerProgressBar != null) {
            footerProgressBar.setVisibility(GONE);
        }
    }

    public void noMoreData() {
        loadMoreEnable = false;
        if (footerText != null) {
            footerText.setText(noMoreHintText);
        }
        if (footerProgressBar != null) {
            footerProgressBar.setVisibility(GONE);
        }
    }

    public void onLoading() {
        loadMoreEnable = false;
        if (footerText != null) {
            footerText.setText(onLoadingHintText);
        }
        if (footerProgressBar != null) {
            footerProgressBar.setVisibility(VISIBLE);
        }
    }

    private void loadMore() {
        if (loadMoreEnable && onLoadListener != null) {
            onLoadListener.call(true);
        }
    }

    public void setAutoLoadMore(boolean autoLoadMore) {
        this.autoLoadMore = autoLoadMore;
    }

    public interface OnLoadMore {
        /**
         * @param autoLoadMore 自动加载
         */
        void setAutoLoadMore(boolean autoLoadMore);

        /**
         * 设置还有更多数据
         */
        void hasMoreData();

        /**
         * 设置没有更多数据了
         */
        void noMoreData();

        /**
         * 显示正在加载
         */
        void onLoading();
    }

    public void setHasMoreHintText(@NonNull String hasMoreHintText) {
        this.hasMoreHintText = hasMoreHintText;
    }

    public void setOnLoadingHintText(@NonNull String onLoadingHintText) {
        this.onLoadingHintText = onLoadingHintText;
    }

    public void setNoMoreHintText(@NonNull String noMoreHintText) {
        this.noMoreHintText = noMoreHintText;
    }
}

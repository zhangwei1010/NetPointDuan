package com.net.point.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 通用的{@link RecyclerView}适配器，该适配器适用于单一数据类型场景
 * <p>
 * Created by cxm
 * on 2017/4/7.
 *
 * @see Data 当前绑定的数据类型
 * @see Holder 当前绑定的UI模型{@link RecyclerView.ViewHolder}
 */

public abstract class CommonItemAdapter<Data, Holder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<Holder> {
    protected ArrayList<Data> items = new ArrayList<>();

    /**
     * 添加单个数据到该适配器，并立即刷新适配器，该数据不能为 {@code null}
     *
     * @param item 该数据不能为 {@code null}
     */
    public void addItem(@NonNull Data item) {
        Objects.requireNonNull(item);
        items.add(item);
        notifyDataSetChanged();
    }

    /**
     * 删除某条数据
     */
    public void removeItem() {
        // TODO: 18-1-15  
    }

    /**
     * 添加一组数据到该适配器，并立即刷新适配器，该组数据不能为{@code null}
     *
     * @param items 该组数据不能为{@code null}
     */
    public void addItems(@NonNull List<Data> items) {
        Objects.requireNonNull(items);
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 重置适配器，并立即刷新适配器。
     *
     * @param items 该组数据不能为{@code null}
     */
    public void setItems(@NonNull List<Data> items) {
        Objects.requireNonNull(items);
        if (this.items != items) {
            this.items.clear();
            addItems(items);
        } else notifyDataSetChanged();
    }

    /**
     * 判断当前列表是否为 {@code null} 或者不包含数据
     *
     * @return true if the {@code items} of contained by this adapter is {@code empty}
     */
    @CheckResult
    final public boolean isEmpty() {
        return getItemCount() == 0;
    }

    /**
     * 判断当前列表是否包含数据
     *
     * @return true if the {@code items} of this adapter is contained more than one data.
     */
    @CheckResult
    final public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * clear this {@code items} and flush immediately.
     */
    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    /**
     * 获得当前已经存在的数据集合，值得注意的是，得到的数据并不是原始数据的拷贝。而是原始数据的直接引用。
     */
    @NonNull
    @CheckResult
    public List<Data> getItems() {
        return items;
    }

    private int position = 0;

    /**
     * 该方法及其返回值仅在 onBindViewHolder 函数内有效
     */
    protected int getPosition() {
        return position;
    }

    protected boolean isFirst() {
        return position == 0;
    }

    protected boolean isLast() {
        return position == items.size() - 1;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        onBindViewHolder(holder, items.get(position));
        this.position = 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    abstract protected void onBindViewHolder(@NonNull Holder holder, @Nullable Data item);
}

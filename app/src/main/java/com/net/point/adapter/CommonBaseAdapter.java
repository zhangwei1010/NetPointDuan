package com.net.point.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cxm
 * on 2017/3/21.
 */

public abstract class CommonBaseAdapter<T, H> extends BaseAdapter {
    @NonNull
    private List<T> data = new LinkedList<>();
    @Nullable
    protected LayoutInflater layoutInflater;
    private int resId;
    private int currentPosition;

    public CommonBaseAdapter(@NonNull LayoutInflater layoutInflater, int resId) {
        this.layoutInflater = layoutInflater;
        this.resId = resId;
    }

    public CommonBaseAdapter(int resId) {
        this.resId = resId;
    }

    public void recycle() {
        data.clear();
        layoutInflater = null;
    }

    @NonNull
    public List<T> getItems() {
        return data;
    }

    public void setItems(@Nullable List<T> data) {
        if (data == null || data.isEmpty()) {
            this.data.clear();
        } else if (data != this.data) {
            this.data.clear();
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addItems(@Nullable List<T> items) {
        if (items != null)
            data.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(@Nullable T item) {
        if (item != null)
            data.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    @Nullable
    public T getItem(int position) {
        List<T> data = this.data;
        if (position < data.size() && data.size() > 0)
            return data.get(position);
        return null;
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public void clear() {
        data.clear();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        currentPosition = position;
        H holder;
        if (convertView == null) {
            LayoutInflater inflater = this.layoutInflater;
            if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(resId, null);
            holder = getHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (H) convertView.getTag();
        }
        inflate(getItem(position), holder);
        return convertView;
    }

    protected int getPosition() {
        return currentPosition;
    }

    @NonNull
    public abstract H getHolder(View view);

    public abstract void inflate(@Nullable T item, @NonNull H holder);
}

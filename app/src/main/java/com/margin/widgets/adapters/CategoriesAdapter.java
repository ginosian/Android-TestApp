package com.cargomatrix.widgets.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cargomatrix.widgets.R;

/**
 * Created by CargoMatrix, Inc. on Feb 09, 2016.
 *
 * @author Denis Shakinov {dshakinov@xbsoftware.com}
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {

    private String[] mCategories;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    public CategoriesAdapter(String[] categories, OnItemClickListener onItemClickListener) {
        mCategories = categories;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row, parent, false);
        return new CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        holder.title.setText(mCategories[position]);
    }

    @Override
    public int getItemCount() {
        return mCategories.length;
    }

    public class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;

        public CategoryHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.category_title);
            itemView.findViewById(R.id.card_view).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClicked(getAdapterPosition());
            }
        }
    }
}


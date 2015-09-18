package com.architjn.epic.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.architjn.epic.R;
import com.architjn.epic.utils.CacheImageLoader;
import com.architjn.epic.utils.items.Image;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ImgsAdapter extends RecyclerView.Adapter<ImgsAdapter.SimpleItemViewHolder> {

    private final List<Image> items;
    private Context context;
    private LinearLayout.LayoutParams layoutParams;
    private CacheImageLoader cacheImageLoader;

    public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView gridImage;
        private View view;

        public SimpleItemViewHolder(View view) {
            super(view);
            this.view = view;
            gridImage = (ImageView) view.findViewById(R.id.item_imageview);
        }
    }

    public ImgsAdapter(Context context, List<Image> items,
                       LinearLayout.LayoutParams layoutParams,
                       CacheImageLoader cil) {
        this.context = context;
        this.items = items;
        this.layoutParams = layoutParams;
        this.cacheImageLoader = cil;
    }

    @Override
    public SimpleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.image_item, parent, false);


        return new SimpleItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SimpleItemViewHolder holder, final int position) {
        String filePath = cacheImageLoader
                .getImage(items.get(position).getPath(), layoutParams.width, layoutParams.height);
        Picasso.with(context).load(Uri.fromFile(new File(filePath))).into(holder.gridImage);
        holder.gridImage.setLayoutParams(layoutParams);
        holder.gridImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.gridImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}


package de.ur.mi.fashionapp.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.ImageViewholder;

/**
 * Created by Philip on 28/03/2016.
 */
public class OutfitDetailAdapter extends CBAdapterRecyclerView<Bitmap> {

  private ImageViewholder.ImageViewholderClickListener listener;

  public OutfitDetailAdapter(Context context, ImageViewholder.ImageViewholderClickListener listener) {
    super(context);
    this.listener = listener;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
    ((ImageViewholder) viewHolder).bind(getItem(position), listener, position);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ImageViewholder(inflater.inflate(R.layout.image_item, parent, false));
  }
}

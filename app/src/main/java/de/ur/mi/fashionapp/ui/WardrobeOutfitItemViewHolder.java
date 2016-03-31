package de.ur.mi.fashionapp.ui;

import android.graphics.Bitmap;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.model.WardrobeOutfitItem;

/**
 * Created by Philip on 24/02/2016.
 *
 * This viewholder binds a WardrobeOutfitItem for the WardrobeAdapter
 */
public class WardrobeOutfitItemViewHolder extends RecyclerView.ViewHolder {

  private PercentRelativeLayout outfitItem;
  private ImageView main;
  private ImageView left;
  private ImageView mid;
  private ImageView right;

  public WardrobeOutfitItemViewHolder(View itemView) {
    super(itemView);
    outfitItem = (PercentRelativeLayout) itemView.findViewById(R.id.outfitItem);
    main = (ImageView) itemView.findViewById(R.id.mainPiece);
    left = (ImageView) itemView.findViewById(R.id.left);
    mid = (ImageView) itemView.findViewById(R.id.mid);
    right = (ImageView) itemView.findViewById(R.id.right);
  }

  public void bind(final WardrobeOutfitItem item,
      final WardrobeItemViewHolder.WardrobeItemViewHolderListener listener) {
    outfitItem.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onWardrobeItemClicked(item);
      }
    });
    Bitmap[] images = item.getImages();
    main.setImageBitmap(images[0]);
    left.setImageBitmap(images[1]);
    mid.setImageBitmap(images[2]);
    right.setImageBitmap(images[3]);
  }
}

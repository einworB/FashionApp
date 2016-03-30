package de.ur.mi.fashionapp.ui;

import android.graphics.Bitmap;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;

/**
 * Created by Philip on 24/02/2016.
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
    //TODO: replace with for loop etc
    Bitmap[] images = item.getImages();
    Bitmap image1 = images[0];
    Bitmap image2 = images[1];
    Bitmap image3 = images[2];
    Bitmap image4 = images[3];
    Log.d("OUTFIT",
        item.getTitle() + ": " + image1 + ", " + image2 + ", " + image3 + ", " + image4);
    main.setImageBitmap(image1);
    left.setImageBitmap(image2);
    mid.setImageBitmap(image3);
    right.setImageBitmap(image4);
  }
}

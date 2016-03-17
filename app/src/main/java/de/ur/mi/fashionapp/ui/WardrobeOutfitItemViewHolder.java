package de.ur.mi.fashionapp.ui;

import android.graphics.Bitmap;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
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
    // TODO: show outfit title in outfit items?? -> no
    main = (ImageView)itemView.findViewById(R.id.mainPiece);
    left = (ImageView)itemView.findViewById(R.id.left);
    mid = (ImageView)itemView.findViewById(R.id.mid);
    right = (ImageView)itemView.findViewById(R.id.right);
  }

  public void bind(final WardrobeOutfitItem item,
      final WardrobeItemViewHolder.WardrobeItemViewHolderListener listener) {
    outfitItem.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onWardrobeItemClicked(item);
      }
    });
    //TODO: get outfit / piece image(s)
    Bitmap image1 = item.getImage1();
    Bitmap image2 = item.getImage2();
    Bitmap image3 = item.getImage3();
    Bitmap image4 = item.getImage4();
    if(image1 !=null) {
      main.setImageBitmap(image1);
    }
    if(image2 !=null) {
      left.setImageBitmap(image2);
    }
    if(image3 !=null) {
      mid.setImageBitmap(image3);
    }
    if(image4 !=null) {
      right.setImageBitmap(image4);
    }
  }
}

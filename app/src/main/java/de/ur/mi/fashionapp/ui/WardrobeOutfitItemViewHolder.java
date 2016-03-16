package de.ur.mi.fashionapp.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;

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

  public void bind(final WardrobeItem item,
      final WardrobeItemViewHolder.WardrobeItemViewHolderListener listener) {
    outfitItem.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onWardrobeItemClicked(item);
      }
    });
    //TODO: get outfit / piece image(s)
    /*
    if(item.!=null) {
      Bitmap bmp = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
      pic.setImageBitmap(bmp);
    }
    */
  }
}

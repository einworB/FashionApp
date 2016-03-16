package de.ur.mi.fashionapp.ui;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Debug;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
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
    if(item.getImage1()==null)Log.d("oh","nooooooooooooooooooooooooooooooooooooooooooooooooo");
    if(item.getImage1()!=null) {
      Bitmap bmp = BitmapFactory.decodeByteArray(item.getImage1(), 0, item.getImage1().length);
      main.setImageBitmap(bmp);
    }
    if(item.getImage2()!=null) {
      Bitmap bmp = BitmapFactory.decodeByteArray(item.getImage2(), 0, item.getImage2().length);
      left.setImageBitmap(bmp);
    }
    if(item.getImage3()!=null) {
      Bitmap bmp = BitmapFactory.decodeByteArray(item.getImage3(), 0, item.getImage3().length);
      mid.setImageBitmap(bmp);
    }
    if(item.getImage4()!=null) {
      Bitmap bmp = BitmapFactory.decodeByteArray(item.getImage4(), 0, item.getImage4().length);
      right.setImageBitmap(bmp);
    }
  }
}

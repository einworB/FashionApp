package de.ur.mi.fashionapp.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;

/**
 * Created by Philip on 24/02/2016.
 */
public class WardrobePieceItemViewHolder extends RecyclerView.ViewHolder {

  private PercentRelativeLayout pieceItem;
  private TextView label;
  private ImageView pic;

  public WardrobePieceItemViewHolder(View itemView) {
    super(itemView);
    pieceItem = (PercentRelativeLayout) itemView.findViewById(R.id.pieceItem);
    label = (TextView) itemView.findViewById(R.id.label);
    pic = (ImageView) itemView.findViewById(R.id.image);
  }

  public void bind(final WardrobeItem item,
      final WardrobeItemViewHolder.WardrobeItemViewHolderListener listener) {
    pieceItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onWardrobeItemClicked(item);
      }
    });
    label.setText(item.getTitle());

    if(item.getImage()!=null) {
      Bitmap bmp = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
      pic.setImageBitmap(bmp);
    }
  }
}

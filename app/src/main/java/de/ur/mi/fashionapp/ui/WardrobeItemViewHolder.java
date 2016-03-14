package de.ur.mi.fashionapp.ui;

import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;

/**
 * Created by Philip on 24/02/2016.
 */
public class WardrobeItemViewHolder extends RecyclerView.ViewHolder {

  private PercentRelativeLayout outfitItem, pieceItem;

  public interface WardrobeItemViewHolderListener {
    void onWardrobeItemClicked(String itemID);
  }

  public WardrobeItemViewHolder(View itemView) {
    super(itemView);
    outfitItem = (PercentRelativeLayout) itemView.findViewById(R.id.outfitItem);
    pieceItem = (PercentRelativeLayout) itemView.findViewById(R.id.pieceItem);
  }

  public void bind(final WardrobeItem item, final WardrobeItemViewHolderListener listener) {
    //itemLayout.setText(item.getTitle());
    //TODO: get outfit / piece image(s)
    outfitItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onWardrobeItemClicked(item.getID());
      }
    });
    /*pieceItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onWardrobeItemClicked(item.getID());
      }
    });*/
  }
}

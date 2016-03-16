package de.ur.mi.fashionapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;

/**
 * Created by Philip on 24/02/2016.
 */
public abstract class WardrobeItemViewHolder extends RecyclerView.ViewHolder {

  public interface WardrobeItemViewHolderListener {
    void onWardrobeItemClicked(WardrobeItem item);
  }

  public WardrobeItemViewHolder(View itemView) {
    super(itemView);
  }

  public abstract void bind(final WardrobeItem item, final WardrobeItemViewHolderListener listener);
}

package de.ur.mi.fashionapp.wardrobe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.WardrobeItemViewHolder;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

/**
 * Created by Philip on 29/02/2016.
 */
public class WardrobeAdapter extends CBAdapterRecyclerView<WardrobeItem>
    implements WardrobeItemViewHolder.WardrobeItemViewHolderListener {

  private static final int VIEWTYPE_PIECE = 0;
  private static final int VIEWTYPE_OUTFIT = 1;

  private WardrobeAdapterListener listener;

  interface WardrobeAdapterListener{
    void onWardrobeItemClicked(int itemID);
  }

  public WardrobeAdapter(Context context, WardrobeAdapterListener listener) {
    super(context);
    this.listener = listener;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
    switch (viewType) {
      case VIEWTYPE_PIECE:
      case VIEWTYPE_OUTFIT:
        ((WardrobeItemViewHolder) viewHolder).bind(getItem(position), this);
        break;
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case VIEWTYPE_PIECE:
      case VIEWTYPE_OUTFIT:
        return new WardrobeItemViewHolder(inflater.inflate(R.layout.simple_text, parent, false));
      default:
        return null;
    }
  }

  @Override public int getItemViewType(int position) {
    if (getItem(position) instanceof WardrobeOutfitItem) {
      return VIEWTYPE_OUTFIT;
    }
    else if (getItem(position) instanceof WardrobePieceItem) {
      return VIEWTYPE_PIECE;
    }
    else {
      return super.getItemViewType(position);
    }
  }

  @Override public void onWardrobeItemClicked(int itemID) {
    listener.onWardrobeItemClicked(itemID);
  }
}

package de.ur.mi.fashionapp.edit.outfit.pickpieces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.PickOutfitPiecesViewHolder;
import de.ur.mi.fashionapp.model.WardrobeItem;
import de.ur.mi.fashionapp.model.WardrobePieceItem;
import java.util.List;

/**
 * Created by Philip on 23/03/2016.
 *
 * This adapter displays the pieces of the current wardrobe in the contentView(RecyclerView) of the
 * PickOutfitPiecesActivity.
 * It also forwards (click) events on the items to the activity.
 */
public class PickOutfitPiecesAdapter extends CBAdapterRecyclerView<WardrobePieceItem>
    implements PickOutfitPiecesViewHolder.PickOutfitPiecesViewHolderListener {

  PickOutfitPiecesAdapterListener listener;

  public List<WardrobePieceItem> getItems() {
    return items;
  }

  interface PickOutfitPiecesAdapterListener {
    void onOutfitPieceItemsSelected(WardrobePieceItem item);

    void onMaxPiecesSeleced();
  }

  public PickOutfitPiecesAdapter(Context context, PickOutfitPiecesAdapterListener listener) {
    super(context);
    this.listener = listener;
  }

  @Override public void onPickOutfitPiecesItemClicked(WardrobePieceItem item) {
    if (item != null && listener != null) {
      if (item.isSelected()) {
        item.setSelection(false);
      } else {
        item.setSelection(true);
      }
      listener.onOutfitPieceItemsSelected(item);
    }
  }

  @Override public void onMaxPiecesReached() {
    listener.onMaxPiecesSeleced();
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
    ((PickOutfitPiecesViewHolder) viewHolder).bind(getItem(position), this);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new PickOutfitPiecesViewHolder(inflater.inflate(R.layout.piece_item, parent, false));
  }

  @Override public void setItems(List<WardrobePieceItem> items) {
    this.items = items;
  }

  public int getItemPosition(String itemID) {
    if (itemID != null) {
      for (int i = 0; i < items.size(); i++) {
        WardrobeItem item = items.get(i);
        if (itemID.equals(item.getID())) {
          return i;
        }
      }
    }
    return -1;
  }

  public void setItemSelected(String itemID) {
    for (int i = 0; i < items.size(); i++) {
      WardrobeItem item = items.get(i);
      if (itemID.equals(item.getID())) {
        items.get(i).setSelection(true);
      }
    }
  }
}

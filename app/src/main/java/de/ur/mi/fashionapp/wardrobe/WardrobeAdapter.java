package de.ur.mi.fashionapp.wardrobe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.WardrobeItemViewHolder;
import de.ur.mi.fashionapp.ui.WardrobeOutfitItemViewHolder;
import de.ur.mi.fashionapp.ui.WardrobePieceItemViewHolder;
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
    void onWardrobeItemClicked(WardrobeItem item);
  }

  public WardrobeAdapter(Context context, WardrobeAdapterListener listener) {
    super(context);
    this.listener = listener;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
    switch (viewType) {
      case VIEWTYPE_PIECE:
        ((WardrobePieceItemViewHolder) viewHolder).bind(getItem(position), this);
        break;
      case VIEWTYPE_OUTFIT:
        ((WardrobeOutfitItemViewHolder) viewHolder).bind((WardrobeOutfitItem)getItem(position), this);
        break;
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case VIEWTYPE_PIECE:
        return new WardrobePieceItemViewHolder(inflater.inflate(R.layout.piece_item, parent, false));
      case VIEWTYPE_OUTFIT:
        return new WardrobeOutfitItemViewHolder(inflater.inflate(R.layout.outfit_item, parent, false));
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

  @Override public void onWardrobeItemClicked(WardrobeItem item) {
    listener.onWardrobeItemClicked(item);
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

  public void searchForItemsWith (String query){
    if(query==null){
      //show all items in wardrobe
    }else{
      //show only the items with "query" in the title
    }
  }

  public void filterItemsBy (int[] filters){
    if(filters[0]!=0){
      //show all items with the category (filters[0]-1)
    }
    if(filters[1]!=0){
      //show all items with the season (filters[1]-1)
    }
    if(filters[2]!=0){
      //show all items with the occasion (filters[2]-1)
    }
    if(filters[3]!=0){
      //show all items with the color (filters[3]-1)
    }
  }

}

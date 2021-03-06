package de.ur.mi.fashionapp.wardrobe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.model.WardrobeItem;
import de.ur.mi.fashionapp.model.WardrobeOutfitItem;
import de.ur.mi.fashionapp.model.WardrobePieceItem;
import de.ur.mi.fashionapp.ui.WardrobeItemViewHolder;
import de.ur.mi.fashionapp.ui.WardrobeOutfitItemViewHolder;
import de.ur.mi.fashionapp.ui.WardrobePieceItemViewHolder;
import de.ur.mi.fashionapp.util.CatWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 29/02/2016.
 *
 * this adapter is responsible for displyaing the pieces and outfits of the current wardrobe.
 * it also forwards clicks on those items.
 */
public class WardrobeAdapter extends CBAdapterRecyclerView<WardrobeItem>
    implements WardrobeItemViewHolder.WardrobeItemViewHolderListener {

  private static final int VIEWTYPE_PIECE = 0;
  private static final int VIEWTYPE_OUTFIT = 1;
  private static final int FILTER_CATEGORY = 0;
  private static final int FILTER_SEASON = 1;
  private static final int FILTER_OCCASION = 2;
  private static final int FILTER_COLOR = 3;

  private List<WardrobeItem> model = new ArrayList<>();
  private List<WardrobeItem> tempItems = new ArrayList<>();

  private WardrobeAdapterListener listener;

  interface WardrobeAdapterListener {
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
        ((WardrobeOutfitItemViewHolder) viewHolder).bind((WardrobeOutfitItem) getItem(position),
            this);
        break;
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case VIEWTYPE_PIECE:
        return new WardrobePieceItemViewHolder(
            inflater.inflate(R.layout.piece_item, parent, false));
      case VIEWTYPE_OUTFIT:
        return new WardrobeOutfitItemViewHolder(
            inflater.inflate(R.layout.outfit_item, parent, false));
      default:
        return null;
    }
  }

  @Override public int getItemViewType(int position) {
    if (getItem(position) instanceof WardrobeOutfitItem) {
      return VIEWTYPE_OUTFIT;
    } else if (getItem(position) instanceof WardrobePieceItem) {
      return VIEWTYPE_PIECE;
    } else {
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

  public void searchForItemsWith(CharSequence query) {
    if (query == null || query.equals("")) {
      // show all items
      items = new ArrayList<>(model);
    } else {
      if (tempItems == null || tempItems.size() < items.size()) {
        tempItems = new ArrayList<>(items);
      }
      if (items.size() < model.size()) {
        // reset
        items = new ArrayList<>(model);
      }
      items.clear();
      //show only the items with "query" in the title
      for (WardrobeItem item : tempItems) {
        String title = item.getTitle();
        if (title != null) {
          title = title.toLowerCase();
        }
        query = ("" + query).toLowerCase();
        if (title != null && title.contains(query)) {
          items.add(item);
        }
      }
    }
    notifyDataSetChanged();
  }

  public void filterItemsBy(int[] filters) {
    if (tempItems == null || tempItems.size() < items.size()) {
      tempItems = new ArrayList<>(items);
    }
    if (items.size() < model.size()) {
      // reset
      items = new ArrayList<>(model);
    }

    if (filters[FILTER_CATEGORY] != 0) {
      //show all items with the category (filters[0]-1)
      filter(FILTER_CATEGORY, filters[FILTER_CATEGORY], items);
    }
    if (filters[FILTER_SEASON] != 0) {
      //show all items with the season (filters[1]-1)
      filter(FILTER_SEASON, filters[FILTER_SEASON], items);
    }
    if (filters[FILTER_OCCASION] != 0) {
      //show all items with the occasion (filters[2]-1)
      filter(FILTER_OCCASION, filters[FILTER_OCCASION], items);
    }
    if (filters[FILTER_COLOR] != 0) {
      //show all items with the color (filters[3]-1)
      filter(FILTER_COLOR, filters[FILTER_COLOR], items);
    }
    notifyDataSetChanged();
  }

  private void filter(int type, int filter, List<WardrobeItem> currentItems) {
    filter--;
    if (type == FILTER_COLOR) {
      filter = new CatWrapper().colorWrap(filter);
    }
    List<WardrobeItem> currentTempItems = new ArrayList<>(currentItems);
    currentItems.clear();
    for (WardrobeItem item : currentTempItems) {
      if (!(item instanceof WardrobePieceItem)) {
        return;
      }
      WardrobePieceItem pieceItem = (WardrobePieceItem) item;
      int check = -1;
      switch (type) {
        case FILTER_CATEGORY:
          check = pieceItem.getCat();
          break;
        case FILTER_SEASON:
          check = pieceItem.getSeason();
          break;
        case FILTER_OCCASION:
          check = pieceItem.getOccasion();
          break;
        case FILTER_COLOR:
          check = pieceItem.getColor();
          break;
      }
      if (check != -1 && filter == check) {
        currentItems.add(item);
      }
    }
  }

  @Override public void setItems(List<WardrobeItem> items) {
    this.items = items;
    this.model = new ArrayList<>(items);
  }
}

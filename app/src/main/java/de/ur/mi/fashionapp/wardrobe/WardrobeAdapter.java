package de.ur.mi.fashionapp.wardrobe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.TextViewHolder;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

/**
 * Created by Philip on 29/02/2016.
 */
public class WardrobeAdapter extends CBAdapterRecyclerView<WardrobeItem> {

  private static final int VIEWTYPE_PIECE = 0;
  private static final int VIEWTYPE_OUTFIT = 1;

  public WardrobeAdapter(Context context) {
    super(context);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
    switch (viewType) {
      case VIEWTYPE_PIECE:
      case VIEWTYPE_OUTFIT:
        ((TextViewHolder) viewHolder).bind(getItem(position).getTitle());
        break;
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case VIEWTYPE_PIECE:
      case VIEWTYPE_OUTFIT:
        return new TextViewHolder(inflater.inflate(R.layout.simple_text, parent, false));
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
}

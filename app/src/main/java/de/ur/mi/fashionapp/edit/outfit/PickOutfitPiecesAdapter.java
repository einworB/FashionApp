package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.PickOutfitPiecesViewHolder;
import de.ur.mi.fashionapp.ui.WardrobePieceItemViewHolder;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

/**
 * Created by Philip on 23/03/2016.
 */
public class PickOutfitPiecesAdapter extends CBAdapterRecyclerView<WardrobePieceItem>{

    //TODO: items to activity! viewholderlistener! (see wardrobe adapter +  piece viewholder)
  interface PickOutfitPiecesAdapterListener {
    void onOutfitPieceItemsSelected(WardrobePieceItem item);
  }

  public PickOutfitPiecesAdapter(Context context) {
    super(context);
  }


  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
    // TODO: bind data from the item to the viewholder here via Viewholder.bind; dont forget the click listener

  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    // TODO: inflate the viewholder here
      return new PickOutfitPiecesViewHolder(
              inflater.inflate(R.layout.piece_item, parent, false));
  }
}

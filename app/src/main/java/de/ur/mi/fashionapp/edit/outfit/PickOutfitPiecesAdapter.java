package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;

/**
 * Created by Philip on 23/03/2016.
 */
public class PickOutfitPiecesAdapter extends CBAdapterRecyclerView<PickOutfitPieceItem>{

  interface PickOutfitPiecesAdapterListener {
    void onOutfitPieceItemsSelected(PickOutfitPieceItem item);
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
    return null;
  }
}

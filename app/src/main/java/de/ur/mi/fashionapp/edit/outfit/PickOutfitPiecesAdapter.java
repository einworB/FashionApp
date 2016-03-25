package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.PickOutfitPiecesViewHolder;
import de.ur.mi.fashionapp.ui.WardrobePieceItemViewHolder;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

/**
 * Created by Philip on 23/03/2016.
 */
public class PickOutfitPiecesAdapter extends CBAdapterRecyclerView<WardrobePieceItem> implements PickOutfitPiecesViewHolder.PickOutfitPiecesViewHolderListener {

    PickOutfitPiecesAdapterListener listener;
    private List<WardrobePieceItem> model = new ArrayList<>();

    //TODO: items to activity! viewholderlistener! (see wardrobe adapter +  piece viewholder)
    interface PickOutfitPiecesAdapterListener {
        void onOutfitPieceItemsSelected(WardrobePieceItem item);
    }

    public PickOutfitPiecesAdapter(Context context, PickOutfitPiecesAdapterListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    public void onPickOutfitPiecesItemClicked(WardrobePieceItem item) {
        if(item != null && listener != null){
            Log.d("POPA", "onPickOutfitPiecesItemClicked");
            listener.onOutfitPieceItemsSelected(item);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
        // TODO: bind data from the item to the viewholder here via Viewholder.bind; dont forget the click listener
        Log.d("POPA", "onBindViewHolder");
        ((PickOutfitPiecesViewHolder) viewHolder).bind(getItem(position), this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PickOutfitPiecesViewHolder(
                inflater.inflate(R.layout.piece_item, parent, false));
    }

    @Override public void setItems(List<WardrobePieceItem> items) {
        this.items = items;
        this.model = new ArrayList<>(items);
    }
}

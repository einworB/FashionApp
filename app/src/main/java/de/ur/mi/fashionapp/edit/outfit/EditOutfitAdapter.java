package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.EditOutfitViewHolder;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

/**
 * Created by Philip on 29/02/2016.
 */
public class EditOutfitAdapter extends CBAdapterRecyclerView<WardrobePieceItem>
        implements EditOutfitViewHolder.EditOutfitViewHolderListener {

    private List<WardrobePieceItem> model = new ArrayList<>();

    private EditOutfitAdapterListener listener;

    @Override
    public void onEditOutfitItemClicked(WardrobePieceItem item) {

    }

    interface EditOutfitAdapterListener {
        void onEditOutfitItemClicked(EditOutfitItem item);
    }

    public EditOutfitAdapter(Context context, EditOutfitAdapterListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
        ((EditOutfitViewHolder) viewHolder).bind(getItem(position), this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EditOutfitViewHolder(inflater.inflate(R.layout.piece_item, parent, false));
    }

    @Override
    public void setItems(List<WardrobePieceItem> items) {
        this.items = items;
        this.model = new ArrayList<>(items);
    }
}

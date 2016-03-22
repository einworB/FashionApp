package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.EditOutfitViewHolder;

/**
 * Created by Philip on 29/02/2016.
 */
public class EditOutfitAdapter extends CBAdapterRecyclerView<EditOutfitItem>
    implements EditOutfitViewHolder.EditOutfitViewHolderListener {


  private EditOutfitAdapterListener listener;

    @Override
    public void onEditOutfitItemClicked(String ID, String title) {

    }

    interface EditOutfitAdapterListener{
    void onEditOutfitItemClicked(EditOutfitItem item);
  }

  public EditOutfitAdapter(Context context, EditOutfitAdapterListener listener) {
    super(context);
    this.listener = listener;
  }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
        ((EditOutfitViewHolder)viewHolder).bind(getItem(position), this);
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      return new EditOutfitViewHolder(inflater.inflate(R.layout.outfit_piece, parent, false));
  }

  public int getItemPosition(String itemID) {
    if (itemID != null) {
      for (int i = 0; i < items.size(); i++) {
        EditOutfitItem item = items.get(i);
        if (itemID.equals(item.getID())) {
          return i;
        }
      }
    }
    return -1;
  }

}

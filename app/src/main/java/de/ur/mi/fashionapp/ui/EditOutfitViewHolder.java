package de.ur.mi.fashionapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.edit.outfit.EditOutfitItem;

/**
 * Created by Philip on 24/02/2016.
 */
public class EditOutfitViewHolder extends RecyclerView.ViewHolder {

  private TextView text;

  public interface EditOutfitViewHolderListener {
    void onEditOutfitItemClicked(String ID, String title);
  }

  public EditOutfitViewHolder(View itemView) {
    super(itemView);
    text = (TextView) itemView.findViewById(R.id.text);
  }

  public void bind(final EditOutfitItem item, final EditOutfitViewHolderListener listener) {
    text.setText(item.getTitle());
    text.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onEditOutfitItemClicked(item.getID(), item.getTitle());
      }
    });
  }
}

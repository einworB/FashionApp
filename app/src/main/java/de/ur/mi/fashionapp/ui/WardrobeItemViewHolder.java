package de.ur.mi.fashionapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;

/**
 * Created by Philip on 24/02/2016.
 */
public class WardrobeItemViewHolder extends RecyclerView.ViewHolder {

  private TextView text;

  public interface WardrobeItemViewHolderListener {
    void onWardrobeItemClicked(String itemID);
  }

  public WardrobeItemViewHolder(View itemView) {
    super(itemView);
    text = (TextView) itemView.findViewById(R.id.text);
  }

  public void bind(final WardrobeItem item, final WardrobeItemViewHolderListener listener) {
    text.setText(item.getTitle());
    text.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onWardrobeItemClicked(item.getID());
      }
    });
  }
}

package de.ur.mi.fashionapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeMenuWardrobeItem;

/**
 * Created by Philip on 24/02/2016.
 */
public class WardrobeViewHolder extends RecyclerView.ViewHolder {

  private TextView text;

  public interface WardrobeViewHolderListener {
    void onWardrobeClicked(int ID);
  }

  public WardrobeViewHolder(View itemView) {
    super(itemView);
    text = (TextView) itemView.findViewById(R.id.text);
  }

  public void bind(final WardrobeMenuWardrobeItem item, final WardrobeViewHolderListener listener) {
    text.setText(item.getTitle());
    text.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onWardrobeClicked(item.getID());
      }
    });
  }
}

package de.ur.mi.fashionapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuNewWardrobeItem;

/**
 * Created by Philip on 24/02/2016.
 *
 * This viewholder binds a single WardrobeMenuNewWardrobeItem for the WardrobeMenuAdapter and
 * forwards the click on the item
 */
public class NewWardrobeViewHolder extends RecyclerView.ViewHolder {

  private TextView text;

  public interface NewWardrobeViewHolderListener {
    void onNewWardrobeClicked();
  }

  public NewWardrobeViewHolder(View itemView) {
    super(itemView);
    text = (TextView) itemView.findViewById(R.id.text);
  }

  public void bind(final WardrobeMenuNewWardrobeItem item,
      final NewWardrobeViewHolderListener listener) {
    text.setText(item.getTitle());
    text.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onNewWardrobeClicked();
      }
    });
    text.setTextSize(14);
  }
}

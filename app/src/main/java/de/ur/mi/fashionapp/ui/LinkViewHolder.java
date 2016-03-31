package de.ur.mi.fashionapp.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuLinkItem;

/**
 * Created by Philip on 24/02/2016.
 *
 * This viewholder binds a single WardrobeMenuLinkItem for the WardrobeMenuAdapter and forwards the
 * click on the item
 */
public class LinkViewHolder extends RecyclerView.ViewHolder {

  private TextView text;

  public interface LinkViewHolderListener {
    void onLinkClicked(String title);
  }

  public LinkViewHolder(View itemView) {
    super(itemView);
    text = (TextView) itemView.findViewById(R.id.text);
  }

  public void bind(final WardrobeMenuLinkItem item, final LinkViewHolderListener listener) {
    text.setText(item.getTitle());
    text.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onLinkClicked(item.getTitle());
      }
    });
    text.setTextSize(14);
  }
}

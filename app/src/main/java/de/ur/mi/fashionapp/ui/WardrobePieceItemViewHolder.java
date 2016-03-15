package de.ur.mi.fashionapp.ui;

import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;

/**
 * Created by Philip on 24/02/2016.
 */
public class WardrobePieceItemViewHolder extends RecyclerView.ViewHolder {

  private PercentRelativeLayout pieceItem;
  private TextView label;

  public WardrobePieceItemViewHolder(View itemView) {
    super(itemView);
    pieceItem = (PercentRelativeLayout) itemView.findViewById(R.id.pieceItem);
    label = (TextView) itemView.findViewById(R.id.label);
  }

  public void bind(final WardrobeItem item,
      final WardrobeItemViewHolder.WardrobeItemViewHolderListener listener) {
    pieceItem.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onWardrobeItemClicked(item);
      }
    });
    label.setText(item.getTitle());
    //TODO: get outfit / piece image(s)
  }
}

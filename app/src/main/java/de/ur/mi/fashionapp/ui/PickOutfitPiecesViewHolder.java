package de.ur.mi.fashionapp.ui;

import android.graphics.Bitmap;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.model.WardrobePieceItem;

/**
 * Created by Jana on 24.03.2016.
 *
 * This viewholder binds a single WardrobePieceItem for the PickOutfitPiecesAdapter.
 * If the item is clicked it also marks the item as checked/unchecked and and forwards the click.
 */
public class PickOutfitPiecesViewHolder extends RecyclerView.ViewHolder {

  private PercentRelativeLayout pieceItem;
  private TextView label;
  private ImageView pic, selected;

  public PickOutfitPiecesViewHolder(View v) {
    super(v);
    pieceItem = (PercentRelativeLayout) itemView.findViewById(R.id.pieceItem);
    label = (TextView) itemView.findViewById(R.id.label);
    pic = (ImageView) itemView.findViewById(R.id.image);
    selected = (ImageView) itemView.findViewById(R.id.selection);
  }

  public interface PickOutfitPiecesViewHolderListener {
    void onPickOutfitPiecesItemClicked(WardrobePieceItem item);

    void onMaxPiecesReached();
  }

  public void bind(final WardrobePieceItem item, final PickOutfitPiecesViewHolderListener listener,
      final boolean isSelected) {
    pieceItem.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (selected.getVisibility() == View.GONE) {
          if (!item.isMaxReached()) {
            selected.setVisibility(View.VISIBLE);
          } else {
            listener.onMaxPiecesReached();
          }
        } else {
          selected.setVisibility(View.GONE);
        }
        listener.onPickOutfitPiecesItemClicked(item);
      }
    });
    if (isSelected) {
      selected.setVisibility(View.VISIBLE);
    }
    label.setText(item.getTitle());
    label.setVisibility(View.GONE);
    Bitmap image = item.getImage();
    if (image != null) {
      pic.setImageBitmap(image);
    }
  }
}

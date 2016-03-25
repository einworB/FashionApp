package de.ur.mi.fashionapp.ui;

import android.graphics.Bitmap;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.edit.outfit.EditOutfitItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

/**
 * Created by Philip on 24/02/2016.
 */
public class EditOutfitViewHolder extends RecyclerView.ViewHolder {

    private PercentRelativeLayout pieceItem;
    private ImageView pic;
    private TextView label;

    public interface EditOutfitViewHolderListener {
        void onEditOutfitItemClicked(WardrobePieceItem item);
    }

    public EditOutfitViewHolder(View itemView) {
        super(itemView);
        pieceItem = (PercentRelativeLayout) itemView.findViewById(R.id.pieceItem);
        pic = (ImageView) itemView.findViewById(R.id.image);
        label = (TextView) itemView.findViewById(R.id.label);
    }

    public void bind(final WardrobePieceItem item, final EditOutfitViewHolderListener listener) {
        pieceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditOutfitItemClicked(item);
            }
        });
        label.setText(item.getTitle());
        label.setVisibility(View.GONE);
        Bitmap image = item.getImage();
        if (image != null) {
            pic.setImageBitmap(image);
        }
    }
}

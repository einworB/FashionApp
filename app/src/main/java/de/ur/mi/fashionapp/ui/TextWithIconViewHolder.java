package de.ur.mi.fashionapp.ui;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import de.ur.mi.fashionapp.R;

/**
 * Created by Philip on 24/02/2016.
 *
 * This simple viewholder binds a String and an image to a TextView and an ImageView
 */
public class TextWithIconViewHolder extends RecyclerView.ViewHolder {

  public interface TextWithIconViewHolderListener {
    void onIconClicked(String ID);
  }

  private TextView text;
  private ImageView icon;

  public TextWithIconViewHolder(View itemView) {
    super(itemView);
    text = (TextView) itemView.findViewById(R.id.text);
    icon = (ImageView) itemView.findViewById(R.id.icon);
  }

  public void bind(String str, final String ID, Drawable iconDrawable,
      final TextWithIconViewHolderListener listener) {
    text.setText(str);
    icon.setImageDrawable(iconDrawable);
    icon.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onIconClicked(ID);
      }
    });
  }
}

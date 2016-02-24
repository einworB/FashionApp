package de.ur.mi.fashionapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Philip on 24/02/2016.
 */
public class TextViewHolder extends RecyclerView.ViewHolder {

  private TextView text;

  public TextViewHolder(View itemView) {
    super(itemView);
    text = (TextView) itemView.findViewById(R.id.text);
  }

  public void bind(String str) {
    text.setText(str);
  }
}

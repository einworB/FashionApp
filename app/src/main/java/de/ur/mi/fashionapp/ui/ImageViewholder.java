package de.ur.mi.fashionapp.ui;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import de.ur.mi.fashionapp.R;

/**
 * Created by Philip on 28/03/2016.
 */
public class ImageViewholder extends RecyclerView.ViewHolder {

  public interface ImageViewholderClickListener {
    void onImageClicked(int position);
  }

  private ImageView imageView;

  public ImageViewholder(View itemView) {
    super(itemView);
    imageView = (ImageView) itemView.findViewById(R.id.image);
  }

  public void bind(final Bitmap bitmap, final ImageViewholderClickListener listener, final int position) {
    imageView.setImageBitmap(bitmap);
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (listener != null) {
          listener.onImageClicked(position);
        }
      }
    });
  }
}

package de.ur.mi.fashionapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.ur.mi.fashionapp.model.WardrobePieceItem;

/**
 * Created by Philip on 18/03/2016.
 *
 * this helper class is used to reduce the bitmap size for performance reasons.
 */
public class ImageHelper {
  public static Bitmap getScaledBitmap(byte[] data) {
    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeByteArray(data, 0, data.length, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, WardrobePieceItem.MAX_IMAGE_WIDTH,
        WardrobePieceItem.MAX_IMAGE_HEIGHT);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeByteArray(data, 0, data.length, options);
  }

  private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
      int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {
      final int halfHeight = height / 2;
      final int halfWidth = width / 2;

      // Calculate the largest inSampleSize value that is a power of 2 and keeps both
      // height and width larger than the requested height and width.
      while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
        inSampleSize *= 2;
      }
    }
    return inSampleSize;
  }
}

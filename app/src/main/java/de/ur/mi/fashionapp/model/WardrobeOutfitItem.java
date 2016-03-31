package de.ur.mi.fashionapp.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import com.hannesdorfmann.parcelableplease.annotation.ParcelableNoThanks;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Philip on 29/02/2016.
 *
 * This class contains all fields, and getter/setters for them specifically for outfits.
 * To be able to pass it from activity to activity via bundle it is made parcelable.
 * By this the whole items do not have to be loaded in every activity.
 * images however are not included in the parcelable and always have to be loaded because of their
 * filesize.
 */
@ParcelablePlease public class WardrobeOutfitItem extends WardrobeItem {
  String[] pieceIDs = new String[] { null, null, null, null, null, null, null, null, null, null };
  @ParcelableNoThanks Bitmap[] images =
      new Bitmap[] { null, null, null, null, null, null, null, null, null, null };

  public static final Creator<WardrobeOutfitItem> CREATOR = new Creator<WardrobeOutfitItem>() {
    @Override public WardrobeOutfitItem createFromParcel(Parcel source) {
      WardrobeOutfitItem target = new WardrobeOutfitItem();
      WardrobeOutfitItemParcelablePlease.readFromParcel(target, source);
      return target;
    }

    @Override public WardrobeOutfitItem[] newArray(int size) {
      return new WardrobeOutfitItem[size];
    }
  };

  public String[] getPieceIDs() {
    return pieceIDs;
  }

  public void setPieceIDs(String[] pieceIDs) {
    this.pieceIDs = pieceIDs;
  }

  public Bitmap[] getImages() {
    return images;
  }

  public void setImages(Bitmap[] images) {
    this.images = images;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    WardrobeOutfitItemParcelablePlease.writeToParcel(this, dest, flags);
  }
}

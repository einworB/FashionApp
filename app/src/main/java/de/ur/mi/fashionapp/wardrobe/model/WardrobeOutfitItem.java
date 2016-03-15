package de.ur.mi.fashionapp.wardrobe.model;

import android.os.Parcel;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Philip on 29/02/2016.
 */
@ParcelablePlease public class WardrobeOutfitItem extends WardrobeItem {
  // TODO: wieso String?? wieso keine WardrobePieceItems? Das Image der pieces brauchen wir auf jeden Fall!
  String[] pieces = new String[]{null, null, null, null, null, null, null, null, null, null};

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

  public String[] getpicture(){
    return pieces;
  }

  public void setPieces(String[] pieces){
    this.pieces = pieces;
  }
}

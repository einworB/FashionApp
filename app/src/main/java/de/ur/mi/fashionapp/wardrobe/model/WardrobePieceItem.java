package de.ur.mi.fashionapp.wardrobe.model;

import android.os.Parcel;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Philip on 29/02/2016.
 */
@ParcelablePlease public class WardrobePieceItem extends WardrobeItem {

  public static final Creator<WardrobePieceItem> CREATOR = new Creator<WardrobePieceItem>() {
    @Override public WardrobePieceItem createFromParcel(Parcel source) {
      WardrobePieceItem target = new WardrobePieceItem();
      WardrobePieceItemParcelablePlease.readFromParcel(target, source);
      return target;
    }

    @Override public WardrobePieceItem[] newArray(int size) {
      return new WardrobePieceItem[size];
    }
  };

}

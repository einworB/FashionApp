package de.ur.mi.fashionapp.wardrobe.model;

import android.os.Parcel;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Philip on 29/02/2016.
 */
@ParcelablePlease public class WardrobePieceItem extends WardrobeItem {

  int category = 0;
  int tag1 = 0;
  int tag2 = 0;
  int tag3 = 0;

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

  public int getCat() {
    return category;
  }
  public int getTag1() {
    return tag1;
  }
  public int getTag2() {
    return tag2;
  }
  public int getTag3() {
    return tag3;
  }

  public void setCat(int cat) {
    category = cat;
  }
  public void setTag2(int tag) {
    tag2 = tag;
  }
  public void setTag1(int tag) {
    tag1 = tag;
  }
  public void setTag3(int tag) {
    tag3 = tag;
  }

}

package de.ur.mi.fashionapp.wardrobe.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Philip on 29/02/2016.
 */
@ParcelablePlease public class WardrobeItem implements Parcelable{

  String title;
  String ID;
  byte[] image;

  public static final Creator<WardrobeItem> CREATOR = new Creator<WardrobeItem>() {
    @Override public WardrobeItem createFromParcel(Parcel source) {
      WardrobeItem target = new WardrobeItem();
      WardrobeItemParcelablePlease.readFromParcel(target, source);
      return target;
    }

    @Override public WardrobeItem[] newArray(int size) {
      return new WardrobeItem[size];
    }
  };

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public byte[] getImage(){
    return image;
  }

  public void setImage(byte[] image){
    this.image = image;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    WardrobeItemParcelablePlease.writeToParcel(this, dest, flags);
  }
}

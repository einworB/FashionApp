package de.ur.mi.fashionapp.wardrobe.menu.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Philip on 29/02/2016.
 *
 * this menu item links to another wardrobe. as it is also used in the settings activity it is made
 * parcelable to be able to be passed as intent data.
 */
@ParcelablePlease public class WardrobeMenuWardrobeItem extends WardrobeMenuItem
    implements Parcelable {

  String title;
  String ID;

  public static final Creator<WardrobeMenuWardrobeItem> CREATOR =
      new Creator<WardrobeMenuWardrobeItem>() {
        @Override public WardrobeMenuWardrobeItem createFromParcel(Parcel source) {
          WardrobeMenuWardrobeItem target = new WardrobeMenuWardrobeItem();
          WardrobeMenuWardrobeItemParcelablePlease.readFromParcel(target, source);
          return target;
        }

        @Override public WardrobeMenuWardrobeItem[] newArray(int size) {
          return new WardrobeMenuWardrobeItem[size];
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

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    WardrobeMenuWardrobeItemParcelablePlease.writeToParcel(this, dest, flags);
  }
}

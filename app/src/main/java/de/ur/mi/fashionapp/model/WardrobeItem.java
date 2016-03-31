package de.ur.mi.fashionapp.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.hannesdorfmann.parcelableplease.annotation.ParcelableNoThanks;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Philip on 29/02/2016.
 *
 * This class contains all fields, and getter/setters for them that outfits and pieces have in
 * common.
 * To be able to pass it from activity to activity via bundle it is made parcelable.
 * By this the whole items do not have to be loaded in every activity.
 * images however are not included in the parcelable and always have to be loaded because of their
 * filesize.
 */
@ParcelablePlease public class WardrobeItem implements Parcelable {

  String title;
  String ID;
  String WardrobeID;
  @ParcelableNoThanks Bitmap image;

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

  public String getWardrobeID() {
    return WardrobeID;
  }

  public void setWardrobeID(String id) {
    WardrobeID = id;
  }

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
    WardrobeItemParcelablePlease.writeToParcel(this, dest, flags);
  }

  public Bitmap getImage() {
    return image;
  }

  public void setImage(Bitmap image) {
    this.image = image;
  }
}

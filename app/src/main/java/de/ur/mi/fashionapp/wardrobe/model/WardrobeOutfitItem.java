package de.ur.mi.fashionapp.wardrobe.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import com.hannesdorfmann.parcelableplease.annotation.ParcelableNoThanks;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Philip on 29/02/2016.
 */
@ParcelablePlease public class WardrobeOutfitItem extends WardrobeItem {
  String[] pieceIDs = new String[]{null, null, null, null, null, null, null, null, null, null};
  @ParcelableNoThanks Bitmap image1;
  @ParcelableNoThanks Bitmap image2;
  @ParcelableNoThanks Bitmap image3;
  @ParcelableNoThanks Bitmap image4;

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

  public void setPieceIDs(String[] pieceIDs){
    this.pieceIDs = pieceIDs;
  }

  public Bitmap getImage1(){
    return image1;
  }

  public void setImage1(Bitmap image){
    this.image1 = image;
  }

  public Bitmap getImage2(){
    return image2;
  }

  public void setImage2(Bitmap image){
    this.image2 = image;
  }

  public Bitmap getImage3(){
    return image3;
  }

  public void setImage3(Bitmap image){
    this.image3 = image;
  }

  public Bitmap getImage4(){
    return image4;
  }

  public void setImage4(Bitmap image){
    this.image4 = image;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    WardrobeOutfitItemParcelablePlease.writeToParcel(this, dest, flags);
  }
}

package de.ur.mi.fashionapp.wardrobe.model;

import android.os.Parcel;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Philip on 29/02/2016.
 */
@ParcelablePlease public class WardrobeOutfitItem extends WardrobeItem {
  String[] pieces = new String[]{null, null, null, null, null, null, null, null, null, null};
  byte[] image1;
  byte[] image2;
  byte[] image3;
  byte[] image4;

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

  public byte[] getImage1(){
    return image1;
  }

  public void setImage1(byte[] image){
    this.image1 = image;
  }

  public byte[] getImage2(){
    return image2;
  }

  public void setImage2(byte[] image){
    this.image2 = image;
  }

  public byte[] getImage3(){
    return image3;
  }

  public void setImage3(byte[] image){
    this.image3 = image;
  }

  public byte[] getImage4(){
    return image4;
  }

  public void setImage4(byte[] image){
    this.image4 = image;
  }
}

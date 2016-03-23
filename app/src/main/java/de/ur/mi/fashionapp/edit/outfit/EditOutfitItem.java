package de.ur.mi.fashionapp.edit.outfit;

import android.graphics.Bitmap;

/**
 * Created by Philip on 29/02/2016.
 */
public class EditOutfitItem {

  String title;
  String ID;
  Bitmap image;

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

  public Bitmap getImage(){
    return image;
  }

  public void setImage(Bitmap image){
    this.image = image;
  }
}

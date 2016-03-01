package de.ur.mi.fashionapp.wardrobe.model;

/**
 * Created by Philip on 29/02/2016.
 */
public class WardrobeMenuWardrobeItem extends WardrobeMenuItem {

  private int ID;
  private String title;

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public int getID() {
    return ID;
  }
}

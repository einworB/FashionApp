package de.ur.mi.fashionapp.wardrobe.menu.model;

/**
 * Created by Philip on 29/02/2016.
 */
public class WardrobeMenuWardrobeItem extends WardrobeMenuItem {

  private String ID;
  private String title;

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getID() {
    return ID;
  }
}

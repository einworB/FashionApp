package de.ur.mi.fashionapp.wardrobe.menu.model;

/**
 * Created by Philip on 05/03/2016.
 */
public class WardrobeMenuNewWardrobeItem extends WardrobeMenuItem {

  public WardrobeMenuNewWardrobeItem(String title) {
    this.title = title;
  }

  private String title;

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}
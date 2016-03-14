package de.ur.mi.fashionapp.wardrobe.menu.model;

/**
 * Created by Philip on 14/03/2016.
 */
public class WardrobeMenuSectionItem extends WardrobeMenuItem {

  private String title;

  public WardrobeMenuSectionItem(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}

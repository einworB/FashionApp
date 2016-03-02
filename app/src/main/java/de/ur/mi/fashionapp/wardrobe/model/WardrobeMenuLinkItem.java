package de.ur.mi.fashionapp.wardrobe.model;

/**
 * Created by Philip on 29/02/2016.
 */
public class WardrobeMenuLinkItem extends WardrobeMenuItem {

  private String title;

  public WardrobeMenuLinkItem(String title) {
    this.title = title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
}

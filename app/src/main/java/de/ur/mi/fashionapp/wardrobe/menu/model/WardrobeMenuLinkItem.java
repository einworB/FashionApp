package de.ur.mi.fashionapp.wardrobe.menu.model;

/**
 * Created by Philip on 29/02/2016.
 *
 * a menu item that links to another activity. linking is done via the items title and the
 * linkservice
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

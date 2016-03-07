package de.ur.mi.fashionapp.create.model;

/**
 * Created by Philip on 05/03/2016.
 */
public class NewItem {

  private int userID;
  private int itemID;
  private String title;
  // TODO: add other common(outfit+piece) properties + getters/setters

  public NewItem(int userID, int itemID) {
    this.userID = userID;
    this.itemID = itemID;
  }

  public int getUserID() {
    return userID;
  }

  public int getItemID() {
    return itemID;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}

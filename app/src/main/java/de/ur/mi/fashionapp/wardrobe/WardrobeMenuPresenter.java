package de.ur.mi.fashionapp.wardrobe;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeMenuItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeMenuWardrobeItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 01/03/2016.
 */
public class WardrobeMenuPresenter extends MvpBasePresenter<WardrobeMenuView> {

  public void loadMenu() {
    List<WardrobeMenuItem> items = new ArrayList<>();

    // TODO: load wardrobes from parse, wrap them in wardrobe items and return the wardrobes from parse instead of this dummy
    WardrobeMenuWardrobeItem item = new WardrobeMenuWardrobeItem();
    item.setID(1);
    item.setTitle("MeinKleiderschrank");
    items.add(item);

    if (isViewAttached()) {
      getView().showLoading(false);
    }

    if (isViewAttached()) {
      getView().setData(items);
      getView().showContent();
    }
  }
}

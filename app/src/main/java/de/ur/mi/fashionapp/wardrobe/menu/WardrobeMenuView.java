package de.ur.mi.fashionapp.wardrobe.menu;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuItem;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import java.util.List;

/**
 * Created by Philip on 29/02/2016.
 *
 * This interface has to be implemented by the wardrobe activity. The presenter connects to the
 * activity via this interface.
 */
public interface WardrobeMenuView extends MvpLceView<List<WardrobeMenuItem>> {
  void onNewWardrobeCreated(WardrobeMenuWardrobeItem wardrobe);

  void onFirstWardrobeLoaded(String id, String name);
}

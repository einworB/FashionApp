package de.ur.mi.fashionapp.settings;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import java.util.List;

/**
 * Created by Philip on 24/02/2016.
 *
 * This interface has to be implemented by the settings activity. The presenter connects to the
 * activity via this interface.
 */
public interface SettingsView extends MvpLceView<List<WardrobeMenuWardrobeItem>> {
  void onUserDeleted();
}

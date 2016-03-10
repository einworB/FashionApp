package de.ur.mi.fashionapp.settings;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import java.util.List;

/**
 * Created by Philip on 24/02/2016.
 */
public interface SettingsView extends MvpLceView<List<WardrobeMenuWardrobeItem>> {
  void onMailChangeSuccess();
  void onPasswordResetSuccess();
}

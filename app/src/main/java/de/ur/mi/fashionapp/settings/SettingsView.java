package de.ur.mi.fashionapp.settings;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * Created by Philip on 24/02/2016.
 */
public interface SettingsView extends MvpLceView<String> {
  void onMailChangeSuccess();
  void onPasswordResetSuccess();
}

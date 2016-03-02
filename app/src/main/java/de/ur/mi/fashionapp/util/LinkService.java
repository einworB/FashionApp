package de.ur.mi.fashionapp.util;

import android.content.Context;
import android.content.Intent;
import de.ur.mi.fashionapp.settings.SettingsActivity;

/**
 * Created by Philip on 01/03/2016.
 */
public class LinkService {

  private static final String LINK_TITLE_SETTINGS = "Settings";
  private static final String LINK_TITLE_IMPRESSUM = "Impressum";
  private static final String LINK_TITLE_LOGOUT = "Logout";
  private static final String LINK_TITLE_HELP = "Help";

  public static Intent getLink(Context context, String title) {
    // TODO: replace with correct activities
    switch (title) {
      case LINK_TITLE_SETTINGS:
        return new Intent(context, SettingsActivity.class);
      case LINK_TITLE_IMPRESSUM:
        return new Intent(context, SettingsActivity.class);
      case LINK_TITLE_LOGOUT:
        return new Intent(context, SettingsActivity.class);
      case LINK_TITLE_HELP:
        return new Intent(context, SettingsActivity.class);
      default:
        return null;
    }
  }
}

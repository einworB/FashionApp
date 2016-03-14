package de.ur.mi.fashionapp.util;

import android.content.Context;
import android.content.Intent;
import de.ur.mi.fashionapp.detail.OutfitDetailActivity;
import de.ur.mi.fashionapp.detail.PieceDetailActivity;
import de.ur.mi.fashionapp.edit.outfit.EditOutfitActivity;
import de.ur.mi.fashionapp.edit.piece.EditPieceActivity;
import de.ur.mi.fashionapp.settings.SettingsActivity;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;

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

  public static Intent getCreateIntent(Context context, int type) {
    switch (type) {
      case WardrobeFragment.TYPE_OUTFIT:
        return new Intent(context, EditOutfitActivity.class);
      case WardrobeFragment.TYPE_PIECE:
        return new Intent(context, EditPieceActivity.class);
      default:
        return null;
    }
  }

  public static Intent getUpdateIntent(Context context, int type, int itemID) {
    Intent i;
    switch (type) {
      case WardrobeFragment.TYPE_OUTFIT:
        i = new Intent(context, EditOutfitActivity.class);
        i.putExtra(EditOutfitActivity.KEY_ID, itemID);
        return i;
      case WardrobeFragment.TYPE_PIECE:
        i = new Intent(context, EditPieceActivity.class);
        i.putExtra(EditPieceActivity.KEY_ID, itemID);
        return i;
      default:
        return null;
    }
  }

  public static Intent getDetailIntent(Context context, int type, String itemID) {
    Intent i;
    switch (type) {
      case WardrobeFragment.TYPE_OUTFIT:
        i = new Intent(context, OutfitDetailActivity.class);
        i.putExtra(EditOutfitActivity.KEY_ID, itemID);
        return i;
      case WardrobeFragment.TYPE_PIECE:
        i = new Intent(context, PieceDetailActivity.class);
        i.putExtra(EditPieceActivity.KEY_ID, itemID);
        return i;
      default:
        return null;
    }
  }
}

package de.ur.mi.fashionapp.util;

import android.content.Context;
import android.content.Intent;
import de.ur.mi.fashionapp.detail.OutfitDetailActivity;
import de.ur.mi.fashionapp.detail.PieceDetailActivity;
import de.ur.mi.fashionapp.edit.outfit.EditOutfitActivity;
import de.ur.mi.fashionapp.edit.outfit.pickpieces.PickOutfitPiecesActivity;
import de.ur.mi.fashionapp.edit.piece.EditPieceActivity;
import de.ur.mi.fashionapp.extras.ImprintActivity;
import de.ur.mi.fashionapp.extras.HelpActivity;
import de.ur.mi.fashionapp.login.LoginActivity;
import de.ur.mi.fashionapp.settings.SettingsActivity;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;
import de.ur.mi.fashionapp.model.WardrobeItem;
import de.ur.mi.fashionapp.model.WardrobeOutfitItem;

/**
 * Created by Philip on 01/03/2016.
 *
 * this linkservice provides a clean and simple api for the linking of activities via intents.
 */
public class LinkService {

    private static final String LINK_TITLE_SETTINGS = "Settings";
    private static final String LINK_TITLE_IMPRESSUM = "Impressum";
    private static final String LINK_TITLE_LOGOUT = "Logout";
    private static final String LINK_TITLE_HELP = "Help";

    public static Intent getLink(Context context, String title) {
        switch (title) {
            case LINK_TITLE_SETTINGS:
                return new Intent(context, SettingsActivity.class);
            case LINK_TITLE_IMPRESSUM:
                return new Intent(context, ImprintActivity.class);
            case LINK_TITLE_LOGOUT:
                return new Intent(context, LoginActivity.class);
            case LINK_TITLE_HELP:
                return new Intent(context, HelpActivity.class);
            default:
                return null;
        }
    }

    public static Intent getCreateIntent(Context context, int type, String wardrobeID) {
        switch (type) {
            case WardrobeFragment.TYPE_OUTFIT:
                Intent outfit = new Intent(context, EditOutfitActivity.class);
                outfit.putExtra("WardrobeID", wardrobeID);
                return outfit;
            case WardrobeFragment.TYPE_PIECE:
                Intent piece = new Intent(context, EditPieceActivity.class);
                piece.putExtra("WardrobeID", wardrobeID);
                return piece;
            default:
                return null;
        }
    }

    public static Intent getUpdateIntent(Context context, int type, WardrobeItem item, String wardrobeID, boolean isDetail) {
        Intent i;
        switch (type) {
            case WardrobeFragment.TYPE_OUTFIT:
                i = new Intent(context, EditOutfitActivity.class);
                i.putExtra(EditOutfitActivity.KEY_ITEM, item);
                i.putExtra("WardrobeID", wardrobeID);
                i.putExtra("isDetail",isDetail);
                return i;
            case WardrobeFragment.TYPE_PIECE:
                i = new Intent(context, EditPieceActivity.class);
                i.putExtra(EditPieceActivity.KEY_ITEM, item);
                i.putExtra("WardrobeID", wardrobeID);
                i.putExtra("isDetail",isDetail);
                return i;
            default:
                return null;
        }
    }

    public static Intent getDetailIntent(Context context, int type, WardrobeItem item, String wardrobeID, String itemID, boolean isDetail) {
        Intent i;
        switch (type) {
            case WardrobeFragment.TYPE_OUTFIT:
                i = new Intent(context, OutfitDetailActivity.class);
                i.putExtra(OutfitDetailActivity.KEY_ITEM, item);
                i.putExtra(OutfitDetailActivity.KEY_WARDROBE_ID, wardrobeID);
                i.putExtra("isDetail",isDetail);
                return i;
            case WardrobeFragment.TYPE_PIECE:
                i = new Intent(context, PieceDetailActivity.class);
                i.putExtra(PieceDetailActivity.KEY_ITEM, item);
                i.putExtra(PieceDetailActivity.KEY_WARDROBE_ID, wardrobeID);
                i.putExtra(PieceDetailActivity.KEY_ITEM_ID, itemID);
                i.putExtra("isDetail",isDetail);
                return i;
            default:
                return null;
        }
    }

    public static Intent getOutfitPieceIntent(Context context, WardrobeOutfitItem item, String wardrobeID) {
        Intent i = new Intent(context, PickOutfitPiecesActivity.class);
        i.putExtra(EditOutfitActivity.KEY_ITEM, item);
        i.putExtra("WardrobeID", wardrobeID);
        return i;
    }
}

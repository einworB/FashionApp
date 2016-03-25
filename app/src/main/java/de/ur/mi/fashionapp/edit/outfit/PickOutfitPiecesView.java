package de.ur.mi.fashionapp.edit.outfit;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import java.util.List;

import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

/**
 * Created by Jana on 22.03.2016.
 */
public interface PickOutfitPiecesView extends MvpLceView<List<WardrobePieceItem>> {
    void onImageLoaded(String itemID);
}

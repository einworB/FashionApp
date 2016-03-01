package de.ur.mi.fashionapp.wardrobe;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 29/02/2016.
 */
public class WardrobePresenter extends MvpBasePresenter<WardrobeView>{

  public void loadWardrobe(int ID) {
    // TODO: extend item models, maybe load only one big wrapper item which has getPieces() and getOutfits() functions
    // TODO: replace dummy list with real items loaded from parse

    List<WardrobeItem> items = new ArrayList<>();
    WardrobePieceItem piece1 = new WardrobePieceItem();
    piece1.setTitle("Eine Hose");
    items.add(piece1);
    WardrobePieceItem piece2 = new WardrobePieceItem();
    piece2.setTitle("Ein Hemd");
    items.add(piece2);
    WardrobePieceItem piece3 = new WardrobePieceItem();
    piece3.setTitle("Ein Hut");
    items.add(piece3);
    WardrobeOutfitItem outfit1 = new WardrobeOutfitItem();
    outfit1.setTitle("Mein 1. Outfit");
    items.add(outfit1);
    WardrobeOutfitItem outfit2 = new WardrobeOutfitItem();
    outfit2.setTitle("Mein 2. Outfit");
    items.add(outfit2);

    if (isViewAttached()) {
      getView().setData(items);
    }
  }
}

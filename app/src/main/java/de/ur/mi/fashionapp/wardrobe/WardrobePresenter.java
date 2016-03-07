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



  }

  public void loadPieces(int userID, int wardrobeID, boolean pullToRefresh) {
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }

    // TODO: replace dummy model with model loading from parse
    List<WardrobeItem> items = new ArrayList<>();
    WardrobePieceItem piece1 = new WardrobePieceItem();
    piece1.setTitle("Eine Hose");
    piece1.setID(0);
    items.add(piece1);
    WardrobePieceItem piece2 = new WardrobePieceItem();
    piece2.setTitle("Ein Hemd");
    piece2.setID(1);
    items.add(piece2);
    WardrobePieceItem piece3 = new WardrobePieceItem();
    piece3.setTitle("Ein Hut");
    piece3.setID(2);
    items.add(piece3);

    if (isViewAttached()) {
      // TODO: show real Error if model loading failed
      if (true) {
        getView().setData(items);
        getView().showContent();
      }
      else {
        Throwable e = new Exception();
        getView().showError(e, pullToRefresh);
      }
    }
  }

  public void loadOutfits(int userID, int wardrobeID, boolean pullToRefresh) {
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }

    // TODO: replace dummy model with model loading from parse
    List<WardrobeItem> items = new ArrayList<>();
    WardrobeOutfitItem outfit1 = new WardrobeOutfitItem();
    outfit1.setTitle("Mein 1. Outfit");
    outfit1.setID(0);
    items.add(outfit1);
    WardrobeOutfitItem outfit2 = new WardrobeOutfitItem();
    outfit2.setTitle("Mein 2. Outfit");
    outfit2.setID(1);
    items.add(outfit2);

    if (isViewAttached()) {
      // TODO: show real Error if model loading failed
      if (true) {
        getView().setData(items);
        getView().showContent();
      }
      else {
        Throwable e = new Exception();
        getView().showError(e, pullToRefresh);
      }
    }
  }
}

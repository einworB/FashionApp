package de.ur.mi.fashionapp.wardrobe;

import android.content.Context;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 29/02/2016.
 */
public class WardrobePresenter extends MvpBasePresenter<WardrobeView>{

  private Context context;
  private List<WardrobeItem> items;

  public WardrobePresenter(Context context, WardrobeView view) {
    this.context = context;
    attachView(view);
  }

  public void loadWardrobe(String ID) {
    // TODO: extend item models, maybe load only one big wrapper item which has getPieces() and getOutfits() functions
    // TODO: replace dummy list with real items loaded from parse

  }



  public void loadPieces(boolean pullToRefresh) {
    if (isViewAttached()) {
      loadWardrobeItems(false, pullToRefresh);
    }
  }

  public void loadOutfits(boolean pullToRefresh) {
    if (isViewAttached()) {
      loadWardrobeItems(true, pullToRefresh);
    }
  }

  public void loadWardrobeItems(final boolean isOutfit, boolean pullToRefresh){
    ParseQuery<ParseObject> query;
    if(isOutfit) {
      query = ParseQuery.getQuery("Outfit");
    }
    else{
      query = ParseQuery.getQuery("Piece");
    }
    query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    /*
    * TODO: Filter Wardrobe
    * query.whereEqualTo("WardrobeID", currentName);
     */
    getView().showLoading(true);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, com.parse.ParseException e) {
        getView().showLoading(false);
        if (e == null) {
          items = new ArrayList<>();
          for( int i = 0; i<objects.size(); i++) {
            if (isOutfit) {
              WardrobeOutfitItem outfit = new WardrobeOutfitItem();
              outfit.setTitle(objects.get(i).getString("Name"));
              outfit.setID(objects.get(i).getObjectId());
              // TODO: add image, category, color, occasion etc properties!!
              items.add(outfit);
            }
            else {
              WardrobePieceItem piece = new WardrobePieceItem();
              piece.setTitle(objects.get(i).getString("Name"));
              piece.setID(objects.get(i).getObjectId());
              // TODO: add image, pieces, piece images etc properties!!!
              items.add(piece);
            }

          }
          getView().setData(items);
          getView().showContent();
        }
        else{
          getView().showError(e, false);
        }
      }
    });
  }


}

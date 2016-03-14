package de.ur.mi.fashionapp.wardrobe;

import android.content.Context;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
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
      loadWardrobeItems("Piece");
    }
  }

  public void loadOutfits(boolean pullToRefresh) {
    if (isViewAttached()) {
      loadWardrobeItems("Outfit");
    }
  }

  public void loadWardrobeItems(String kind){
    ParseQuery<ParseObject> query;
    if(kind.equals("Outfit")) {
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
            WardrobeOutfitItem piece = new WardrobeOutfitItem();
            piece.setTitle(objects.get(i).getString("Name"));
            piece.setID(objects.get(i).getObjectId());
            items.add(piece);
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

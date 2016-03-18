package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;

/**
 * Created by Philip on 29/02/2016.
 */
public class EditOutfitPresenter extends MvpBasePresenter<EditOutfitView>{

  private Context context;

  public EditOutfitPresenter(Context context, EditOutfitView view) {
    this.context = context;
    attachView(view);
  }


  public void createOutfit(WardrobeOutfitItem item, boolean pullToRefresh) {
    if (isViewAttached()) {
      ParseObject wr = new ParseObject("Outfit");
      wr.put("Name",item.getTitle());
      wr.put("UserID", ParseUser.getCurrentUser().getObjectId());


      //Add all the pieces id's
      String[] pieces = item.getPieceIDs();
      for(int i = 0; i < pieces.length ; i++){
        if( pieces[i] != null){
          String entry = "Piece"+(i+1);
          wr.put(entry,pieces[i]);
        }
      }

      getView().showLoading(true);
      wr.saveInBackground(new SaveCallback() {
        @Override public void done(com.parse.ParseException e) {
          if (e == null) {
            getView().showContent();
            getView().onOutfitEdited();
          } else {
            getView().showError(e, false);
          }
        }
      });
    }
  }

  public void updateOutfit(String itemID, final WardrobeOutfitItem item, final boolean pullToRefresh) {
    if (isViewAttached()) getView().showLoading(pullToRefresh);
    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Outfit");
    query.whereEqualTo("objectId",itemID);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e==null) {
          ParseObject parseObject = objects.get(0);
          parseObject.put("Name",item.getTitle());
          String[] ids = item.getPieceIDs();
          for(int i= 0; i< ids.length; i++){
            parseObject.put("Piece"+i,ids[0]);
          }
          parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
              if(e==null){
                if(isViewAttached())getView().onOutfitEdited();
                if(isViewAttached())getView().showContent();
              }else{
                if(isViewAttached())getView().showError(e, pullToRefresh);
              }
            }
          });
        }
      }
    });
  }


  public void deleteOutfit(String itemID, final boolean pullToRefresh){
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Outfit");
    if (isViewAttached())getView().showLoading(pullToRefresh);
    query.whereEqualTo("objectId",itemID);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, com.parse.ParseException e) {
        if(e==null) {
          for (int i = 0; i < objects.size(); i++) {
            objects.get(i).deleteInBackground();
          }
          if (isViewAttached())getView().showContent();
        }
        else{
          if (isViewAttached())getView().showError(e,pullToRefresh);
        }
      }
    });
  }
}

package de.ur.mi.fashionapp.wardrobe;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
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
              items.add(createOutfit(objects.get(i)));
            }
            else {
              items.add(createPiece(objects.get(i)));
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

  private WardrobePieceItem createPiece(ParseObject obj){
    final WardrobePieceItem piece = new WardrobePieceItem();
    piece.setTitle(obj.getString("Name"));
    piece.setID(obj.getObjectId());
    ParseFile fileObject = (ParseFile) obj.get("Image");
    fileObject.getDataInBackground(new GetDataCallback() {
      @Override
      public void done(byte[] data, ParseException e) {
        if(e==null)piece.setImage(data);
      }
    });
    piece.setCat(obj.getInt("Category"));
    piece.setTag1(obj.getInt("Tag1"));
    piece.setTag2(obj.getInt("Tag2"));
    piece.setTag3(obj.getInt("Tag3"));
    return piece;
  }

  private WardrobeOutfitItem createOutfit(ParseObject obj) {
    WardrobeOutfitItem outfit = new WardrobeOutfitItem();
    outfit.setTitle(obj.getString("Name"));
    outfit.setID(obj.getObjectId());
    String[] pieces = new String[]{"0","0","0","0","0","0","0","0","0","0"};
    for (int i=0; i<10;i++){
      if(obj.getString("Piece"+(i+1))!=null)pieces[i]=obj.getString("Piece"+(i+1));
    }
    outfit.setImage1(getPiecePicture(pieces[0]));
    outfit.setImage2(getPiecePicture(pieces[1]));
    outfit.setImage3(getPiecePicture(pieces[2]));
    outfit.setImage4(getPiecePicture(pieces[3]));
    return outfit;
  }

  private byte[] currentPictureData;
  private byte[] getPiecePicture(String ID){
    ParseQuery<ParseObject> query= ParseQuery.getQuery("Piece");
    query.whereEqualTo("objectId", ID);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if (e == null && objects.size()>0) {
          ParseObject obj = objects.get(0);
          ParseFile fileObject = (ParseFile) obj.get("Image");
          fileObject.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
              if(e==null)setdata(data);
            }
          });
        }
      }
    });
    return currentPictureData;
  }
   private void setdata(byte[] data){
     this.currentPictureData = data;
   }


}

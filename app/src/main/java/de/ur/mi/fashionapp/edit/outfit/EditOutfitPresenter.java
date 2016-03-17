package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
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

      /*
      ParseFile file = new ParseFile("pictureOfThisPiece" + ".bmp",item.getImage1());
      // Upload the image into Parse Cloud
      getView().showLoading(true);
      file.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null) {
            getView().showContent();
            getView().onOutfitEdited();
          } else {
            getView().showError(e, false);
          }
        }
      });

      ParseFile file2 = new ParseFile("pictureOfThisPiece" + ".bmp",item.getImage2());
      // Upload the image into Parse Cloud
      getView().showLoading(true);
      file.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null) {
            getView().showContent();
            getView().onOutfitEdited();
          } else {
            getView().showError(e, false);
          }
        }
      });

      ParseFile file3 = new ParseFile("pictureOfThisPiece" + ".bmp",item.getImage3());
      // Upload the image into Parse Cloud
      getView().showLoading(true);
      file.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null) {
            getView().showContent();
            getView().onOutfitEdited();
          } else {
            getView().showError(e, false);
          }
        }
      });

      ParseFile file4 = new ParseFile("pictureOfThisPiece" + ".bmp",item.getImage4());
      // Upload the image into Parse Cloud
      getView().showLoading(true);
      file.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null) {
            getView().showContent();
            getView().onOutfitEdited();
          } else {
            getView().showError(e, false);
          }
        }
      });
      */

      /*Müssten noch hinzugefügt werden
      wr.put("Image",file);
      */

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

  public void updateOutfit(String itemID, WardrobeOutfitItem item, boolean pullToRefresh) {
    // show loading while uploading
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }

    // TODO: insert upload logic


    if (isViewAttached()) {
      // TODO: show real Error if model loading failed
      if (true) {
        getView().onOutfitEdited();
        getView().showContent();
      }
      else {
        Throwable e = new Exception();
        getView().showError(e, pullToRefresh);
      }
    }
  }
}

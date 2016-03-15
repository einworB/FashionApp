package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.ParseException;
import com.parse.ParseFile;
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
      ParseObject wr = new ParseObject("Piece");
      wr.put("Name",item.getTitle());
      wr.put("UserID", ParseUser.getCurrentUser().getObjectId());


      //Add all the picture id's
      String[] pictures = item.getpicture();
      for(int i = 0; i < pictures.length ; i++){
        if( pictures[i] != null){
          String entry = "Piece"+(i+1);
          wr.put(entry,pictures[i]);
        }
      }

      ParseFile file = new ParseFile("pictureOfThisPiece" + ".bmp",item.getImage());
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

      wr.put("Image",file);
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

  public void updateOutfit(int itemID, WardrobeOutfitItem item, boolean pullToRefresh) {
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

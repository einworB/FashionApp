package de.ur.mi.fashionapp.detail;

import android.graphics.BitmapFactory;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;
import java.util.List;

/**
 * Created by Philip on 17/03/2016.
 */
public class DetailPresenter extends MvpBasePresenter<DetailView> {

  public DetailPresenter(DetailView view) {
    attachView(view);
  }

  void loadOutfitImages(final String[] pieceIDs, final WardrobeOutfitItem outfit) {
    if(isViewAttached()){
      getView().showLoading(true);
    }
    if (pieceIDs.length >= 4) {
      for (int i = 0; i < 4; i++) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Piece");
        query.whereEqualTo("objectId", pieceIDs[i]);
        final int type = i;
        query.findInBackground(new FindCallback<ParseObject>() {
          @Override public void done(List<ParseObject> objects, ParseException e) {
            if (e == null && objects.size() > 0) {
              ParseObject obj = objects.get(0);
              ParseFile fileObject = (ParseFile) obj.get("Image");
              fileObject.getDataInBackground(new GetDataCallback() {
                @Override public void done(byte[] data, ParseException e) {
                  if (e == null && data != null) {
                    switch (type) {
                      case 0:
                        outfit.setImage1(BitmapFactory.decodeByteArray(data, 0, data.length));
                        break;
                      case 1:
                        outfit.setImage2(BitmapFactory.decodeByteArray(data, 0, data.length));
                        break;
                      case 2:
                        outfit.setImage3(BitmapFactory.decodeByteArray(data, 0, data.length));
                        break;
                      case 3:
                        outfit.setImage4(BitmapFactory.decodeByteArray(data, 0, data.length));
                        break;
                    }
                    if (isViewAttached()) {
                      getView().onImageLoaded(pieceIDs[type]);
                    }
                  }
                }
              });
            }
          }
        });
      }
    }
  }

  public void loadPieceImage(String id, final WardrobePieceItem piece) {
    if(isViewAttached()){
      getView().showLoading(true);
    }
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Piece");
    query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());

    query.whereEqualTo("objectId", id);

    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, ParseException e) {
        ParseObject obj = objects.get(0);
        ParseFile fileObject = (ParseFile) obj.get("Image");
        fileObject.getDataInBackground(new GetDataCallback() {
          @Override public void done(byte[] data, ParseException e) {
            if (e == null) piece.setImage(BitmapFactory.decodeByteArray(data, 0, data.length));
            if (isViewAttached()) {
              getView().onImageLoaded(piece.getID());
            }
          }
        });
      }
    });
  }
}

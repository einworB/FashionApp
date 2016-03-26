package de.ur.mi.fashionapp.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import de.ur.mi.fashionapp.util.ImageHelper;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;
import java.util.List;

/**
 * Created by Philip on 17/03/2016.
 */
public class DetailPresenter extends MvpBasePresenter<DetailView> {
  Context context;

  public DetailPresenter(DetailView view, Context context) {

    attachView(view);
    this.context = context;
  }

  void loadOutfitImages(final String[] pieceIDs, final WardrobeOutfitItem outfit) {
    if (isViewAttached()) {
      getView().showLoading(true);
    }
    for (int i = 0; i < pieceIDs.length; i++) {
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Piece");
      query.whereEqualTo("objectId", pieceIDs[i]);
      if (pieceIDs[i] == null || pieceIDs[i].isEmpty()) {
        continue;
      }
      final int piecePosition = i;
      query.findInBackground(new FindCallback<ParseObject>() {
        @Override public void done(List<ParseObject> objects, ParseException e) {
          if (e == null && objects.size() > 0) {
            ParseObject obj = objects.get(0);
            ParseFile fileObject = (ParseFile) obj.get("Image");
            fileObject.getDataInBackground(new GetDataCallback() {
              @Override public void done(byte[] data, ParseException e) {
                if (e == null && data != null) {
                  Bitmap[] images = outfit.getImages();
                  images[piecePosition] = ImageHelper.getScaledBitmap(data);

                  if (isViewAttached()) {
                    getView().onImageLoaded(pieceIDs[piecePosition]);
                    getView().showContent();
                  }
                }
                else{
                  if(e.getCode()==ParseException.CONNECTION_FAILED){
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();}
                  else if(isViewAttached()) getView().showError(e, false);
                }
              }
            });
          }
        }
      });
    }
  }

  public void loadPieceImage(String id, final WardrobePieceItem piece) {
    if (isViewAttached()) {
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
            if (e == null) {
              piece.setImage(ImageHelper.getScaledBitmap(data));
              if (isViewAttached()) {
                getView().onImageLoaded(piece.getID());
                getView().showContent();
              }
            }
            else{
              if(e.getCode()==ParseException.CONNECTION_FAILED){
                Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();}
              else if(isViewAttached()) getView().showError(e, false);
            }
          }
        });
      }
    });
  }
}

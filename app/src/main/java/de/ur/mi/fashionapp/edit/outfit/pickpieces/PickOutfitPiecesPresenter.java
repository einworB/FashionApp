package de.ur.mi.fashionapp.edit.outfit.pickpieces;

import android.content.Context;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import de.ur.mi.fashionapp.util.ImageHelper;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jana on 22.03.2016.
 *
 * This presenter loads all pieces of the current wardrobe. Image loading is handled separately and
 * the images are
 * updated when the asynchronous request finished.
 */
public class PickOutfitPiecesPresenter extends MvpBasePresenter<PickOutfitPiecesView> {

  private Context context;

  public PickOutfitPiecesPresenter(Context context, PickOutfitPiecesView view) {
    this.context = context;
    attachView(view);
  }

  public void loadPieceItems(String wardrobeID, boolean pullToRefresh) {
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }
    ParseQuery<ParseObject> query;

    query = ParseQuery.getQuery("Piece");

    query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());

    if (wardrobeID != null) {

      query.whereEqualTo("WardrobeID", wardrobeID);
    }

    if (isViewAttached()) {
      getView().showLoading(true);
    }

    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, com.parse.ParseException e) {

        if (e == null) {
          ArrayList<WardrobePieceItem> items = new ArrayList<>();
          for (int i = 0; i < objects.size(); i++) {

            items.add(createPiece(objects.get(i)));
          }
          if (isViewAttached()) {
            getView().setData(items);
            getView().showContent();
          }
        } else {
          if (isViewAttached()) {
            getView().showError(e, false);
          }
        }
      }
    });
  }

  private WardrobePieceItem createPiece(ParseObject obj) {
    final WardrobePieceItem piece = new WardrobePieceItem();
    piece.setTitle(obj.getString("Name"));
    piece.setID(obj.getObjectId());
    piece.setWardrobeID(obj.getString("WardrobeID"));
    ParseFile fileObject = (ParseFile) obj.get("Image");
    fileObject.getDataInBackground(new GetDataCallback() {
      @Override public void done(byte[] data, ParseException e) {
        if (e == null) {
          piece.setImage(ImageHelper.getScaledBitmap(data));
        }
        if (isViewAttached()) {
          getView().onImageLoaded(piece.getID());
        }
      }
    });
    piece.setCat(obj.getInt("Category"));
    piece.setSeason(obj.getInt("Tag1"));
    piece.setOccasion(obj.getInt("Tag2"));
    piece.setColor(obj.getInt("Tag3"));
    return piece;
  }
}

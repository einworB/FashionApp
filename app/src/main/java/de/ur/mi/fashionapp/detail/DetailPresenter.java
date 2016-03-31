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
 *
 * This class is responsible for connecting to the parse database and loading the piece or outfit
 * item which shall be displayed in detail. Image loading is handled separately and the images are
 * updated when the asynchronous request finished.
 */
public class DetailPresenter extends MvpBasePresenter<DetailView> {
  Context context;

  public DetailPresenter(DetailView view, Context context) {

    attachView(view);
    this.context = context;
  }

  public void loadPiece(String pieceID, boolean pullToRefresh) {
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Piece");
    query.whereEqualTo("objectId", pieceID);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, ParseException e) {
        if (e == null) {
          if (objects.size() > 0) {
            WardrobePieceItem piece = createPiece(objects.get(0));
            if (isViewAttached()) {
              getView().showContent();
              getView().setData(piece);
            }
          }
        } else {
          if (e.getCode() == ParseException.CONNECTION_FAILED) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
          } else if (isViewAttached()) {
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
          if (isViewAttached()) {
            getView().onImageLoaded(piece.getID());
          }
        } else {
          if (e.getCode() == ParseException.CONNECTION_FAILED) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
          } else if (isViewAttached()) {
            getView().showError(e, false);
          }
        }
      }
    });
    piece.setCat(obj.getInt("Category"));
    piece.setSeason(obj.getInt("Tag1"));
    piece.setOccasion(obj.getInt("Tag2"));
    piece.setColor(obj.getInt("Tag3"));
    return piece;
  }

  void loadOutfitImages(final String[] pieceIDs, final WardrobeOutfitItem outfit,
      boolean pullToRefresh) {
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }
    for (String pieceID : pieceIDs) {
      if (pieceID == null || pieceID.isEmpty()) {
        continue;
      }
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Piece");
      query.whereEqualTo("objectId", pieceID);
      query.findInBackground(new FindCallback<ParseObject>() {
        @Override public void done(List<ParseObject> objects, ParseException e) {
          if (e == null && objects.size() > 0) {
            ParseObject obj = objects.get(0);
            ParseFile fileObject = (ParseFile) obj.get("Image");
            getPieceImage(pieceIDs, outfit, fileObject, obj.getObjectId());
          } else if (e != null) {
            if (e.getCode() == ParseException.CONNECTION_FAILED) {
              Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
            } else if (isViewAttached()) {
              getView().showError(e, false);
            }
          }
        }
      });
    }
  }

  private void getPieceImage(final String[] pieceIDs, final WardrobeOutfitItem outfit,
      ParseFile file, final String pieceID) {
    file.getDataInBackground(new GetDataCallback() {
      @Override public void done(byte[] data, ParseException e) {
        if (e == null) {
          if (data != null) {
            Bitmap[] images = outfit.getImages();
            for (int j = 0; j < pieceIDs.length; j++) {
              if (pieceIDs[j].equals(pieceID)) {
                images[j] = ImageHelper.getScaledBitmap(data);
                break;
              }
            }
            if (isViewAttached()) {
              getView().onImageLoaded(pieceID);
              getView().showContent();
            }
          }
        } else {
          if (e.getCode() == ParseException.CONNECTION_FAILED) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
          } else if (isViewAttached()) {
            getView().showError(e, false);
          }
        }
      }
    });
  }

  public void loadPieceImage(String id, final WardrobePieceItem piece, boolean pullToRefresh) {
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
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
            } else {
              if (e.getCode() == ParseException.CONNECTION_FAILED) {
                Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
              } else if (isViewAttached()) getView().showError(e, false);
            }
          }
        });
      }
    });
  }
}

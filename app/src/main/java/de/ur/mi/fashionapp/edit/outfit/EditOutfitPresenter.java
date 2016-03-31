package de.ur.mi.fashionapp.edit.outfit;

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
import com.parse.SaveCallback;
import de.ur.mi.fashionapp.util.ImageHelper;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import java.util.List;

/**
 * Created by Philip on 29/02/2016.
 *
 * This presenter connects to the parse database to load the items of the outfit if an already
 * existing outfit is edited and to create a new or update the existing outfit.
 */
public class EditOutfitPresenter extends MvpBasePresenter<EditOutfitView> {

  private Context context;

  public EditOutfitPresenter(Context context, EditOutfitView view) {
    this.context = context;
    attachView(view);
  }

  void loadOutfitImages(final String[] pieceIDs, final WardrobeOutfitItem outfit) {
    if (isViewAttached()) {
      getView().showLoading(true);
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

  public void createOutfit(WardrobeOutfitItem item, boolean pullToRefresh) {
    if (isViewAttached()) {
      ParseObject wr = new ParseObject("Outfit");
      wr.put("Name", item.getTitle());
      wr.put("UserID", ParseUser.getCurrentUser().getObjectId());
      wr.put("WardrobeID", item.getWardrobeID());

      //Add all the pieces id's
      String[] pieces = item.getPieceIDs();
      for (int i = 0; i < pieces.length; i++) {
        if (pieces[i] != null) {
          String entry = "Piece" + (i);
          wr.put(entry, pieces[i]);
        }
      }
      if (isViewAttached()) {
        getView().showLoading(pullToRefresh);
      }
      wr.saveInBackground(new SaveCallback() {
        @Override public void done(com.parse.ParseException e) {
          if (e == null) {
            if (isViewAttached()) {
              getView().showContent();
              getView().onOutfitEdited();
            }
          } else {
            if (e.getCode() == ParseException.CONNECTION_FAILED) {
              Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
            } else if (isViewAttached()) getView().showError(e, false);
          }
        }
      });
    }
  }

  public void updateOutfit(String itemID, final WardrobeOutfitItem item,
      final boolean pullToRefresh) {
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }
    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Outfit");
    query.whereEqualTo("objectId", itemID);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, ParseException e) {
        if (e == null) {
          ParseObject parseObject = objects.get(0);
          parseObject.put("Name", item.getTitle());
          String[] ids = item.getPieceIDs();
          for (int i = 0; i < ids.length; i++) {
            if (ids[i] != null) {
              parseObject.put("Piece" + i, ids[i]);
            } else {
              parseObject.remove("Piece" + i);
            }
          }
          parseObject.saveInBackground(new SaveCallback() {
            @Override public void done(ParseException e) {
              if (e == null) {
                if (isViewAttached()) getView().onOutfitEdited();
                if (isViewAttached()) getView().showContent();
              } else {
                if (e.getCode() == ParseException.CONNECTION_FAILED) {
                  Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
                } else if (isViewAttached()) getView().showError(e, false);
              }
            }
          });
        }
      }
    });
  }
}

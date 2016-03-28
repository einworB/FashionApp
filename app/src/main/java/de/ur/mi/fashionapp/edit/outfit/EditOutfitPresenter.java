package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

import de.ur.mi.fashionapp.util.ImageHelper;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

/**
 * Created by Philip on 29/02/2016.
 */
public class EditOutfitPresenter extends MvpBasePresenter<EditOutfitView> {

  private Context context;

  public EditOutfitPresenter(Context context, EditOutfitView view) {
    this.context = context;
    attachView(view);
  }

  public void loadItems(String outfitItemId, boolean pullToRefresh) {
    // TODO: get items from outfit with id = outfitID (sh outfitdetail)
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }
    ParseQuery<ParseObject> query;

    query = ParseQuery.getQuery("Piece");

    query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());

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
    ParseFile fileObject = (ParseFile) obj.get("Image");
    fileObject.getDataInBackground(new GetDataCallback() {
      @Override public void done(byte[] data, ParseException e) {
        if (e == null) {
          piece.setImage(ImageHelper.getScaledBitmap(data));
          Log.d("EOA P", "setImage: " + ImageHelper.getScaledBitmap(data));
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

    /*public void loadOutfitImages(String id, final WardrobeOutfitItem outfit) {
        if (isViewAttached()) {
            getView().showLoading(true);
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Outfit");
        query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());

        query.whereEqualTo("objectId", id);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                ParseObject obj = objects.get(0);
                outfit.setTitle(obj.getString("Name"));
                outfit.setID(obj.getObjectId());
                outfit.setWardrobeID(obj.getString("WardrobeID"));
                String[] pieces = outfit.getPieceIDs();
                for (int i = 0; i < 10; i++) {
                    if (obj.getString("Piece" + (i + 1)) != null)
                        pieces[i] = obj.getString("Piece" + (i + 1));
                }
                for (int i = 0; i < outfit.getImages().length; i++) {
                    getPiecePicture(outfit, pieces, i);
                }
            }
        });
    }*/

  private void getPiecePicture(final WardrobeOutfitItem outfit, final String[] pieces,
      final int number) {
    if (pieces[number] != null && !pieces[number].isEmpty()) {
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Piece");
      query.whereEqualTo("objectId", (pieces[number]));
      query.findInBackground(new FindCallback<ParseObject>() {
        @Override public void done(List<ParseObject> objects, ParseException e) {
          if (e == null && objects.size() > 0) {
            ParseObject obj = objects.get(0);
            ParseFile fileObject = (ParseFile) obj.get("Image");
            fileObject.getDataInBackground(new GetDataCallback() {
              @Override public void done(byte[] data, ParseException e) {
                if (e == null && data != null) {
                  Bitmap[] images = outfit.getImages();
                  images[number] = ImageHelper.getScaledBitmap(data);

                  if (isViewAttached()) {
                    getView().onImageLoaded(outfit.getID());
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
        }
      });
    }
  }

  void loadOutfitImages(final String[] pieceIDs, final WardrobeOutfitItem outfit) {
    if (isViewAttached()) {
      getView().showLoading(true);
    }
    for (int i = 0; i < pieceIDs.length; i++) {
      if (pieceIDs[i] == null || pieceIDs[i].isEmpty()) {
        continue;
      }
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Piece");
      query.whereEqualTo("objectId", pieceIDs[i]);
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
          String entry = "Piece" + (i + 1);
          wr.put(entry, pieces[i]);
        }
      }
      if (isViewAttached()) {
        getView().showLoading(true);
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
            parseObject.put("Piece" + i, ids[0]);
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

  public void deleteOutfit(String itemID, final boolean pullToRefresh) {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Outfit");
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }
    query.whereEqualTo("objectId", itemID);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, com.parse.ParseException e) {
        if (e == null) {
          for (int i = 0; i < objects.size(); i++) {
            objects.get(i).deleteInBackground();
          }
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

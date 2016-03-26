package de.ur.mi.fashionapp.edit.piece;

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
import de.ur.mi.fashionapp.util.ImageHelper;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Philip on 29/02/2016.
 */
public class EditPiecePresenter extends MvpBasePresenter<EditPieceView> {

  private Context context;

  public EditPiecePresenter(Context context, EditPieceView view) {
    this.context = context;
    attachView(view);
  }

  public void createPiece(WardrobePieceItem item, boolean pullToRefresh) {
    if (isViewAttached()) {
      ParseObject wr = new ParseObject("Piece");
      wr.put("Name",item.getTitle());
      wr.put("UserID", ParseUser.getCurrentUser().getObjectId());
      wr.put("WardrobeID",item.getWardrobeID());
      wr.put("Category",item.getCat());
      wr.put("Tag1",item.getSeason());
      wr.put("Tag2",item.getOccasion());
      wr.put("Tag3", item.getColor());

      Bitmap bitmap = item.getImage();

      if(bitmap.getHeight()>=500||bitmap.getWidth()>=500) {
        bitmap = Bitmap.createScaledBitmap(bitmap, WardrobePieceItem.MAX_IMAGE_WIDTH, WardrobePieceItem.MAX_IMAGE_HEIGHT, true);
      }
      ByteArrayOutputStream buffer = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer);
      ParseFile file = new ParseFile("pictureOfThisPiece" + ".bmp", buffer.toByteArray());
      // Upload the image into Parse Cloud
      getView().showLoading(pullToRefresh);
      file.saveInBackground(new SaveCallback() {
        @Override public void done(ParseException e) {
          if (e == null) {
            if(isViewAttached())getView().showContent();
          } else {
            if(e.getCode()==ParseException.CONNECTION_FAILED){
              Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();}
            else if(isViewAttached()) getView().showError(e, false);
          }
        }
      });

      wr.put("Image", file);
      getView().showLoading(true);
      wr.saveInBackground(new SaveCallback() {
        @Override public void done(com.parse.ParseException e) {
          if (e == null) {
            if(isViewAttached())getView().showContent();
            if(isViewAttached())getView().onPieceEdited();
          } else {
            if(isViewAttached())getView().showError(e, false);
          }
        }
      });
    }
  }

  public void updatePiece(String itemID, final WardrobePieceItem item, final boolean pullToRefresh) {
    Log.d("itemID",itemID+" = itemID");
    if (isViewAttached()) getView().showLoading(pullToRefresh);
    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Piece");
    query.whereEqualTo("objectId",itemID);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e==null) {
          ParseObject parseObject = objects.get(0);
          parseObject.put("Name",item.getTitle());

          Bitmap bitmap = item.getImage();

          if(bitmap.getHeight()>=500||bitmap.getWidth()>=500) {
            bitmap = Bitmap.createScaledBitmap(bitmap, WardrobePieceItem.MAX_IMAGE_WIDTH, WardrobePieceItem.MAX_IMAGE_HEIGHT, true);
          }
          ByteArrayOutputStream buffer = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());
          bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer);
          ParseFile file = new ParseFile("pictureOfThisPiece" + ".bmp", buffer.toByteArray());

          if(isViewAttached())getView().showLoading(pullToRefresh);
          file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
              if (e == null) {
                if(isViewAttached())getView().showContent();
              } else {
                if(e.getCode()==ParseException.CONNECTION_FAILED){
                  Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();}
                else if(isViewAttached()) getView().showError(e, false);
              }
            }
          });

          parseObject.put("Image", file);
          parseObject.put("Category", item.getCat());
          parseObject.put("Tag1",item.getSeason());
          parseObject.put("Tag2",item.getOccasion());
          parseObject.put("Tag3",item.getColor());
          parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
              if(e==null){
                if(isViewAttached())getView().onPieceEdited();
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

  public void deletePiece(String itemID, final boolean pullToRefresh){
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Piece");
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
          if(e.getCode()==ParseException.CONNECTION_FAILED){
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();}
          else if(isViewAttached()) getView().showError(e, false);
        }
      }
    });
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
            }
            else {
              if (e.getCode() == ParseException.CONNECTION_FAILED) {
                Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
              } else if (isViewAttached()) getView().showError(e, false);
            }
              if (isViewAttached()) {
                getView().onImageLoaded();
                getView().showContent();
              }

          }
        });
      }
    });
  }
}

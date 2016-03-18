package de.ur.mi.fashionapp.edit.piece;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
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
      wr.put("Category",item.getCat());
      wr.put("Tag1",item.getTag1());
      wr.put("Tag2",item.getTag2());
      wr.put("Tag3", item.getTag3());

      Bitmap bitmap = item.getImage();
      ByteArrayOutputStream buffer = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight());
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer);
      ParseFile file = new ParseFile("pictureOfThisPiece" + ".bmp", buffer.toByteArray());
      // Upload the image into Parse Cloud
      getView().showLoading(pullToRefresh);
      file.saveInBackground(new SaveCallback() {
        @Override public void done(ParseException e) {
          if (e == null) {
            getView().showContent();
          } else {
            getView().showError(e, false);
          }
        }
      });

      wr.put("Image", file);
      getView().showLoading(true);
      wr.saveInBackground(new SaveCallback() {
        @Override public void done(com.parse.ParseException e) {
          if (e == null) {
            getView().showContent();
            getView().onPieceEdited();
          } else {
            getView().showError(e, false);
          }
        }
      });
    }
  }

  public void updatePiece(String itemID, final WardrobePieceItem item, final boolean pullToRefresh) {
    if (isViewAttached()) getView().showLoading(pullToRefresh);
    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Piece");
    query.whereEqualTo("objectId",itemID);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e==null) {
          ParseObject parseObject = objects.get(0);
          parseObject.put("Name",item.getTitle());
          if(item.getImage()!=null)parseObject.put("Image",item.getImage());
          parseObject.put("Category", item.getCat());
          parseObject.put("Tag1",item.getTag1());
          parseObject.put("Tag2",item.getTag2());
          parseObject.put("Tag3",item.getTag3());
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
          if (isViewAttached())getView().showError(e,pullToRefresh);
        }
      }
    });
  }
}

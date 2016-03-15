package de.ur.mi.fashionapp.edit.piece;

import android.content.Context;
import android.graphics.Bitmap;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

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

      ParseFile file = new ParseFile("pictureOfThisPiece" + ".bmp",item.getImage());
      // Upload the image into Parse Cloud
      getView().showLoading(true);
      file.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null) {
            getView().showContent();
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
            getView().onPieceEdited();
          } else {
            getView().showError(e, false);
          }
        }
      });
    }
  }

  public void updatePiece(int itemID, WardrobePieceItem item, boolean pullToRefresh) {
    // show loading while uploading
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }

    // TODO: insert upload logic

    if (isViewAttached()) {
      // TODO: show real Error if model loading failed
      if (true) {
        getView().onPieceEdited();
        getView().showContent();
      } else {
        Throwable e = new Exception();
        getView().showError(e, pullToRefresh);
      }
    }
  }
}

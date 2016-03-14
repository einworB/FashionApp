package de.ur.mi.fashionapp.edit.piece;

import android.content.Context;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
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
      String pieceName = item.getTitle();
      ParseObject wr = new ParseObject("Piece");
      wr.put("Name", pieceName);
      wr.put("UserID", ParseUser.getCurrentUser().getObjectId());
      getView().showLoading(true);
      wr.saveInBackground(new SaveCallback() {
        @Override public void done(com.parse.ParseException e) {
          if (e == null) {
            getView().showLoading(false);
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

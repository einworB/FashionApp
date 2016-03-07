package de.ur.mi.fashionapp.edit.piece;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import de.ur.mi.fashionapp.edit.model.EditPieceItem;

/**
 * Created by Philip on 29/02/2016.
 */
public class EditPiecePresenter extends MvpBasePresenter<EditPieceView>{

  // TODO: maybe implement a "prepareNewPiece" function which returns a itemID?


  public void createPiece(EditPieceItem item, boolean pullToRefresh) {
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
      }
      else {
        Throwable e = new Exception();
        getView().showError(e, pullToRefresh);
      }
    }
  }

  public void updateOutfit(int itemID, EditPieceItem item, boolean pullToRefresh) {
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
      }
      else {
        Throwable e = new Exception();
        getView().showError(e, pullToRefresh);
      }
    }
  }
}
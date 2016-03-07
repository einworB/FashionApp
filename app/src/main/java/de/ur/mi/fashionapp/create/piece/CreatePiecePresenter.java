package de.ur.mi.fashionapp.create.piece;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import de.ur.mi.fashionapp.create.model.NewPieceItem;

/**
 * Created by Philip on 29/02/2016.
 */
public class CreatePiecePresenter extends MvpBasePresenter<CreatePieceView>{

  // TODO: maybe implement a "prepareNewPiece" function which returns a itemID?


  public void createPiece(int userID, NewPieceItem item, boolean pullToRefresh) {
    // show loading while uploading
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }

    // TODO: insert upload logic


    if (isViewAttached()) {
      // TODO: show real Error if model loading failed
      if (true) {
        getView().onPieceCreated();
        getView().showContent();
      }
      else {
        Throwable e = new Exception();
        getView().showError(e, pullToRefresh);
      }
    }
  }
}

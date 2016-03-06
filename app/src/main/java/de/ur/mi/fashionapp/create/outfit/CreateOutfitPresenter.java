package de.ur.mi.fashionapp.create.outfit;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import de.ur.mi.fashionapp.create.model.NewOutfitItem;

/**
 * Created by Philip on 29/02/2016.
 */
public class CreateOutfitPresenter extends MvpBasePresenter<CreateOutfitView>{

  // TODO: maybe implement a "prepareNewOutfit" function which returns a itemID?

  public void createOutfit(int userID, NewOutfitItem item, boolean pullToRefresh) {
    // show loading while uploading
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }

    // TODO: insert upload logic


    if (isViewAttached()) {
      // TODO: show real Error if model loading failed
      if (true) {
        getView().onOutfitCreated();
        getView().showContent();
      }
      else {
        Throwable e = new Exception();
        getView().showError(e, pullToRefresh);
      }
    }
  }
}

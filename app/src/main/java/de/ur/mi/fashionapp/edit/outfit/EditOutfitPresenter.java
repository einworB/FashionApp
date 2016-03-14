package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;

/**
 * Created by Philip on 29/02/2016.
 */
public class EditOutfitPresenter extends MvpBasePresenter<EditOutfitView>{

  private Context context;

  public EditOutfitPresenter(Context context, EditOutfitView view) {
    this.context = context;
    attachView(view);
  }

  // TODO: maybe implement a "prepareNewOutfit" function which returns a itemID?

  public void createOutfit(WardrobeOutfitItem item, boolean pullToRefresh) {
    // show loading while uploading
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }

    // TODO: insert upload logic


    if (isViewAttached()) {
      // TODO: show real Error if model loading failed
      if (true) {
        getView().onOutfitEdited();
        getView().showContent();
      }
      else {
        Throwable e = new Exception();
        getView().showError(e, pullToRefresh);
      }
    }
  }

  public void updateOutfit(int itemID, WardrobeOutfitItem item, boolean pullToRefresh) {
    // show loading while uploading
    if (isViewAttached()) {
      getView().showLoading(pullToRefresh);
    }

    // TODO: insert upload logic


    if (isViewAttached()) {
      // TODO: show real Error if model loading failed
      if (true) {
        getView().onOutfitEdited();
        getView().showContent();
      }
      else {
        Throwable e = new Exception();
        getView().showError(e, pullToRefresh);
      }
    }
  }
}

package de.ur.mi.fashionapp.create.outfit;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.create.model.NewOutfitItem;

/**
 * Created by Philip on 05/03/2016.
 */
public class CreateOutfitActivity extends CBActivityMvpToolbar<LinearLayout, Object, CreateOutfitView, CreateOutfitPresenter> implements CreateOutfitView {

  private int userID;
  private NewOutfitItem newItem;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    // TODO: create layout; register createOutfit() method on create Button click listener
    // TODO: get userID from sharedPrefernces or pass it from Wardrobe
  }

  @NonNull @Override public CreateOutfitPresenter createPresenter() {
    return new CreateOutfitPresenter();
  }

  @Override public void setData(Object data) {
    // not needed
  }

  @Override public void loadData(boolean pullToRefresh) {
    // not needed
    // TODO: maybe needed for a "prepareNewOutfit" function which returns a itemID?
  }

  @Override protected Integer getLayoutRes() {
    return R.layout.activity_create_piece;
  }

  @Override public void onOutfitCreated() {
    finish();
  }

  private void createOutfit() {
    // TODO: get data from EditTexts for the new NewOutfitItem(userID, itemID, title)
    presenter.createOutfit(userID, newItem, true);
  }
}

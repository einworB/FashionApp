package de.ur.mi.fashionapp.edit.outfit;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.edit.model.EditOutfitItem;

/**
 * Created by Philip on 05/03/2016.
 */
public class EditOutfitActivity extends CBActivityMvpToolbar<LinearLayout, Object, EditOutfitView, EditOutfitPresenter> implements
    EditOutfitView {

  public static final String KEY_ID = "itemID";

  private EditOutfitItem editItem;
  private int editItemID;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    editItemID = getIntent().getIntExtra(KEY_ID, 0);
    // TODO: get parcelable item from bundle if editItemID != 0
    // TODO: create layout; register if(editItemID == 0) createOutfit() else updateOutfit() to on save Button click listener
  }

  @Override protected void onMvpViewCreated() {
    super.onMvpViewCreated();
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (editItemID != 0) {
      getSupportActionBar().setDisplayShowTitleEnabled(true);
      getSupportActionBar().setTitle("Edit Item "+editItemID);
    }
  }

  @NonNull @Override public EditOutfitPresenter createPresenter() {
    return new EditOutfitPresenter();
  }

  @Override public void setData(Object data) {
    // not needed
  }

  @Override public void loadData(boolean pullToRefresh) {
    // not needed
    // TODO: maybe needed for a "prepareNewOutfit" function which returns a itemID?
  }

  @Override protected Integer getLayoutRes() {
    return R.layout.activity_edit_piece;
  }

  @Override public void onOutfitEdited() {
    finish();
  }

  private void createOutfit() {
    // TODO: get data from EditTexts for the new EditOutfitItem(editItem, title)
    presenter.createOutfit(editItem, true);
  }

  private void updateOutfit() {
    // TODO: get data from EditTexts for the updated EditOutfitItem(editItemID, editItem, title)
    presenter.updateOutfit(editItemID, editItem, true);
  }
}

package de.ur.mi.fashionapp.edit.piece;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.edit.model.EditPieceItem;

/**
 * Created by Philip on 05/03/2016.
 */
public class EditPieceActivity extends CBActivityMvpToolbar<LinearLayout, Object, EditPieceView, EditPiecePresenter> implements
    EditPieceView {

  public static final String KEY_ID = "itemID";

  private EditPieceItem editItem;
  private int editItemID;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    editItemID = getIntent().getIntExtra(KEY_ID, 0);
    // TODO: get parcelable item from bundle if editItemID != 0
    // TODO: create layout; register if(editItemID == 0) createPiece() else updatePiece to on create Button click listener
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

  @NonNull @Override public EditPiecePresenter createPresenter() {
    return new EditPiecePresenter();
  }

  @Override public void setData(Object data) {
    // not needed
  }

  @Override public void loadData(boolean pullToRefresh) {
    // not needed
  }

  @Override protected Integer getLayoutRes() {
    return R.layout.activity_edit_piece;
  }

  @Override public void onPieceEdited() {
    finish();
  }

  private void createPiece() {
    // TODO: get data from EditTexts for the new EditPieceItem(editItem, title)
    presenter.createPiece(editItem, true);
  }

  private void updatePiece() {
    // TODO: get data from EditTexts for the updated EditPieceItem(editItemID, editItem, title)
    presenter.updateOutfit(editItemID, editItem, true);
  }
}

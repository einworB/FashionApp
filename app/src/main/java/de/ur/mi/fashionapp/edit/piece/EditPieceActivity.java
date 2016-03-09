package de.ur.mi.fashionapp.edit.piece;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.edit.model.EditPieceItem;
import de.ur.mi.fashionapp.util.ImageSlider;
import de.ur.mi.fashionapp.util.ImageSliderController;

/**
 * Created by Philip on 05/03/2016.
 */
public class EditPieceActivity extends CBActivityMvpToolbar<LinearLayout, Object, EditPieceView, EditPiecePresenter> implements
    EditPieceView, ImageSlider.ImageSliderListener {

  public static final String KEY_ID = "itemID";

  private EditPieceItem editItem;
  private int editItemID;

  private View seasonContainer;
  private View categoryContainer;
  private View colorContainer;
  private View occasionContainer;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);

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
    editItemID = getIntent().getIntExtra(KEY_ID, 0);

    seasonContainer = findViewById(R.id.edit_piece_season_container);
    categoryContainer = findViewById(R.id.edit_piece_category_container);
    colorContainer = findViewById(R.id.edit_piece_color_container);
    occasionContainer = findViewById(R.id.edit_piece_occasion_container);

    ImageSliderController sliderController = new ImageSliderController(this, this);
    sliderController.addSlider(seasonContainer, false);
    sliderController.addSlider(categoryContainer, false);
    sliderController.addSlider(colorContainer, true);
    sliderController.addSlider(occasionContainer, true);
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
    presenter.updatePiece(editItemID, editItem, true);
  }

  @Override public void onImageSelected(View root, int id) {
    // TODO: set the selected season/occasion/color/category to the piece
    if (root == seasonContainer) {
      Log.d("EDITPIECE", "Selected season #"+(id+1));
    }
    else if (root == categoryContainer) {
      Log.d("EDITPIECE", "Selected category #"+(id+1));
    }
    else if (root == occasionContainer) {
      Log.d("EDITPIECE", "Selected occasion #"+(id+1));
    }
    else if (root == colorContainer) {
      Log.d("EDITPIECE", "Selected color #"+(id+1));
    }
  }
}

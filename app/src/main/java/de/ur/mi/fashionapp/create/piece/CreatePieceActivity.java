package de.ur.mi.fashionapp.create.piece;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.create.model.NewPieceItem;

/**
 * Created by Philip on 05/03/2016.
 */
public class CreatePieceActivity extends CBActivityMvpToolbar<LinearLayout, Object, CreatePieceView, CreatePiecePresenter> implements CreatePieceView {

  private NewPieceItem newItem;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    // TODO: create layout; register createPiece() method on create Button click listener
  }

  @NonNull @Override public CreatePiecePresenter createPresenter() {
    return new CreatePiecePresenter();
  }

  @Override public void setData(Object data) {
    // not needed
  }

  @Override public void loadData(boolean pullToRefresh) {
    // not needed
  }

  @Override protected Integer getLayoutRes() {
    return R.layout.activity_create_piece;
  }

  @Override public void onPieceCreated() {
    finish();
  }

  private void createOutfit() {
    // TODO: get data from EditTexts for the new NewPieceItem(newItem, title)
    presenter.createPiece(newItem, true);
  }
}

package de.ur.mi.fashionapp.edit.piece;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * Created by Philip on 29/02/2016.
 *
 * This interface has to be implemented by the editpiece activitiy. The presenter connects
 * to the
 * activity via this interface.
 */
public interface EditPieceView extends MvpLceView<Object> {
  void onPieceEdited();

  void onImageLoaded();
}

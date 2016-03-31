package de.ur.mi.fashionapp.detail;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * Created by Philip on 17/03/2016.
 *
 * This interface has to be implemented by the details activities. The presenter connects to the
 * activities via this interface.
 */
public interface DetailView extends MvpLceView<Object> {
  void onImageLoaded(String pieceID);
}

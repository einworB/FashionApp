package de.ur.mi.fashionapp.detail;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * Created by Philip on 17/03/2016.
 */
public interface DetailView extends MvpLceView<Object> {
  void onImageLoaded(String pieceID);
}

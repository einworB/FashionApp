package de.ur.mi.fashionapp.edit.outfit;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

/**
 * Created by Philip on 29/02/2016.
 */
public interface EditOutfitView extends MvpLceView<Object>{
  void onOutfitEdited();
  void onImageLoaded(String id);
}

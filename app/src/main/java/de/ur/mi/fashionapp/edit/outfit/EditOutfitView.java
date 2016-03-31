package de.ur.mi.fashionapp.edit.outfit;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

/**
 * Created by Philip on 29/02/2016.
 *
 * This interface has to be implemented by the editoutfit activitiy. The presenter connects
 * to the
 * activity via this interface.
 */
public interface EditOutfitView extends MvpLceView<List<WardrobePieceItem>> {
  void onOutfitEdited();

  void onImageLoaded(String id);
}

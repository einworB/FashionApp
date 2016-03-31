package de.ur.mi.fashionapp.edit.outfit.pickpieces;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;
import java.util.List;

/**
 * Created by Jana on 22.03.2016.
 *
 * This interface has to be implemented by the pickoutfit pieces activitiy. The presenter connects
 * to the
 * activity via this interface.
 */
public interface PickOutfitPiecesView extends MvpLceView<List<WardrobePieceItem>> {
  void onImageLoaded(String itemID);
}

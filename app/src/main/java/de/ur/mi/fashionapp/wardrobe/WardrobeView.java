package de.ur.mi.fashionapp.wardrobe;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import de.ur.mi.fashionapp.model.WardrobeItem;
import java.util.List;

/**
 * Created by Philip on 29/02/2016.
 *
 * This interface has to be implemented by the wardrobe fragments. The presenter connects to the
 * fragments via this interface.
 */
public interface WardrobeView extends MvpLceView<List<WardrobeItem>> {
  void onImageLoaded(String itemID);
}

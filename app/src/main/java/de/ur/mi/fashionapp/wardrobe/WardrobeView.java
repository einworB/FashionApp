package de.ur.mi.fashionapp.wardrobe;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import java.util.List;

/**
 * Created by Philip on 29/02/2016.
 */
public interface WardrobeView extends MvpLceView<List<WardrobeItem>>{
  void onImageLoaded(String itemID);
}

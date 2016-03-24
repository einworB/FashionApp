package de.ur.mi.fashionapp.util;

import android.content.Context;
import android.view.View;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 09/03/2016.
 */
public class ImageSliderController {

  public static final String SLIDER_TYPE_CATEGORY = "category";
  public static final String SLIDER_TYPE_OCCASION = "occasion";
  public static final String SLIDER_TYPE_SEASON = "season";

  private Context context;
  private ImageSlider.ImageSliderListener listener;
  private List<ImageSlider> sliders;

  public ImageSliderController(Context context, ImageSlider.ImageSliderListener listener) {
    this.context = context;
    this.listener = listener;
    sliders = new ArrayList<>();
  }

  public void addSlider(View root, boolean reverse, int length, String sliderType) {
    sliders.add(new ImageSlider(context, root, listener, reverse, this, length, sliderType));
  }

  public void onSliderOpen(ImageSlider sliderToOpen) {
    for (ImageSlider s : sliders) {
      if (s != sliderToOpen && s.isSliderOpen()) {
        s.closeSlider();
      }
    }
  }

  public void prefill(WardrobePieceItem item) {
    for (ImageSlider s : sliders) {
      s.preset(item);
    }
  }
}

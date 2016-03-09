package de.ur.mi.fashionapp.util;

import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 09/03/2016.
 */
public class ImageSliderController {

  private Context context;
  private ImageSlider.ImageSliderListener listener;
  private List<ImageSlider> sliders;

  public ImageSliderController(Context context, ImageSlider.ImageSliderListener listener) {
    this.context = context;
    this.listener = listener;
    sliders = new ArrayList<>();
  }

  public void addSlider(View root, boolean reverse) {
    sliders.add(new ImageSlider(context, root, listener, reverse, this));
  }

  public void onSliderOpen(ImageSlider sliderToOpen) {
    for (ImageSlider s : sliders) {
      if (s != sliderToOpen && s.isSliderOpen()) {
        s.closeSlider();
      }
    }
  }
}

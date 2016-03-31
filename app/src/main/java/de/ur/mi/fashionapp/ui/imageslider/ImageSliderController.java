package de.ur.mi.fashionapp.ui.imageslider;

import android.content.Context;
import android.view.View;
import de.ur.mi.fashionapp.model.WardrobePieceItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 09/03/2016.
 *
 * This class creates and controls image sliders.
 * if one of the sliders controlled by the controller is clicked, the controller causes all other
 * open sliders to close. the controller is also used to preset the sliders for example if an
 * existing WardrobePieceItem is edited in the EditPieceActivity.
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

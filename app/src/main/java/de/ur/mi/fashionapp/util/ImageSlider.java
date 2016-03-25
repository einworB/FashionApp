package de.ur.mi.fashionapp.util;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 09/03/2016.
 */
public class ImageSlider {

  private View root;
  private ImageView selector;
  private List<ImageView> icons;

  private ObjectAnimator slideLeft, slideRight;
  private boolean slideIn = true;

  private Context context;
  private ImageSliderListener listener;
  private boolean reverse;
  private ImageSliderController controller;
  private int length;
  private int maxTranslation;

  private String sliderType;

  private List<Integer> itemIndex;

  public interface ImageSliderListener {
    void onImageSelected(View root, int id);
  }

  public ImageSlider(Context context, View root, ImageSliderListener listener, boolean reverse,
      ImageSliderController controller, int length, String sliderType) {
    this.context = context;
    this.root = root;
    this.listener = listener;
    this.reverse = reverse;
    this.controller = controller;
    this.length = length;
    this.sliderType = sliderType;
    maxTranslation = (length - 1) * 45;
    init();
    bindView();
  }

  private void init() {
    icons = new ArrayList<>(length - 1);
    itemIndex = new ArrayList<>(length);
    //selector
    selector = (ImageView) root.findViewWithTag("selector");
    itemIndex.add(0);
    //icons
    for (int i = 1; i < length; i++) {
      icons.add((ImageView) root.findViewWithTag("icon" + i));
      itemIndex.add(i);
    }

    if (reverse) {
      slideLeft =
          ObjectAnimator.ofFloat(root, "translationX", 0, -dpToPixel(context, maxTranslation));
    } else {
      slideLeft =
          ObjectAnimator.ofFloat(root, "translationX", dpToPixel(context, maxTranslation), 0);
    }
    slideLeft.setDuration(500);
    slideLeft.setInterpolator(new ImageSliderInterpolator());

    if (reverse) {
      slideRight =
          ObjectAnimator.ofFloat(root, "translationX", -dpToPixel(context, maxTranslation), 0);
    } else {
      slideRight =
          ObjectAnimator.ofFloat(root, "translationX", 0, dpToPixel(context, maxTranslation));
    }
    slideRight.setDuration(500);
    slideRight.setInterpolator(new ImageSliderInterpolator());
  }

  private void bindView() {
    selector.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        animateContainer();
      }
    });

    if (listener != null) {
      for (final ImageView icon : icons) {
        icon.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            animateContainer();
            String tag = icon.getTag().toString();
            if (tag != null) {
              //swap indizes and drawables
              int iconNum = Integer.parseInt(tag.substring(4));
              int temp = itemIndex.get(iconNum);
              itemIndex.set(iconNum, itemIndex.get(0));
              itemIndex.set(0, temp);
              Drawable tempDrawable = icon.getDrawable();
              icon.setImageDrawable(selector.getDrawable());
              selector.setImageDrawable(tempDrawable);
              listener.onImageSelected(root, temp);
              Log.d("IMAGESLIDER", "item number " + temp + " selected in " + root.toString());
            }
          }
        });
      }
    }
  }

  private void animateContainer() {
    if (slideIn) {
      controller.onSliderOpen(this);
      if (reverse) {
        slideLeft.start();
      } else {
        slideRight.start();
      }
    } else {
      if (reverse) {
        slideRight.start();
      } else {
        slideLeft.start();
      }
    }

    slideIn = !slideIn;
  }

  public int dpToPixel(Context context, int dp) {
    return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
  }

  private class ImageSliderInterpolator implements Interpolator {

    @Override public float getInterpolation(float x) {
      return (float) (6 * Math.pow(x, 2) - 8 * Math.pow(x, 3) + 3 * Math.pow(x, 4));
    }
  }

  public boolean isSliderOpen() {
    return !slideIn;
  }

  public void closeSlider() {
    animateContainer();
  }

  public void preset(WardrobePieceItem item) {
    int id = -1;
    switch (sliderType) {
      case ImageSliderController.SLIDER_TYPE_SEASON:
        id = item.getSeason();
        break;
      case ImageSliderController.SLIDER_TYPE_OCCASION:
        id = item.getOccasion();
        break;
      case ImageSliderController.SLIDER_TYPE_CATEGORY:
        id = item.getCat();
        break;
    }
    if (id > 0) {
      // id 0 means we do not have to prefill as it is the default selector
      ImageView icon = (ImageView) root.findViewWithTag("icon" + id);
      Drawable tempDrawable = icon.getDrawable();
      icon.setImageDrawable(selector.getDrawable());
      selector.setImageDrawable(tempDrawable);
      int temp = itemIndex.get(id);
      itemIndex.set(id, itemIndex.get(0));
      itemIndex.set(0, temp);
    }
  }
}

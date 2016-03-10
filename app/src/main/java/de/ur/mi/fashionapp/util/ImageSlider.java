package de.ur.mi.fashionapp.util;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;

/**
 * Created by Philip on 09/03/2016.
 */
public class ImageSlider {

  private View root;
  private ImageView icon1, icon2, icon3, icon4, selector;

  private ObjectAnimator slideLeft, slideRight, slideReset;
  private boolean slideIn = true;

  private Context context;
  private ImageSliderListener listener;
  private boolean reverse;
  private ImageSliderController controller;

  public interface ImageSliderListener {
    void onImageSelected(View root, int id);
  }

  public ImageSlider(Context context, View root, ImageSliderListener listener, boolean reverse, ImageSliderController controller) {
    this.context = context;
    this.root = root;
    this.listener = listener;
    this.reverse = reverse;
    this.controller = controller;
    init();
    bindView();
  }

  private void init() {
    icon1 = (ImageView) root.findViewWithTag("icon1");
    icon2 = (ImageView) root.findViewWithTag("icon2");
    icon3 = (ImageView) root.findViewWithTag("icon3");
    icon4 = (ImageView) root.findViewWithTag("icon4");
    selector = (ImageView) root.findViewWithTag("selector");

    if (reverse) {
      slideLeft = ObjectAnimator.ofFloat(root, "translationX", 0, -dpToPixel(context, 180));
    }
    else {
      slideLeft = ObjectAnimator.ofFloat(root, "translationX", dpToPixel(context, 180), 0);
    }
    slideLeft.setDuration(500);
    slideLeft.setInterpolator(new ImageSliderInterpolator());

    if (reverse) {
      slideRight = ObjectAnimator.ofFloat(root, "translationX", -dpToPixel(context, 180), 0);
    }
    else {
      slideRight = ObjectAnimator.ofFloat(root, "translationX", 0, dpToPixel(context, 180));
    }
    slideRight.setDuration(500);
    slideRight.setInterpolator(new ImageSliderInterpolator());

    //slideReset = ObjectAnimator.ofFloat(root, "translationX",
    //    -dpToPixel(context, 120), 0);
    //slideReset.setDuration(1);
    //slideReset.setInterpolator(new ImageSliderInterpolator());

  }

  private void bindView() {
    //resetAnimation();

    selector.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        animateContainer();
      }
    });

    if (listener != null) {
      icon1.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          animateContainer();
          listener.onImageSelected(root, 0);
          selector.setImageDrawable(icon1.getDrawable());
        }
      });
      icon2.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          animateContainer();
          listener.onImageSelected(root, 1);
          selector.setImageDrawable(icon2.getDrawable());
        }
      });
      icon3.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          animateContainer();
          listener.onImageSelected(root, 2);
          selector.setImageDrawable(icon3.getDrawable());
        }
      });
      icon4.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          animateContainer();
          listener.onImageSelected(root, 3);
          selector.setImageDrawable(icon4.getDrawable());
        }
      });
    }
  }

  private void animateContainer() {
    if (slideIn) {
      controller.onSliderOpen(this);
      if (reverse) {
        slideLeft.start();
      }
      else {
        slideRight.start();
      }
    } else {
      if (reverse) {
        slideRight.start();
      }
      else {
        slideLeft.start();
      }
    }

    slideIn = !slideIn;
  }

  //private void resetAnimation() {
  //  if (!slideIn) {
  //    slideReset.start();
  //    slideIn = !slideIn;
  //  }
  //}

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
}

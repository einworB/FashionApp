package de.ur.mi.fashionapp;

import android.os.Handler;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by Philip on 24/02/2016.
 */
public class LoginPresenter extends MvpBasePresenter<LoginView> {

  public void loadObject() {
    if (isViewAttached()) {
      getView().showLoading(false);
    }
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        if (isViewAttached()) {
          getView().setData("test");
          getView().showContent();
        }
      }
    }, 5000);
  }

  public void login(String name, String password) {
    // TODO: parse logic here!

    // TODO wichtig: asynchron, bei success:
    if (isViewAttached()) {
      getView().setData(/*Parse Ergebnis Object*/"");
    }
  }
}


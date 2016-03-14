package de.ur.mi.fashionapp.login;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * Created by Philip on 24/02/2016.
 */
public interface LoginView extends MvpLceView<String> {

  void onLoginSuccess();
  void onRegisterSuccess();
  void onPasswordResetSuccess();
}

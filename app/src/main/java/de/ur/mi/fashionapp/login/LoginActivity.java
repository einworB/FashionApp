package de.ur.mi.fashionapp.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import android.widget.Toast;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.CBActivityMvpFragment;
import de.ur.mi.fashionapp.wardrobe.WardrobeActivity;

/**
 * this activity uses fragments to display the required screens for login and registering.
 * the actual registering and login is handled by the presenter
 * after successful login or registering the wardrobe activity is opened.
 */
public class LoginActivity
    extends CBActivityMvpFragment<LinearLayout, String, LoginView, LoginPresenter>
    implements LoginView {

  private static Context context;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    setContext(getApplicationContext());
  }

  @Override protected Fragment createFragmentToDisplay() {
    return new LoginFragment();
  }

  @NonNull @Override public LoginPresenter createPresenter() {
    return new LoginPresenter(this, this);
  }

  @Override public void setData(String data) {
    // not needed
  }

  @Override public void loadData(boolean pullToRefresh) {
    // not needed
  }

  @Override public void onLoginSuccess() {
    Intent intent = new Intent(this, WardrobeActivity.class);
    startActivity(intent);
  }

  @Override public void onRegisterSuccess() {
    Intent intent = new Intent(this, WardrobeActivity.class);
    startActivity(intent);
  }

  public void performLogin(String username, String password) {
    presenter.login(username, password);
  }

  public void performRegister(String username, String password, String email) {
    presenter.register(username, email, password);
  }

  public void openRegisterFragment() {
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
        R.anim.slide_left_in, R.anim.slide_right_out);
    transaction.replace(R.id.contentView, new RegisterFragment());
    transaction.addToBackStack(null);
    transaction.commit();
  }

  @Override public void onPasswordResetSuccess() {
    Toast.makeText(this,
        "Password reset successful. Please check the email account you specified on registering!",
        Toast.LENGTH_LONG).show();
  }

  @Override protected void onErrorViewClicked() {
    showContent();
  }

  void resetPassword(String userName) {
    presenter.resetPassword(userName);
  }

  //getter for the current context
  public static Context getContext() {
    return context;
  }

  public static void setContext(Context con) {
    context = con;
  }
}

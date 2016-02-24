package de.ur.mi.fashionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbarFragment;

public class MainActivity extends CBActivityMvpToolbarFragment<LinearLayout, String, LoginView, LoginPresenter>
    implements LoginView{

  LoginAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setTitle("Login");
    //adapter = new LoginAdapter(this);
    //contentView.setLayoutManager(new LinearLayoutManager(this));
    //contentView.setAdapter(adapter);
  }

  @Override protected Fragment createFragmentToDisplay() {
    return new LoginFragment();
  }

  @NonNull @Override public LoginPresenter createPresenter() {
    return new LoginPresenter();
  }

  @Override public void setData(String data) {
    //adapter.setData(data);
    //adapter.notifyDataSetChanged();
  }

  @Override public void loadData(boolean pullToRefresh) {
    //presenter.loadObject();
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
    //presenter.login(username, password);
  }

  public void performRegister(String username, String email, String password) {
    //presenter.register(username, email, password);
  }

  public void openRegisterFragment(){
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
    transaction.replace(R.id.contentView, new RegisterFragment());
    transaction.commit();
  }
}

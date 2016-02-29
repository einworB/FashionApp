package de.ur.mi.fashionapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.christianbahl.appkit.core.activity.CBActivityMvpToolbarFragment;
import com.parse.Parse;

public class MainActivity extends CBActivityMvpToolbarFragment<LinearLayout, String, LoginView, LoginPresenter>
    implements LoginView{

  LoginAdapter adapter;
  private static Context context;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setContext(getApplicationContext());
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setTitle("Login");
    //adapter = new LoginAdapter(this);
    //contentView.setLayoutManager(new LinearLayoutManager(this));
    //contentView.setAdapter(adapter);

    //Parse initialization
    Parse.enableLocalDatastore(this);
    Parse.initialize(this,"TWxq1vLfTKthoZe7ZT2QaWd3EVjMFB4GN2QEjfkf","GqctpYG2rJTGmf9vQxdRfG582D8dg01i1PnbBadS");

  }

  @Override protected Fragment createFragmentToDisplay() {
    return new LoginFragment();
  }

  @NonNull @Override public LoginPresenter createPresenter() {
    return new LoginPresenter(this);
  }

  @Override public void setData(String data) {
    //adapter.setData(data);
    //adapter.notifyDataSetChanged();
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

  public void performRegister(String username, String email, String password) {
    presenter.register(username, email, password);
  }

  public void openRegisterFragment(){
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
    transaction.replace(R.id.contentView, new RegisterFragment());
    transaction.commit();
  }

  //getter for the current context
  public static Context getContext(){
    return context;
  }
    public static void setContext(Context con) {
        context = con;
    }
}

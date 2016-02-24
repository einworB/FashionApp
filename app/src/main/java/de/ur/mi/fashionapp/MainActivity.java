package de.ur.mi.fashionapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;

public class MainActivity extends CBActivityMvpToolbar<RecyclerView, String, LoginView, LoginPresenter> implements LoginView{

  LoginAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    adapter = new LoginAdapter(this);
    contentView.setAdapter(adapter);
  }

  @NonNull @Override public LoginPresenter createPresenter() {
    return new LoginPresenter();
  }

  @Override public void setData(String data) {
    adapter.setData(data);
    adapter.notifyDataSetChanged();
  }

  @Override public void loadData(boolean pullToRefresh) {
    presenter.loadObject();
  }
}

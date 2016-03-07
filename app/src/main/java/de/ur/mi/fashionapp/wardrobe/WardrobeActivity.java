package de.ur.mi.fashionapp.wardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.astuetz.PagerSlidingTabStrip;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbarTabs;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.wardrobe.menu.WardrobeMenuAdapter;
import de.ur.mi.fashionapp.wardrobe.menu.WardrobeMenuPresenter;
import de.ur.mi.fashionapp.wardrobe.menu.WardrobeMenuView;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuItem;
import java.util.List;

/**
 * Created by Philip on 24/02/2016.
 */
public class WardrobeActivity extends
    CBActivityMvpToolbarTabs<List<WardrobeMenuItem>, WardrobeMenuView, WardrobeMenuPresenter, WardrobePagerAdapter>
    implements WardrobeMenuAdapter.WardrobeMenuAdapterListener, WardrobeMenuView {

  private WardrobeMenuAdapter menuAdapter;
  private RecyclerView menuRecyclerView;
  private FragmentManager fragmentManager;
  private FloatingActionButton fab;
  private DrawerLayout drawerLayout;
  private PagerSlidingTabStrip tabs;
  private WardrobePagerAdapter adapter;
  private ActionBar actionBar;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
  }

  @Override protected void onMvpViewCreated() {
    fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Fragment f = fragmentManager.findFragmentByTag(
            "android:switcher:" + contentView.getId() + ":" + contentView.getCurrentItem());
        if (f instanceof WardrobeFragment) {
          Intent i = LinkService.getCreateIntent(WardrobeActivity.this, ((WardrobeFragment) f).getType());
          startActivity(i);
        }

      }
    });
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
    if (tabs == null) {
      throw new NullPointerException(
          "No tabs found. Did you forget to add it to your layout file with the id R.id.tabs?");
    }

    if (adapter == null) {
      adapter = createAdapter();
    }
    if (adapter == null) {
      throw new NullPointerException(
          "No adapter found. Did you forget to create one in createAdapter()?");
    }

    contentView.setAdapter(adapter);
    tabs.setViewPager(contentView);

    actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayShowTitleEnabled(true);
    }

    int margin = Math.max(getPageMargin(), 0);

    if (margin > 0) {
      contentView.setPageMargin(margin);

      Integer pageMarginDrawable = getViewPagerDividerDrawable();
      if (pageMarginDrawable != null) {
        contentView.setPageMarginDrawable(pageMarginDrawable);
      }
    }
  }

  @NonNull @Override public WardrobeMenuPresenter createPresenter() {
    return new WardrobeMenuPresenter();
  }

  @Override public void setData(List<WardrobeMenuItem> data) {
    if (data != null && !data.isEmpty()) {
      menuAdapter.setItems(data);
      menuAdapter.notifyDataSetChanged();
    }
  }

  @Override public void loadData(boolean pullToRefresh) {
    presenter.loadMenu();
  }

  @Override protected Integer getLayoutRes() {
    return R.layout.activity_wardrobe;
  }

  @Override protected WardrobePagerAdapter createAdapter() {
    fragmentManager = getSupportFragmentManager();
    menuAdapter = new WardrobeMenuAdapter(this, this);
    menuRecyclerView = (RecyclerView) findViewById(R.id.drawer_recycler_view);
    menuRecyclerView.setAdapter(menuAdapter);
    menuRecyclerView.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    return new WardrobePagerAdapter(fragmentManager);
  }

  @Override public void onWardrobeClicked(int ID, String title) {
    for (Fragment f : fragmentManager.getFragments()) {
      if (f instanceof WardrobeFragment) {
        ((WardrobeFragment) f).setWardrobe(ID);
      }
    }
    if (actionBar != null) {
      actionBar.setTitle(title);
    }
    drawerLayout.closeDrawers();
  }

  @Override public void onNewWardrobeClicked() {
    // TODO: create new Wardrobe here
  }

  @Override public void onLinkClicked(String title) {
    Intent intent = LinkService.getLink(this, title);
    startActivity(intent);
  }
}

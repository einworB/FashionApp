package de.ur.mi.fashionapp.wardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.astuetz.PagerSlidingTabStrip;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbarTabs;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeMenuItem;
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
  private PagerSlidingTabStrip tabs;
  private WardrobePagerAdapter adapter;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    setTitle("Mein Kleiderschrank");
  }

  @Override protected void onMvpViewCreated() {
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

  @Override public void onWardrobeClicked(int ID) {
    for (Fragment f : fragmentManager.getFragments()) {
      if (f instanceof WardrobeFragment) {
        ((WardrobeFragment) f).setWardrobe(ID);
      }
    }
  }

  @Override public void onLinkClicked(String title) {
    Intent intent = LinkService.getLink(this, title);
    startActivity(intent);
  }
}

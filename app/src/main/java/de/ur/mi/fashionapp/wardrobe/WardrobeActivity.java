package de.ur.mi.fashionapp.wardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.astuetz.PagerSlidingTabStrip;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbarTabs;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.settings.SettingsActivity;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.wardrobe.menu.WardrobeMenuAdapter;
import de.ur.mi.fashionapp.wardrobe.menu.WardrobeMenuPresenter;
import de.ur.mi.fashionapp.wardrobe.menu.WardrobeMenuView;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuItem;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 24/02/2016.
 */
public class WardrobeActivity extends
    CBActivityMvpToolbarTabs<List<WardrobeMenuItem>, WardrobeMenuView, WardrobeMenuPresenter, WardrobePagerAdapter>
    implements WardrobeMenuAdapter.WardrobeMenuAdapterListener, WardrobeMenuView {

  static int REQUESTCODE_CREATE = 101;
  public static int REQUESTCODE_SETTINGS = 102;
  private WardrobeMenuAdapter menuAdapter;
  private RecyclerView menuRecyclerView;
  private FragmentManager fragmentManager;
  private FloatingActionButton fab;
  private DrawerLayout drawerLayout;
  private PagerSlidingTabStrip tabs;
  private WardrobePagerAdapter adapter;
  private ActionBarDrawerToggle drawerToggle;

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
          Intent i =
              LinkService.getCreateIntent(WardrobeActivity.this, ((WardrobeFragment) f).getType());
          startActivityForResult(i, REQUESTCODE_CREATE);
        }
      }
    });
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_menu);
    setSupportActionBar(toolbar);

    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
        R.string.drawer_close);

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
    return new WardrobeMenuPresenter(this, this);
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

  @Override public void onWardrobeClicked(String ID, String title) {
    for (Fragment f : fragmentManager.getFragments()) {
      if (f instanceof WardrobeFragment) {
        ((WardrobeFragment) f).setWardrobe(ID);
      }
    }
    toolbar.setTitle(title);
    drawerLayout.closeDrawers();
  }

  @Override public void onNewWardrobeClicked() {
    presenter.addNewWardrobe();
  }

  @Override public void onLinkClicked(String title) {
    Intent intent = LinkService.getLink(this, title);
    if(intent != null && title.contains("Settings")) {
      ArrayList<WardrobeMenuWardrobeItem> wardrobes = new ArrayList<>();
      List<WardrobeMenuItem> menuItems = menuAdapter.getItems();
      for (WardrobeMenuItem item : menuItems) {
        if (item instanceof WardrobeMenuWardrobeItem) {
          wardrobes.add((WardrobeMenuWardrobeItem)item);
        }
      }
      intent.putParcelableArrayListExtra(SettingsActivity.EXTRAS_WARDROBES, wardrobes);
    }
    startActivity(intent);
  }

  public void onWardrobeItemClicked(int type, String itemID) {
    startActivity(LinkService.getDetailIntent(this, type, itemID));
  }

  @Override public void onBackPressed() {
    MaterialDialog.Builder builder = new MaterialDialog.Builder(this).title("Quit")
        .content("Do you really want to quit the application?")
        .positiveText(R.string.dialog_positive)
        .negativeText(R.string.dialog_negative);
    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
      @Override public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        // "exits" the application
        //Intent intent = new Intent(Intent.ACTION_MAIN);
        //intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);
        finish();
      }
    }).build().show();
  }

  public void onNewWardrobeCreated() {
    presenter.loadMenu();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUESTCODE_SETTINGS && resultCode == SettingsActivity.RESULTCODE_WARDROBE_DELETED) {
      // wardrobe was deleted, reload menu
      loadData(true);
    } else if(requestCode == REQUESTCODE_CREATE) {
      // pieces and outfits may have changed, handled by fragments
      for (Fragment f : fragmentManager.getFragments()) {
        f.onActivityResult(requestCode, resultCode, data);
      }
    }

    super.onActivityResult(requestCode, resultCode, data);
  }
}

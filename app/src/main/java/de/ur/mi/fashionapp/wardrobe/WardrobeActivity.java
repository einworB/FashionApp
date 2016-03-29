package de.ur.mi.fashionapp.wardrobe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
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
  private MenuItem mSearchAction;
  private MenuItem mFilterAction;
  private boolean isSearchOpened = false;
  private EditText edtSeach;
  private int[] FilterArray;
  private String wardrobeID;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
  }

  @Override protected void onMvpViewCreated() {
    presenter.getFirstWardrobeID();

    fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Fragment f = fragmentManager.findFragmentByTag(
            "android:switcher:" + contentView.getId() + ":" + contentView.getCurrentItem());
        if (f instanceof WardrobeFragment) {
          Intent i =
              LinkService.getCreateIntent(WardrobeActivity.this, ((WardrobeFragment) f).getType(), wardrobeID);
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
    presenter.setCurrentWardrobeID(ID);
    wardrobeID = ID;
    toolbar.setTitle(title);
    drawerLayout.closeDrawers();
  }

  @Override public void onNewWardrobeClicked() {

    presenter.addNewWardrobe();
  }

  @Override public void onLinkClicked(String title) {
    Intent intent = LinkService.getLink(this, title);
    if (intent != null && title.contains("Settings")) {
      ArrayList<WardrobeMenuWardrobeItem> wardrobes = new ArrayList<>();
      List<WardrobeMenuItem> menuItems = menuAdapter.getItems();
      for (WardrobeMenuItem item : menuItems) {
        if (item instanceof WardrobeMenuWardrobeItem) {
          wardrobes.add((WardrobeMenuWardrobeItem) item);
        }
      }
      intent.putParcelableArrayListExtra(SettingsActivity.EXTRAS_WARDROBES, wardrobes);
    }
    startActivity(intent);
  }

  public void onWardrobeItemClicked(int type, WardrobeItem item) {
    startActivity(LinkService.getDetailIntent(this, type, item,wardrobeID, null, true));
  }

  @Override public void onBackPressed() {
    if (isSearchOpened) {
      handleMenuSearch();
    }
    else {
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

  }


  @Override
  public void onNewWardrobeCreated(WardrobeMenuWardrobeItem wardrobe) {
    presenter.loadMenu();
    wardrobeID = wardrobe.getID();
    toolbar.setTitle(wardrobe.getTitle());
  }

  @Override
  public void onFirstWardrobeLoaded(String id, String name) {
    wardrobeID=id;
    toolbar.setTitle(name);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUESTCODE_SETTINGS
        && resultCode == SettingsActivity.RESULTCODE_WARDROBE_DELETED) {
      // wardrobe was deleted, reload menu
      loadData(true);
    } else if (requestCode == REQUESTCODE_CREATE) {
      // pieces and outfits may have changed, handled by fragments
      for (Fragment f : fragmentManager.getFragments()) {
        f.onActivityResult(requestCode, resultCode, data);
      }
    }

    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override public boolean onPrepareOptionsMenu(Menu menu) {
    mSearchAction = menu.findItem(R.id.action_search);
    mFilterAction = menu.findItem(R.id.action_filter);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_search) {
      handleMenuSearch();
      return true;
    } else if (id == R.id.action_filter) {
      handleMenuFilter();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  protected void handleMenuSearch() {
    ActionBar action = getSupportActionBar(); //get the actionbar
    if (isSearchOpened) { //test if the search is open

      if (action != null) {
        action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
        action.setDisplayShowTitleEnabled(true); //show the title in the action bar
      }

      //hides the keyboard
      View view = this.getCurrentFocus();
      if (view != null) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
      }
      /*
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);
      */

      for (Fragment f : fragmentManager.getFragments()) {
        if (f instanceof WardrobeFragment) {
          ((WardrobeFragment) f).search(null);
        }
      }

      mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));
      mFilterAction.setVisible(true);
      isSearchOpened = false;
    } else { //open the search entry

      if (action != null) {
        action.setDisplayShowCustomEnabled(true); //enable it to display a
        // custom view in the action bar.
        action.setCustomView(R.layout.search_bar);//add the custom view
        action.setDisplayShowTitleEnabled(false); //hide the title
      }

      mFilterAction.setVisible(false);

      edtSeach = (EditText) action.getCustomView().findViewById(R.id.edtSearch); //the text editor
      edtSeach.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start,
                                            int count, int after) {
                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start,
                                            int before, int count) {
                                          for (Fragment f : fragmentManager.getFragments()) {
                                            if (f instanceof WardrobeFragment) {
                                              ((WardrobeFragment) f).search(s);
                                            }
                                          }
                                        }

                                        @Override public void afterTextChanged(Editable s) {
                                        }
                                      }

      );
      edtSeach.requestFocus();

      //open the keyboard focused in the edtSearch
      InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);

      //add the close icon
      mSearchAction.setIcon(

          getResources()

              .

                  getDrawable(R.drawable.crop__ic_cancel)

      );

      isSearchOpened = true;
    }
  }

  private void handleMenuFilter() {
    String[] seasons = { "no filter","all seasons", "spring", "summer", "fall", "winter" };
    String[] categories = { "no filter", "top", "bottom", "shoe", "accessoire" };
    String[] occasions = { "no filter", "evening", "beach", "couch", "sport" };
    String[] colors = {
        "no filter", "red", "blue", "green", "yellow", "black", "white", "pink", "purple", "orange",
        "turquoise"
    };

    final ArrayAdapter<String> season_adp =
        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, seasons);
    final ArrayAdapter<String> cat_adp =
        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
    final ArrayAdapter<String> occ_adp =
        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, occasions);
    final ArrayAdapter<String> col_adp =
        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);

    LayoutInflater factory = LayoutInflater.from(this);
    final View stdView = factory.inflate(R.layout.filter_dialog, null);
    LinearLayout linearLayout = (LinearLayout) stdView.findViewById(R.id.filter_layout);
    final Spinner sp_cat = (Spinner) linearLayout.findViewById(R.id.filter_cat);
    final Spinner sp_season = (Spinner) linearLayout.findViewById(R.id.filter_season);
    final Spinner sp_occasion = (Spinner) linearLayout.findViewById(R.id.filter_occasion);
    final Spinner sp_color = (Spinner) linearLayout.findViewById(R.id.filter_color);

    sp_cat.setAdapter(cat_adp);
    sp_season.setAdapter(season_adp);
    sp_occasion.setAdapter(occ_adp);
    sp_color.setAdapter(col_adp);

    if(FilterArray!=null){
      sp_cat.setSelection(FilterArray[0]);
      sp_season.setSelection(FilterArray[1]);
      sp_occasion.setSelection(FilterArray[2]);
      sp_color.setSelection(FilterArray[3]);
    }

    MaterialDialog.Builder builder = new MaterialDialog.Builder(this).title("Filter")
        .positiveText(R.string.dialog_positive)
        .negativeText(R.string.dialog_negative)
        .onPositive(new MaterialDialog.SingleButtonCallback() {
          @Override
          public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            int[] itemPositions = new int[]{
                    sp_cat.getSelectedItemPosition(), sp_season.getSelectedItemPosition(),
                    sp_occasion.getSelectedItemPosition(), sp_color.getSelectedItemPosition()
            };
            FilterArray = new int[]{sp_cat.getSelectedItemPosition(),
                    sp_season.getSelectedItemPosition(),
                    sp_occasion.getSelectedItemPosition(),
                    sp_color.getSelectedItemPosition()};
            for (Fragment f : fragmentManager.getFragments()) {
              if (f instanceof WardrobeFragment) {
                ((WardrobeFragment) f).filter(itemPositions);
              }
            }
          }
        });
    builder.customView(linearLayout, true);
    builder.build().show();
  }

}

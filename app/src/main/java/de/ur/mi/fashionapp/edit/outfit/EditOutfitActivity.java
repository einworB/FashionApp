package de.ur.mi.fashionapp.edit.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;

import java.util.List;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;

/**
 * Created by Philip on 05/03/2016.
 */
public class EditOutfitActivity
    extends CBActivityMvpToolbar<LinearLayout, Object, EditOutfitView, EditOutfitPresenter>
    implements EditOutfitView, EditOutfitAdapter.EditOutfitAdapterListener {

  public static final String KEY_ITEM = "item";

  private WardrobeOutfitItem editItem;
  private EditOutfitAdapter adapter;
    private FloatingActionButton fab;

    private List<EditOutfitItem> pieces;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    editItem = getIntent().getParcelableExtra(KEY_ITEM);
    adapter = new EditOutfitAdapter(this, this);

    // TODO: get parcelable item from bundle; if editItem == null new item is created
  }

  @Override protected void onMvpViewCreated() {
    super.onMvpViewCreated();
      fab = (FloatingActionButton) findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

          }
      });
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (editItem != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle("Edit Item " + editItem.getTitle());
    }
  }

  @NonNull @Override public EditOutfitPresenter createPresenter() {
    return new EditOutfitPresenter(this, this);
  }

  @Override public void setData(Object data) {
    // not needed
  }

  @Override public void loadData(boolean pullToRefresh) {
    // not needed
    // TODO: maybe needed for a "prepareNewOutfit" function which returns a itemID?
  }

  @Override protected Integer getLayoutRes() {
    return R.layout.activity_edit_outfit;
  }

  @Override public void onOutfitEdited() {
    finish();
  }

  private void createOutfit() {
    // TODO: get data from EditTexts for the new WardrobeOutfitItem(editItem, title)
    presenter.createOutfit(editItem, true);
  }

  private void updateOutfit() {
    // TODO: get data from EditTexts for the updated WardrobeOutfitItem(editItemID, editItem, title)
    presenter.updateOutfit(editItem.getID(), editItem, true);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home: {
        finish();
        break;
      }
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onEditOutfitItemClicked(EditOutfitItem item) {

  }
}

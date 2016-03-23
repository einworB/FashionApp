package de.ur.mi.fashionapp.edit.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import java.util.List;

/**
 * Created by Philip on 05/03/2016.
 */
public class EditOutfitActivity
        extends CBActivityMvpToolbar<RecyclerView, Object, EditOutfitView, EditOutfitPresenter>
        implements EditOutfitView, EditOutfitAdapter.EditOutfitAdapterListener {

    static int REQUESTCODE_ADD = 401;

    public static final String KEY_ITEM = "item";

    private WardrobeOutfitItem editItem;
    private EditOutfitAdapter adapter;
    private FloatingActionButton fab;

    private List<EditOutfitItem> pieces;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editItem = getIntent().getParcelableExtra(KEY_ITEM);
        adapter = new EditOutfitAdapter(this, this);
        contentView.setAdapter(adapter);
      contentView.setLayoutManager(new LinearLayoutManager(this));
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // TODO: move to linkservice; maybe pass data via Bundle
              startActivityForResult(new Intent(EditOutfitActivity.this, PickOutfitPiecesActivity.class), REQUESTCODE_ADD);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (editItem != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit Item " + editItem.getTitle());
        }

        // TODO: get parcelable item from bundle; if editItem == null new item is created
    }

    @NonNull
    @Override
    public EditOutfitPresenter createPresenter() {
        return new EditOutfitPresenter(this, this);
    }

    @Override
    public void setData(Object data) {
        // not needed
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        // not needed
        // TODO: maybe needed for a "prepareNewOutfit" function which returns a itemID?
    }

    @Override
    protected Integer getLayoutRes() {
        return R.layout.activity_edit_outfit;
    }

    @Override
    public void onOutfitEdited() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUESTCODE_ADD && resultCode == RESULT_OK) {
            //TODO: get selected items for outfit
          if (data != null) {
            // TODO: get item from intent and user other type than object!
            Object item = data.getParcelableExtra(PickOutfitPiecesActivity.INTENT_EXTRA_PICKED_ITEM);
          }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

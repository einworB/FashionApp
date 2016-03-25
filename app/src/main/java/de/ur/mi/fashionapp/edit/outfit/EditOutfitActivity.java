package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 05/03/2016.
 */
public class EditOutfitActivity
        extends CBActivityMvpToolbar<RecyclerView, Object, EditOutfitView, EditOutfitPresenter>
        implements EditOutfitView, EditOutfitAdapter.EditOutfitAdapterListener {

    static int REQUESTCODE_ADD = 401;

    public static final String KEY_ITEM = "item";

    private Context context;

    private WardrobeOutfitItem editItem;
    private EditOutfitAdapter adapter;
    private FloatingActionButton fab;
    private  String wardrobeID;

    private List<WardrobePieceItem> pieces;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wardrobeID = getIntent().getStringExtra("WardrobeID");
        Log.d("idaaaaa",wardrobeID);
        context = this;
        editItem = getIntent().getParcelableExtra(KEY_ITEM);
        adapter = new EditOutfitAdapter(this, this);
        contentView.setAdapter(adapter);
        contentView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: maybe pass data via Bundle
                Intent i =
                        LinkService.getOutfitPieceIntent(EditOutfitActivity.this, wardrobeID);
                startActivityForResult(i, REQUESTCODE_ADD);
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
        EditText et = (EditText) findViewById(R.id.edit_outfit_name);
        editItem = new WardrobeOutfitItem();
        editItem.setTitle(et.getText().toString());
        Bitmap[] bitmaps = new Bitmap[10];
        if(!pieces.isEmpty()){
            for(int i=0; i<pieces.size(); i++){
                Bitmap img = ((WardrobePieceItem)pieces.get(i)).getImage();
                bitmaps[i] = img;
            }
            editItem.setImages(bitmaps);
        }
        if(wardrobeID!=null)editItem.setWardrobeID(wardrobeID);
        presenter.createOutfit(editItem, true);
    }

    private void updateOutfit() {
        // TODO: get data from EditTexts for the updated WardrobeOutfitItem(editItemID, editItem, title)
        presenter.updateOutfit(editItem.getID(), editItem, true);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_piece_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
            case R.id.menu_piece_edit_save:
                setResult(RESULT_OK);
                if (editItem == null) {
                    createOutfit();
                } else {
                    updateOutfit();
                }
                break;
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
                pieces = data.getParcelableArrayListExtra(PickOutfitPiecesActivity.INTENT_EXTRA_PICKED_ITEM);
                Log.d("EOA", "pieces: "+pieces);
                adapter.setItems(pieces);
                adapter.notifyDataSetChanged();
                //WardrobePieceItem item = data.getParcelableExtra(PickOutfitPiecesActivity.INTENT_EXTRA_PICKED_ITEM);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

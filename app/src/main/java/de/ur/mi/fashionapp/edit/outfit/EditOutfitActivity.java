package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;

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

    private WardrobeOutfitItem editItem;
    private EditOutfitAdapter adapter;
    private FloatingActionButton fab;
    private  String wardrobeID;

    private List<WardrobePieceItem> pieces;

    private EditText editTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wardrobeID = getIntent().getStringExtra("WardrobeID");
        editItem = getIntent().getParcelableExtra(KEY_ITEM);
        adapter = new EditOutfitAdapter(this, this);
        contentView.setAdapter(adapter);
        contentView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTitle = (EditText) findViewById(R.id.edit_outfit_name);

        if (editItem != null) {
            presenter.loadOutfitImages(editItem.getID(), editItem);
            editTitle.setText(editItem.getTitle());
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit Item " + editItem.getTitle());
        } else {
            editItem = new WardrobeOutfitItem();
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: maybe pass data via Bundle
                Intent i =
                        LinkService.getOutfitPieceIntent(EditOutfitActivity.this, editItem, wardrobeID);
                startActivityForResult(i, REQUESTCODE_ADD);
            }
        });
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
        Log.d("EOA", "loadData");
        presenter.loadItems(true);
        /*if(editItem != null && editItem.getID() != null){
            presenter.loadOutfitImages(editItem.getID(), editItem);
        }*/
    }

    @Override
    protected Integer getLayoutRes() {
        return R.layout.activity_edit_outfit;
    }

    @Override
    public void onOutfitEdited() {
        finish();
    }

    @Override
    public void onImageLoaded(String id) {
        Log.d("EOA", "onImageLoaded");

        if(pieces != null) {
            for (int i = 0; i < pieces.size(); i++) {
                int itemPosition = adapter.getItemPosition(pieces.get(i).getID());
                if (itemPosition != -1) {
                    adapter.notifyItemChanged(itemPosition);
                }
                /*Bitmap image = pieces.get(i).getImage();
                if (image != null) {
                    ImageView pieceImage = (ImageView)findViewById(R.id.image);
                    Drawable dImage = new BitmapDrawable(getResources(), image);
                    pieceImage.setImageDrawable(dImage);
                    pieceImage.requestLayout();
                }*/
            }
        }
    }

    private void createOutfit() {
        EditText et = (EditText) findViewById(R.id.edit_outfit_name);
        editItem.setTitle(et.getText().toString());
        Bitmap[] bitmaps = new Bitmap[10];
        if(!pieces.isEmpty()){
            Log.d("EOA", "pieces not empty");
            for(int i=0; i<pieces.size(); i++){
                Bitmap img = ((WardrobePieceItem)pieces.get(i)).getImage();
                Log.d("EOA", "img: "+img.toString());
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
                Log.d("EOA", "piece[0]image: " + pieces.get(0).getImage());
                adapter.setItems(pieces);
                adapter.notifyDataSetChanged();
                //WardrobePieceItem item = data.getParcelableExtra(PickOutfitPiecesActivity.INTENT_EXTRA_PICKED_ITEM);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

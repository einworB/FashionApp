package de.ur.mi.fashionapp.edit.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Jana on 22.03.2016.
 */
public class PickOutfitPiecesActivity
        extends CBActivityMvpToolbar<RecyclerView, List<WardrobePieceItem>, PickOutfitPiecesView, PickOutfitPiecesPresenter>
        implements PickOutfitPiecesView, PickOutfitPiecesAdapter.PickOutfitPiecesAdapterListener{

    public static String INTENT_EXTRA_PICKED_ITEM = "picked_item";
    public static String KEY_ITEM = "item";
    private PickOutfitPiecesAdapter adapter;

    private WardrobeOutfitItem editItem;
    private String wardrobeID;
    private ArrayList<WardrobePieceItem> outfitItemsAdded;
    private ArrayList<String> tempPieceIDs;
    private int num = 0;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wardrobeID = getIntent().getStringExtra("WardrobeID");
        loadData(true);
        editItem = getIntent().getParcelableExtra(KEY_ITEM);
        adapter = new PickOutfitPiecesAdapter(this, this);
        contentView.setAdapter(adapter);
        contentView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        outfitItemsAdded = new ArrayList<>();
        if(editItem != null){
            tempPieceIDs = new ArrayList<>(Arrays.asList(editItem.getPieceIDs()));
            for(int i=0; i<editItem.getPieceIDs().length; i++){
                if(editItem.getPieceIDs()[i]!= null){
                    WardrobePieceItem item = new WardrobePieceItem();
                    item.setID(editItem.getPieceIDs()[i]);
                    item.setImage(editItem.getImages()[i]);
                    outfitItemsAdded.add(item);
                    num++;
                }
            }
        } else tempPieceIDs = new ArrayList<>();
    }

    @NonNull
    @Override
    public PickOutfitPiecesPresenter createPresenter() {
        return new PickOutfitPiecesPresenter(this, this);
    }

    @Override
    public void setData(List<WardrobePieceItem> data) {
        if (data != null && !data.isEmpty()) {
            adapter.setItems(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        if(wardrobeID != null){
            presenter.loadWardrobeItems(wardrobeID, true);
        }
    }

    @Override
    public void onOutfitPieceItemsSelected(WardrobePieceItem item){
        Intent intent = new Intent();
        if(item != null && num < 10){
            if(!tempPieceIDs.contains(item.getID())){
                outfitItemsAdded.add(item);
                tempPieceIDs.add(item.getID());
                num++;
            } else {
                for(int i=0; i < outfitItemsAdded.size(); i++){
                    if(outfitItemsAdded.get(i).getID().equals(item.getID())){
                        outfitItemsAdded.remove(i);
                    }
                }
                tempPieceIDs.remove(item.getID());
                num--;
            }
        }
    }

    @Override protected Integer getLayoutRes() {
        return R.layout.activity_pick_outfit_pieces;
    }

    @Override
    public void onImageLoaded(String itemID) {
        if(tempPieceIDs.contains(itemID)){
            adapter.setItemSelected(itemID);
        }
        adapter.notifyItemChanged(adapter.getItemPosition(itemID));
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_piece_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                setResult(RESULT_CANCELED);
                finish();
                break;
            }
            case R.id.menu_piece_edit_save:
                saveChanges();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveChanges(){
        //TODO: called on savebutton click
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(INTENT_EXTRA_PICKED_ITEM, outfitItemsAdded);
        setResult(RESULT_OK, intent);
        finish();
    }
}

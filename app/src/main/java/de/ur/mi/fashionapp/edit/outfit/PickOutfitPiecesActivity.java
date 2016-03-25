package de.ur.mi.fashionapp.edit.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jana on 22.03.2016.
 */
public class PickOutfitPiecesActivity
        extends CBActivityMvpToolbar<RecyclerView, List<WardrobePieceItem>, PickOutfitPiecesView, PickOutfitPiecesPresenter>
        implements PickOutfitPiecesView, PickOutfitPiecesAdapter.PickOutfitPiecesAdapterListener{

    public static String INTENT_EXTRA_PICKED_ITEM = "picked_item";

    private PickOutfitPiecesAdapter adapter;
    private ArrayList<WardrobePieceItem> outfitItemsAdded;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PickOutfitPiecesAdapter(this);
        contentView.setAdapter(adapter);
        contentView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
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
      // load all pieces from the presenter here (unless they are passed from the last activity)
    }

    @Override
    public void onOutfitPieceItemsSelected(WardrobePieceItem item){
        // set item here
        Intent intent = new Intent();
        // use wardrobeitems
        intent.putExtra(INTENT_EXTRA_PICKED_ITEM, item);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override protected Integer getLayoutRes() {
        return R.layout.activity_pick_outfit_pieces;
    }

    @Override
    public void onImageLoaded(String itemID) {

    }

    private void saveChanges(){
        //TODO: called on savebutton click
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(INTENT_EXTRA_PICKED_ITEM, outfitItemsAdded);
        setResult(RESULT_OK, intent);
        finish();
    }
}

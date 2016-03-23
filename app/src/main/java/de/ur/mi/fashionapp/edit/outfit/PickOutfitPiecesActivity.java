package de.ur.mi.fashionapp.edit.outfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import java.util.List;


/**
 * Created by Jana on 22.03.2016.
 */
public class PickOutfitPiecesActivity
        extends CBActivityMvpToolbar<RecyclerView, List<PickOutfitPieceItem>, PickOutfitPiecesView, PickOutfitPiecesPresenter>
        implements PickOutfitPiecesView, PickOutfitPiecesAdapter.PickOutfitPiecesAdapterListener{

    public static String INTENT_EXTRA_PICKED_ITEM = "picked_item";

    private PickOutfitPiecesAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PickOutfitPiecesAdapter(this);
        contentView.setAdapter(adapter);
        contentView.setLayoutManager(new LinearLayoutManager(this));
    }

    @NonNull
    @Override
    public PickOutfitPiecesPresenter createPresenter() {
        return new PickOutfitPiecesPresenter(this, this);
    }

    @Override
    public void setData(List<PickOutfitPieceItem> data) {
      // data is set from the presenter here (if(isViewAttached() getView().setData(data)
      // TODO: set the data to the adapter (adapter.setItems(data)
    }

    @Override
    public void loadData(boolean pullToRefresh) {
      // load all pieces from the presenter here (unless they are passed from the last activity)
    }

    @Override
    public void onOutfitPieceItemsSelected(PickOutfitPieceItem item){
        // set item here
        Intent intent = new Intent();
        // only working with parcelable items; make pick item parcelable or use wardrobeitems
        //intent.putExtra(INTENT_EXTRA_PICKED_ITEM, item);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override protected Integer getLayoutRes() {
        return R.layout.activity_pick_outfit_pieces;
    }
}

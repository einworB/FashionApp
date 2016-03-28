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
    private String[] pieceIDs;

    private int num = 0;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PickOutfitPiecesAdapter(this, this);
        contentView.setAdapter(adapter);
        contentView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        outfitItemsAdded = new ArrayList<>();
        pieceIDs = new String[10];
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
        presenter.loadWardrobeItems(true);
    }

    @Override
    public void onOutfitPieceItemsSelected(WardrobePieceItem item){
        // set item here
        Intent intent = new Intent();
        // use wardrobeitems
        if(item != null){

            Log.d("POPA", "itemImage: " + item.getImage());
            outfitItemsAdded.add(item);
            pieceIDs[num] = item.getID();
            num++;
        }
    }

    @Override protected Integer getLayoutRes() {
        return R.layout.activity_pick_outfit_pieces;
    }

    @Override
    public void onImageLoaded(String itemID) {
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

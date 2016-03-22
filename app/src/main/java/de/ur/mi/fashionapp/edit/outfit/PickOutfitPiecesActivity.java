package de.ur.mi.fashionapp.edit.outfit;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;


import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;

import java.util.List;


/**
 * Created by Jana on 22.03.2016.
 */
public class PickOutfitPiecesActivity
        extends CBActivityMvpToolbar<RecyclerView, List<PickOutfitPieceItem>, PickOutfitPiecesView, PickOutfitPiecesPresenter>
        implements PickOutfitPiecesView{

    public static int RESULTCODE_PIECES_SELECTED = 404;

    @NonNull
    @Override
    public PickOutfitPiecesPresenter createPresenter() {
        return new PickOutfitPiecesPresenter(this, this);
    }

    @Override
    public void setData(List<PickOutfitPieceItem> data) {

    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }

    public void onOutfitPieceItemsSelected(){
        setResult(RESULTCODE_PIECES_SELECTED);
    }


}

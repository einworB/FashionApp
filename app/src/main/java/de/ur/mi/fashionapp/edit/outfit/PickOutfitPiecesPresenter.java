package de.ur.mi.fashionapp.edit.outfit;

import android.content.Context;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by Jana on 22.03.2016.
 */
public class PickOutfitPiecesPresenter extends MvpBasePresenter<PickOutfitPiecesView> {

    private Context context;

    public PickOutfitPiecesPresenter(Context context, PickOutfitPiecesView view) {
        this.context = context;
        attachView(view);
    }
}

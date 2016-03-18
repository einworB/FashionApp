package de.ur.mi.fashionapp.detail;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.CatWrapper;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

public class PieceDetailActivity extends CBActivityMvpToolbar<RecyclerView, Object, DetailView, DetailPresenter> implements DetailView {

  public static final String KEY_ITEM = "item";

  private WardrobePieceItem item;

  private ImageView pieceImage, pieceType, pieceColor, pieceSeason, pieceOccasion;

  private CatWrapper cW;

  @Override protected void onCreate(Bundle savedInstanceState) {
    item = getIntent().getParcelableExtra(KEY_ITEM);

    // TODO: get parcelable item from bundle

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_piece_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    cW = new CatWrapper();

    pieceImage = (ImageView)findViewById(R.id.pieceImage);
    Bitmap image = item.getImage();
    if (image != null) {
      Drawable dImage = new BitmapDrawable(getResources(), image);
      pieceImage.setImageDrawable(dImage);
    }


    pieceColor = (ImageView)findViewById(R.id.pieceColor);
      pieceColor.setImageResource(0);
      if(cW.colorWrap(item.getTag3()) != -1){
          pieceColor.setBackgroundColor(getResources().getColor(cW.colorWrap(item.getTag3())));
          Log.d("PieceDetailCat", "Color: " + cW.catWrap(item.getTag3()) + " tag: " + item.getTag3());
      }

      Log.d("PieceDetailCat", "Category: " + cW.catWrap(item.getCat())+" tag: "+item.getCat());
    pieceType = (ImageView)findViewById(R.id.pieceType);
      if(cW.catWrap(item.getCat()) != -1){
          pieceType.setImageResource(cW.catWrap(item.getCat()));
      }


      Log.d("PieceDetailCat", "Season: " + cW.catWrap(item.getTag1()) + " tag: " + item.getTag1());
    pieceSeason = (ImageView)findViewById(R.id.pieceSeason);
      if(cW.seasonWrap(item.getTag1())!= -1){
          pieceSeason.setImageResource(cW.seasonWrap(item.getTag1()));
      }


      Log.d("PieceDetailCat", "Occasion: " + cW.catWrap(item.getTag2()) + " tag: " + item.getTag2());
    pieceOccasion = (ImageView)findViewById(R.id.pieceOccasion);
      if(cW.occasionWrap(item.getTag2()) != -1){
          pieceOccasion.setImageResource(cW.occasionWrap(item.getTag2()));
      }



    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(LinkService.getUpdateIntent(PieceDetailActivity.this, WardrobeFragment.TYPE_PIECE,
            item));
      }
    });
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(true);
    getSupportActionBar().setTitle(item.getTitle());

    // TODO: start activity for result and set result for wardrobe activity to enable reload if item was updated
  }

  @NonNull @Override public DetailPresenter createPresenter() {
    return new DetailPresenter(this);
  }

  @Override public void setData(Object data) {

  }

  @Override public void loadData(boolean pullToRefresh) {
    presenter.loadPieceImage(item.getID(), item);
  }

  @Override public void onImageLoaded(String pieceID) {
    Bitmap image = item.getImage();
    if (image != null) {
      Drawable dImage = new BitmapDrawable(getResources(), image);
      pieceImage.setImageDrawable(dImage);
      pieceImage.requestLayout();
    }
  }
}

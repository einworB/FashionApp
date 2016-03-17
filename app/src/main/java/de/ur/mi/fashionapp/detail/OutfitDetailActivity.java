package de.ur.mi.fashionapp.detail;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;

public class OutfitDetailActivity extends CBActivityMvpToolbar<RecyclerView, Object, DetailView, DetailPresenter> implements DetailView {

  public static final String KEY_ITEM = "item";

  private WardrobeOutfitItem item;
  private ImageView mainPiece, leftPiece, midPiece, rightPiece;

  @Override protected void onCreate(Bundle savedInstanceState) {
      item = getIntent().getParcelableExtra(KEY_ITEM);
      // TODO: get parcelable item from bundle
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_outfit_detail);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      mainPiece = (ImageView)findViewById(R.id.mainPiece);
      leftPiece = (ImageView)findViewById(R.id.leftPiece);
      midPiece = (ImageView)findViewById(R.id.midPiece);
      rightPiece = (ImageView)findViewById(R.id.rightPiece);

      leftPiece.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              focusOnPiece(leftPiece);
          }
      });
      midPiece.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View view) {
              focusOnPiece(midPiece);
          }
      });

      rightPiece.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              focusOnPiece(rightPiece);
          }
      });

      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        startActivity(LinkService.getUpdateIntent(OutfitDetailActivity.this, WardrobeFragment.TYPE_OUTFIT,
            item));
        }
      });
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowTitleEnabled(true);
      // TODO: replace with item title
      getSupportActionBar().setTitle("Outfit Detail");
  }

    @NonNull @Override public DetailPresenter createPresenter() {
        return new DetailPresenter(this);
    }

    private void setImages() {
      Bitmap image1 = item.getImage1();
      Bitmap image2 = item.getImage2();
      Bitmap image3 = item.getImage3();
      Bitmap image4 = item.getImage4();
        if (image1 != null){
            Drawable main = new BitmapDrawable(getResources(), image1);
            mainPiece.setImageDrawable(main);
        }
        if (image2 != null){
            Drawable left = new BitmapDrawable(getResources(), image2);
            leftPiece.setImageDrawable(left);
        }
        if (image3 != null){
            Drawable mid = new BitmapDrawable(getResources(), image3);
            midPiece.setImageDrawable(mid);
        }
        if (image4 != null){
            Drawable right = new BitmapDrawable(getResources(), image4);
            rightPiece.setImageDrawable(right);
        }
    }

  private void setImage(String pieceID) {
    // TODO: das muss noch viel besser gehen....
    int imageNumber = -1;
    if (pieceID != null) {
      String[] ids = item.getPieceIDs();
      for (int i=0; i < ids.length; i++) {
        String id = ids[i];
        if (pieceID.equals(id)) {
          imageNumber = i+1;
          break;
        }
      }
    }
    switch (imageNumber) {
      case -1:
        break;
      case 1:
        Bitmap image1 = item.getImage1();
        if (image1 != null){
          Drawable main = new BitmapDrawable(getResources(), image1);
          mainPiece.setImageDrawable(main);
          mainPiece.requestLayout();
        }
        break;
      case 2:
        Bitmap image2 = item.getImage2();
        if (image2 != null){
          Drawable left = new BitmapDrawable(getResources(), image2);
          leftPiece.setImageDrawable(left);
          leftPiece.requestLayout();
        }
        break;
      case 3:
        Bitmap image3 = item.getImage3();
        if (image3 != null){
          Drawable mid = new BitmapDrawable(getResources(), image3);
          midPiece.setImageDrawable(mid);
          midPiece.requestLayout();
        }
        break;
      case 4:
        Bitmap image4 = item.getImage4();
        if (image4 != null){
          Drawable right = new BitmapDrawable(getResources(), image4);
          rightPiece.setImageDrawable(right);
          rightPiece.requestLayout();
        }
        break;
    }

  }

    private void focusOnPiece(ImageView curPiece) {
        Drawable temp = mainPiece.getDrawable();
        mainPiece.setImageDrawable(curPiece.getDrawable());
        curPiece.setImageDrawable(temp);
    }

    @Override public void setData(Object data) {

    }

    @Override public void loadData(boolean pullToRefresh) {
      presenter.loadOutfitImages(item.getPieceIDs(), item);
    }

  @Override public void onImageLoaded(String pieceID) {
    setImage(pieceID);
  }
}

package de.ur.mi.fashionapp.detail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;

public class OutfitDetailActivity extends AppCompatActivity {

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

      setImages();

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

    private void setImages() {
        if (item.getImage1() != null){
            Bitmap mainImage = BitmapFactory.decodeByteArray(item.getImage1(), 0, item.getImage1().length);
            Drawable main = new BitmapDrawable(getResources(), mainImage);
            mainPiece.setImageDrawable(main);
        }
        if (item.getImage2() != null){
            Bitmap leftImage = BitmapFactory.decodeByteArray(item.getImage2(), 0, item.getImage2().length);
            Drawable left = new BitmapDrawable(getResources(), leftImage);
            leftPiece.setImageDrawable(left);
        }
        if (item.getImage3() != null){
            Bitmap midImage = BitmapFactory.decodeByteArray(item.getImage3(), 0, item.getImage3().length);
            Drawable mid = new BitmapDrawable(getResources(), midImage);
            midPiece.setImageDrawable(mid);
        }
        if (item.getImage4() != null){
            Bitmap rightImage = BitmapFactory.decodeByteArray(item.getImage4(), 0, item.getImage4().length);
            Drawable right = new BitmapDrawable(getResources(), rightImage);
            midPiece.setImageDrawable(right);
        }
    }

    private void focusOnPiece(ImageView curPiece) {
        Drawable temp = mainPiece.getDrawable();
        mainPiece.setImageDrawable(curPiece.getDrawable());
        curPiece.setImageDrawable(temp);
    }
}

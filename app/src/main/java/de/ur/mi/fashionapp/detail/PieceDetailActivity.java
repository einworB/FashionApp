package de.ur.mi.fashionapp.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.CatWrapper;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.util.share.ShareHelper;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

public class PieceDetailActivity
    extends CBActivityMvpToolbar<RecyclerView, Object, DetailView, DetailPresenter>
    implements DetailView {

  public static final String KEY_ITEM = "item";
  public static final String KEY_WARDROBE_ID = "wardrobe_id";
  public static final String KEY_ITEM_ID = "item_id";

  private WardrobePieceItem item;
  private String wardrobeID;
  private String itemID;
  private ImageView pieceImage, pieceType, pieceColor, pieceSeason, pieceOccasion;
  private View tagContainer, shareContainer;
  private TextView sharingText;

  private CatWrapper cW;

  @Override protected void onCreate(Bundle savedInstanceState) {
    item = getIntent().getParcelableExtra(KEY_ITEM);
    Log.d("PDA", "item: " + item);
    wardrobeID = getIntent().getStringExtra(KEY_WARDROBE_ID);
    itemID = getIntent().getStringExtra(KEY_ITEM_ID);

    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_piece_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    pieceImage = (ImageView) findViewById(R.id.pieceImage);
    pieceColor = (ImageView) findViewById(R.id.pieceColor);
    pieceType = (ImageView) findViewById(R.id.pieceType);
    pieceSeason = (ImageView) findViewById(R.id.pieceSeason);
    pieceOccasion = (ImageView) findViewById(R.id.pieceOccasion);
    tagContainer = findViewById(R.id.tagContainer);
    shareContainer = findViewById(R.id.shareContainer);
    sharingText = (TextView) findViewById(R.id.sharingText);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(true);

    if (item != null) {
      setupDetails();
    } else {
      getSupportActionBar().setTitle("Loading...");
    }

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(
            LinkService.getUpdateIntent(PieceDetailActivity.this, WardrobeFragment.TYPE_PIECE, item,
                wardrobeID));
      }
    });
  }

  private void setupDetails() {
    getSupportActionBar().setTitle(item.getTitle());

    CatWrapper cW = new CatWrapper();

    Bitmap image = item.getImage();
    if (image != null) {
      Drawable dImage = new BitmapDrawable(getResources(), image);
      pieceImage.setImageDrawable(dImage);
    }

    pieceColor.setImageResource(0);
    if (cW.colorWrap(item.getColor()) != -1) {
      pieceColor.setBackgroundColor(getResources().getColor(cW.colorWrap(item.getColor())));
    }

    if (cW.catWrap(item.getCat()) != -1) {
      pieceType.setImageResource(cW.catWrap(item.getCat()));
    }

    if (cW.seasonWrap(item.getSeason()) != -1) {
      pieceSeason.setImageResource(cW.seasonWrap(item.getSeason()));
    }

    if (cW.occasionWrap(item.getOccasion()) != -1) {
      pieceOccasion.setImageResource(cW.occasionWrap(item.getOccasion()));
    }
  }

  @NonNull @Override public DetailPresenter createPresenter() {
    return new DetailPresenter(this, getApplicationContext());
  }

  @Override public void setData(Object data) {
    if (data != null && data instanceof WardrobePieceItem) {
      item = (WardrobePieceItem) data;
      setupDetails();
    }
  }

  @Override public void loadData(boolean pullToRefresh) {
    if (item != null) {
      presenter.loadPieceImage(item.getID(), item);
    } else {
      presenter.loadPiece(itemID);
    }
  }

  @Override public void onImageLoaded(String pieceID) {
    Bitmap image = item.getImage();
    if (image != null) {
      Drawable dImage = new BitmapDrawable(getResources(), image);
      pieceImage.setImageDrawable(dImage);
      pieceImage.requestLayout();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_piece_detail, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_piece_detail_share:
        // TODO: sharing here
        tagContainer.setVisibility(View.GONE);
        sharingText.setVisibility(View.VISIBLE);
        shareContainer.invalidate();
        shareContainer.post(new Runnable() {
          @Override public void run() {
            shareContainer.setDrawingCacheEnabled(true);
            shareContainer.buildDrawingCache(true);
            Bitmap bitmap = Bitmap.createBitmap(shareContainer.getDrawingCache(true));
            Intent intent = ShareHelper.startSharing(bitmap, PieceDetailActivity.this);
            if (intent != null) {
              startActivityForResult(intent, 1);
            } else {
              // TODO: show error toast; no social media apps installed(twitter, facebook, whatsapp)
            }
            tagContainer.setVisibility(View.VISIBLE);
            sharingText.setVisibility(View.GONE);
            shareContainer.setDrawingCacheEnabled(false);
          }
        });

        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}

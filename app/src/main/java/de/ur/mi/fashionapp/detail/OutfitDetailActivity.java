package de.ur.mi.fashionapp.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.ImageViewholder;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.util.share.ShareHelper;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;
import java.util.ArrayList;
import java.util.List;

public class OutfitDetailActivity
    extends CBActivityMvpToolbar<RecyclerView, Object, DetailView, DetailPresenter>
    implements DetailView, ImageViewholder.ImageViewholderClickListener {

  public static final String KEY_ITEM = "item";
  public static final String KEY_WARDROBE_ID = "wardrobe_id";
  private String wardrobeID;

  private WardrobeOutfitItem item;
  private ImageView mainPiece;
  private OutfitDetailAdapter adapter;
  private int currentPosition;
  private int pieceCount;
  private List<Integer> loadedImagePositions = new ArrayList<>();
  private View shareContainer;
  private TextView sharingText;

  @Override protected void onCreate(Bundle savedInstanceState) {
    item = getIntent().getParcelableExtra(KEY_ITEM);
    wardrobeID = getIntent().getStringExtra(KEY_WARDROBE_ID);
    if (item != null) {
      for (String id : item.getPieceIDs()) {
        if (id != null && !id.isEmpty()) {
          pieceCount++;
        }
      }
    }
    super.onCreate(savedInstanceState);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    mainPiece = (ImageView) findViewById(R.id.mainPiece);
    shareContainer = findViewById(R.id.shareContainer);
    sharingText = (TextView) findViewById(R.id.sharingText);
    adapter = new OutfitDetailAdapter(this, this);
    contentView.setAdapter(adapter);
    contentView.setLayoutManager(
        new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(
            LinkService.getUpdateIntent(OutfitDetailActivity.this, WardrobeFragment.TYPE_OUTFIT,
                item, wardrobeID, getIntent().getExtras().getBoolean("isDetail")));
      }
    });
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(true);
    // TODO: replace with item title
    getSupportActionBar().setTitle("Outfit Detail");
  }

  @Override protected Integer getLayoutRes() {
    return R.layout.activity_outfit_detail;
  }

  @NonNull @Override public DetailPresenter createPresenter() {
    return new DetailPresenter(this, getApplicationContext());
  }

  private void setImage(String pieceID) {
    int imageNumber = -1;
    if (pieceID != null) {
      String[] ids = item.getPieceIDs();
      for (int i = 0; i < ids.length; i++) {
        String id = ids[i];
        if (pieceID.equals(id) && !loadedImagePositions.contains(i)) {
          imageNumber = i;
          loadedImagePositions.add(i);
          break;
        }
      }
    }
    if (imageNumber != -1) {
      // piececount is used to ensure only so many views are created as pieces exist in the oufit
      List<Bitmap> bitmapList = new ArrayList<>(pieceCount);
      Bitmap[] bitmaps = item.getImages();
      for (int i = 0; i < pieceCount; i++) {
        bitmapList.add(bitmaps[i]);
      }
      adapter.setItems(bitmapList);
      adapter.notifyItemChanged(imageNumber);
      if (imageNumber == 0) {
        Bitmap bitmap = item.getImages()[imageNumber];
        if (bitmap != null) {
          mainPiece.setImageBitmap(bitmap);
          mainPiece.requestLayout();
          currentPosition = 0;
        }
      }
    }
  }

  @Override public void setData(Object data) {
    // not needed
  }

  @Override public void loadData(boolean pullToRefresh) {
    presenter.loadOutfitImages(item.getPieceIDs(), item);
  }

  @Override public void onImageLoaded(String pieceID) {
    setImage(pieceID);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_outfit_detail, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_outfit_detail_share:
        // TODO: sharing here
        sharingText.setVisibility(View.VISIBLE);
        shareContainer.invalidate();
        shareContainer.post(new Runnable() {
          @Override public void run() {
            shareContainer.setDrawingCacheEnabled(true);
            shareContainer.buildDrawingCache(true);
            Bitmap bitmap = Bitmap.createBitmap(shareContainer.getDrawingCache(true));
            Intent intent = ShareHelper.startSharing(bitmap, OutfitDetailActivity.this);
            if (intent != null) {
              startActivityForResult(intent, 1);
            } else {
              Toast.makeText(OutfitDetailActivity.this, "No social media apps installed", Toast.LENGTH_LONG).show();
            }
            sharingText.setVisibility(View.GONE);
            shareContainer.setDrawingCacheEnabled(false);
          }
        });
        return true;
      case R.id.menu_outfit_detail_info:
        String pieceID = this.item.getPieceIDs()[currentPosition];
        startActivity(
            LinkService.getDetailIntent(this, WardrobeFragment.TYPE_PIECE, null, wardrobeID,
                    pieceID, getIntent().getExtras().getBoolean("isDetail")));
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void onImageClicked(int position) {
    Bitmap bitmap = item.getImages()[position];
    mainPiece.setImageBitmap(bitmap);
    mainPiece.requestLayout();
    currentPosition = position;
  }
}

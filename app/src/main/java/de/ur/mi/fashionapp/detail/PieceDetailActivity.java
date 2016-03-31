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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.edit.piece.EditPieceActivity;
import de.ur.mi.fashionapp.util.CatWrapper;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.util.share.ShareHelper;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

/**
 * This activity shows the details of a certain piece. The piece itself is loaded by the
 * presenter.
 * Every piece has a category, an occasion, a color and a season which are also displayed.
 * Clicking the "share" menu button allows the user to share a screenshot of the piece together
 * with a short description via social media. This is only possible if a social media appliction
 * (twitter, whatsapp, facebook) is installed.
 */
public class PieceDetailActivity
    extends CBActivityMvpToolbar<RecyclerView, Object, DetailView, DetailPresenter>
    implements DetailView {

  public static final String KEY_ITEM = "item";
  public static final String KEY_WARDROBE_ID = "wardrobe_id";
  public static final String KEY_ITEM_ID = "item_id";
  public static final int REQUESTCODE_UPDATE = 102;

  private WardrobePieceItem item;
  private String wardrobeID;
  private String itemID;
  private ImageView pieceImage, pieceType, pieceColor, pieceSeason, pieceOccasion;
  private View tagContainer, shareContainer;
  private TextView sharingText;

  @Override protected void onCreate(Bundle savedInstanceState) {
    item = getIntent().getParcelableExtra(KEY_ITEM);
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
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    if (item != null) {
      setupDetails();
    } else if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle("Loading...");
    }

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivityForResult(
            LinkService.getUpdateIntent(PieceDetailActivity.this, WardrobeFragment.TYPE_PIECE, item,
                wardrobeID, getIntent().getExtras().getBoolean("isDetail")), REQUESTCODE_UPDATE);
      }
    });
  }

  private void setupDetails() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(item.getTitle());
    }

    CatWrapper cW = new CatWrapper();

    Bitmap image = item.getImage();
    if (image != null) {
      Drawable dImage = new BitmapDrawable(getResources(), image);
      pieceImage.setImageDrawable(dImage);
    }

    pieceColor.setImageResource(0);
    if (item.getColor() != -1) {
      pieceColor.setImageResource(R.drawable.ic_icon_rounded_corners);
      pieceColor.setBackgroundColor(item.getColor());
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
      presenter.loadPieceImage(item.getID(), item, pullToRefresh);
    } else {
      presenter.loadPiece(itemID, pullToRefresh);
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
              Toast.makeText(PieceDetailActivity.this, "No social media apps installed",
                  Toast.LENGTH_LONG).show();
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

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (REQUESTCODE_UPDATE == requestCode && resultCode == RESULT_OK && data != null) {
      WardrobePieceItem updatedItem = data.getParcelableExtra(EditPieceActivity.KEY_ITEM);
      if (updatedItem != null) {
        item = updatedItem;
        setupDetails();
        loadData(true);
      }
    }
  }
}

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
import android.widget.TextView;

import de.ur.mi.fashionapp.CatWrapper;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;

public class PieceDetailActivity extends AppCompatActivity {

  public static final String KEY_ITEM = "item";

  private WardrobePieceItem item;

  private ImageView pieceImage, pieceType, pieceColor, pieceSeason, pieceOccasion;
  private TextView pieceName;

  private CatWrapper cW;

  @Override protected void onCreate(Bundle savedInstanceState) {
    item = getIntent().getParcelableExtra(KEY_ITEM);
    // TODO: get parcelable item from bundle

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_piece_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    cW = new CatWrapper();
    pieceName = (TextView)findViewById(R.id.pieceName);
    pieceName.setText(item.getTitle());

    pieceImage = (ImageView)findViewById(R.id.pieceImage);
    Bitmap image = BitmapFactory.decodeByteArray(item.getImage(), 0, item.getImage().length);
    Drawable dImage = new BitmapDrawable(getResources(), image);
    pieceImage.setImageDrawable(dImage);

    pieceColor = (ImageView)findViewById(R.id.pieceColor);
      pieceColor.setImageResource(0);
    pieceColor.setBackgroundColor(cW.colorWrap(item.getTag3()));

    pieceType = (ImageView)findViewById(R.id.pieceType);
    pieceType.setImageResource(cW.catWrap(item.getCat()));

    pieceSeason = (ImageView)findViewById(R.id.pieceSeason);
    pieceSeason.setImageResource(cW.seasonWrap(item.getTag1()));

    pieceOccasion = (ImageView)findViewById(R.id.pieceOccasion);
    pieceOccasion.setImageResource(cW.occasionWrap(item.getTag2()));

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        startActivity(LinkService.getUpdateIntent(PieceDetailActivity.this, WardrobeFragment.TYPE_PIECE,
            item));
      }
    });
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowTitleEnabled(true);
    // TODO: replace with item title
    getSupportActionBar().setTitle("Item "+ item.getTitle() +" Detail");

    // TODO: start activity for result and set result for wardrobe activity to enable reload if item was updated
  }
}

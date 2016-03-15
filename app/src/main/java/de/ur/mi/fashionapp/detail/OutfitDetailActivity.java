package de.ur.mi.fashionapp.detail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;

public class OutfitDetailActivity extends AppCompatActivity {

  public static final String KEY_ITEM = "item";

  private WardrobeOutfitItem item;

  @Override protected void onCreate(Bundle savedInstanceState) {
    item = getIntent().getParcelableExtra(KEY_ITEM);
    // TODO: get parcelable item from bundle

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_outfit_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

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
    getSupportActionBar().setTitle("Item "+ item.getTitle() +" Detail");
  }
}

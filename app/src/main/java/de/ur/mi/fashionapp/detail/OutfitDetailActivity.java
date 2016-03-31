package de.ur.mi.fashionapp.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;

import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.edit.outfit.EditOutfitActivity;
import de.ur.mi.fashionapp.ui.ImageViewholder;
import de.ur.mi.fashionapp.util.LinkService;
import de.ur.mi.fashionapp.util.share.ShareHelper;
import de.ur.mi.fashionapp.wardrobe.WardrobeFragment;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;

import java.util.ArrayList;
import java.util.List;

public class OutfitDetailActivity
        extends CBActivityMvpToolbar<RecyclerView, Object, DetailView, DetailPresenter>
        implements DetailView {

    public static final String KEY_ITEM = "item";
    public static final String KEY_WARDROBE_ID = "wardrobe_id";
    public static final int REQUESTCODE_UPDATE = 101;
    private String wardrobeID;

    private WardrobeOutfitItem item;
    private ImageView mainPiece, pieceTwo, pieceThree, pieceFour, pieceFive, pieceSix, pieceSeven, pieceEight, pieceNine, pieceTen;
    private int currentPosition;
    private int pieceCount;
    private List<Integer> loadedImagePositions = new ArrayList<>();
    private View shareContainer;
    private TextView sharingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        item = getIntent().getParcelableExtra(KEY_ITEM);
        wardrobeID = getIntent().getStringExtra(KEY_WARDROBE_ID);
        if (item != null) {
            pieceCount = 0;
            for (String id : item.getPieceIDs()) {
                if (id != null && !id.isEmpty()) {
                    pieceCount++;
                }
            }
        }
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupImages();
        shareContainer = findViewById(R.id.shareContainer);
        sharingText = (TextView) findViewById(R.id.sharingText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        LinkService.getUpdateIntent(OutfitDetailActivity.this, WardrobeFragment.TYPE_OUTFIT,
                                item, wardrobeID, getIntent().getExtras().getBoolean("isDetail")),
                        REQUESTCODE_UPDATE);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    private void setupImages() {
        mainPiece = (ImageView) findViewById(R.id.imageView1);
        mainPiece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pieceID = item.getPieceIDs()[currentPosition];
                startActivity(
                        LinkService.getDetailIntent(OutfitDetailActivity.this, WardrobeFragment.TYPE_PIECE, null, wardrobeID,
                                pieceID, getIntent().getExtras().getBoolean("isDetail")));
            }
        });
        pieceTwo = (ImageView) findViewById(R.id.imageView2);
        setOnClickListeners(pieceTwo, 1);
        pieceThree = (ImageView) findViewById(R.id.imageView3);
        setOnClickListeners(pieceThree, 2);
        pieceFour = (ImageView) findViewById(R.id.imageView4);
        setOnClickListeners(pieceFour, 3);
        pieceFive = (ImageView) findViewById(R.id.imageView5);
        setOnClickListeners(pieceFive, 4);
        pieceSix = (ImageView) findViewById(R.id.imageView6);
        setOnClickListeners(pieceSix, 5);
        pieceSeven = (ImageView) findViewById(R.id.imageView7);
        setOnClickListeners(pieceSeven, 6);
        pieceEight = (ImageView) findViewById(R.id.imageView8);
        setOnClickListeners(pieceEight, 7);
        pieceNine = (ImageView) findViewById(R.id.imageView9);
        setOnClickListeners(pieceNine, 8);
        pieceTen = (ImageView) findViewById(R.id.imageView10);
        setOnClickListeners(pieceTen, 9);
    }

    private void setOnClickListeners(ImageView view, final int pos){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchImages(v, pos);
            }
        });
    }

    @Override
    protected Integer getLayoutRes() {
        return R.layout.activity_outfit_detail;
    }

    @NonNull
    @Override
    public DetailPresenter createPresenter() {
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
            List<Bitmap> bitmapList = new ArrayList<>(10);
            Bitmap[] bitmaps = item.getImages();
            for (int i = 0; i < 10; i++) {
                bitmapList.add(bitmaps[i]);
            }
            mainPiece.setImageBitmap(bitmapList.get(0));
            pieceTwo.setImageBitmap(bitmapList.get(1));
            pieceThree.setImageBitmap(bitmapList.get(2));
            pieceFour.setImageBitmap(bitmapList.get(3));
            pieceFive.setImageBitmap(bitmapList.get(4));
            pieceSix.setImageBitmap(bitmapList.get(5));
            pieceSeven.setImageBitmap(bitmapList.get(6));
            pieceEight.setImageBitmap(bitmapList.get(7));
            pieceNine.setImageBitmap(bitmapList.get(8));
            pieceTen.setImageBitmap(bitmapList.get(9));
        }
    }

    @Override
    public void setData(Object data) {
        // not needed
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadOutfitImages(item.getPieceIDs(), item, pullToRefresh);
    }

    @Override
    public void onImageLoaded(String pieceID) {
        setImage(pieceID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_outfit_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_outfit_detail_share:
                // TODO: sharing here
                sharingText.setVisibility(View.VISIBLE);
                shareContainer.invalidate();
                shareContainer.post(new Runnable() {
                    @Override
                    public void run() {
                        shareContainer.setDrawingCacheEnabled(true);
                        shareContainer.buildDrawingCache(true);
                        Bitmap bitmap = Bitmap.createBitmap(shareContainer.getDrawingCache(true));
                        Intent intent = ShareHelper.startSharing(bitmap, OutfitDetailActivity.this);
                        if (intent != null) {
                            startActivityForResult(intent, 1);
                        } else {
                            Toast.makeText(OutfitDetailActivity.this, "No social media apps installed",
                                    Toast.LENGTH_LONG).show();
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

    private void switchImages(View v, int position){
        Bitmap list[] = item.getImages();
        if(list[position]!=null){
            Bitmap bitmap = list[position];
            Bitmap temp = list[0];
            mainPiece.setImageBitmap(bitmap);
            mainPiece.requestLayout();
            ((ImageView) v).setImageBitmap(temp);
            list[0] = bitmap;
            list[position] = temp;
            item.setImages(list);
            currentPosition = position;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUESTCODE_UPDATE == requestCode && resultCode == RESULT_OK && data != null) {
            WardrobeOutfitItem updatedItem = data.getParcelableExtra(EditOutfitActivity.KEY_ITEM);
            if (updatedItem != null) {
                item = updatedItem;
                pieceCount = 0;
                loadedImagePositions.clear();
                for (String id : item.getPieceIDs()) {
                    if (id != null && !id.isEmpty()) {
                        pieceCount++;
                    }
                }
                getSupportActionBar().setTitle(item.getTitle());
                loadData(true);
            }
        }
    }
}

package de.ur.mi.fashionapp.edit.piece;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import com.soundcloud.android.crop.Crop;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.ImageSlider;
import de.ur.mi.fashionapp.util.ImageSliderController;
import de.ur.mi.fashionapp.wardrobe.model.WardrobePieceItem;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Philip on 05/03/2016.
 */
public class EditPieceActivity
    extends CBActivityMvpToolbar<LinearLayout, Object, EditPieceView, EditPiecePresenter>
    implements EditPieceView, ImageSlider.ImageSliderListener {

  private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
  public static final String KEY_ID = "itemID";

  private WardrobePieceItem editItem;
  private int editItemID;

  private View seasonContainer;
  private View categoryContainer;
  private View colorContainer;
  private View occasionContainer;
  private ImageView uploadImage;

  private String fullSizeImagePath;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);

    // TODO: get parcelable item from bundle if editItemID != 0
    // TODO: create layout; register if(editItemID == 0) createPiece() else updatePiece to on create Button click listener
  }

  @Override protected void onMvpViewCreated() {
    super.onMvpViewCreated();
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    if (editItemID != 0) {
      getSupportActionBar().setDisplayShowTitleEnabled(true);
      getSupportActionBar().setTitle("Edit Item " + editItemID);
    }
    editItemID = getIntent().getIntExtra(KEY_ID, 0);

    seasonContainer = findViewById(R.id.edit_piece_season_container);
    categoryContainer = findViewById(R.id.edit_piece_category_container);
    colorContainer = findViewById(R.id.edit_piece_color_container);
    occasionContainer = findViewById(R.id.edit_piece_occasion_container);
    uploadImage = (ImageView) findViewById(R.id.edit_piece_image);

    uploadImage.setOnClickListener(new OnImageClickListener());

    ImageSliderController sliderController = new ImageSliderController(this, this);
    sliderController.addSlider(seasonContainer, false);
    sliderController.addSlider(categoryContainer, false);
    sliderController.addSlider(colorContainer, true);
    sliderController.addSlider(occasionContainer, true);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_piece_edit, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home: {
        setResult(RESULT_CANCELED);
        finish();
        break;
      }
      case R.id.menu_piece_edit_save:
        setResult(RESULT_OK);
        if (editItemID == 0) {
          createPiece();
        } else {
          updatePiece();
        }
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @NonNull @Override public EditPiecePresenter createPresenter() {
    return new EditPiecePresenter(this, this);
  }

  @Override public void setData(Object data) {
    // not needed
  }

  @Override public void loadData(boolean pullToRefresh) {
    // not needed
  }

  @Override protected Integer getLayoutRes() {
    return R.layout.activity_edit_piece;
  }

  @Override public void onPieceEdited() {
    finish();
  }

  private void createPiece() {
    EditText et = (EditText) findViewById(R.id.edit_piece_name);
    editItem = new WardrobePieceItem();
    editItem.setTitle(et.getText().toString());
    // TODO: get categories and other data from EditTexts and ImageView for the new WardrobePieceItem(editItem, title)
    presenter.createPiece(editItem, true);
  }

  private void updatePiece() {
    // TODO: get data from EditTexts and ImageView for the updated WardrobePieceItem(editItemID, editItem, title)
    presenter.updatePiece(editItemID, editItem, true);
  }

  @Override public void onImageSelected(View root, int id) {
    // TODO: set the selected season/occasion/color/category to the piece
    // TODO: CAUTION: id is null based!
    if (root == seasonContainer) {
      Log.d("EDITPIECE", "Selected season #" + (id + 1));
    } else if (root == categoryContainer) {
      Log.d("EDITPIECE", "Selected category #" + (id + 1));
    } else if (root == occasionContainer) {
      Log.d("EDITPIECE", "Selected occasion #" + (id + 1));
    } else if (root == colorContainer) {
      Log.d("EDITPIECE", "Selected color #" + (id + 1));
    }
  }

  private void beginCrop(Uri source) {
    Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
    Crop.of(source, destination).withAspect(4, 3).start(this);
  }

  private void handleCrop(int resultCode, Intent result) {
    if (resultCode == Activity.RESULT_OK) {
      Uri uri = Crop.getOutput(result);

      // fix for samsung devices which do not save portrait images but instead write rotation instructions in the EXIF header
      Bitmap bitmap = BitmapFactory.decodeFile(uri.getEncodedPath());
      Matrix matrix = new Matrix();
      matrix.postRotate(getImageOrientation(uri.getEncodedPath()));
      Bitmap rotatedBitmap =
          Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

      uploadImage.setImageBitmap(rotatedBitmap);
      uploadImage.invalidate();
      // TODO: save Image to Piece Item, e.g. encoded as Base64 String
    } else if (resultCode == Crop.RESULT_ERROR) {
      Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
    }
  }

  public int getImageOrientation(String imagePath) {
    int rotate = 0;
    try {

      File imageFile = new File(imagePath);
      ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
      int orientation =
          exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

      switch (orientation) {
        case ExifInterface.ORIENTATION_ROTATE_270:
          rotate = 270;
          break;
        case ExifInterface.ORIENTATION_ROTATE_180:
          rotate = 180;
          break;
        case ExifInterface.ORIENTATION_ROTATE_90:
          rotate = 90;
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return rotate;
  }

  private File createImageFile() throws IOException {
    // Create an image file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMANY).format(new Date());
    String imageFileName = "PNG_" + timeStamp + "_";
    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    File image = File.createTempFile(imageFileName,  /* prefix */
        ".png",         /* suffix */
        storageDir      /* directory */);

    fullSizeImagePath = image.getAbsolutePath();

    return image;
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      if (data == null || data.getData() == null) {
        // captured new image
        beginCrop(Uri.fromFile(new File(fullSizeImagePath)));
      } else {
        // picked existing image
        beginCrop(data.getData());
      }
    } else if (requestCode == Crop.REQUEST_CROP) {
      handleCrop(resultCode, data);
    }

    super.onActivityResult(requestCode, resultCode, data);
  }

  private class OnImageClickListener implements View.OnClickListener {

    @Override public void onClick(View v) {
      List<Intent> targetedImageIntents = new ArrayList<>();
      Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
      pickImageIntent.setType("image/*");
      try {
        File imageFile = createImageFile();
        Intent takeImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takeImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(takeImageIntent, 0);
        if (!resInfo.isEmpty()) {
          for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            targetedImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            targetedImageIntent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName,
                resolveInfo.activityInfo.name));
            targetedImageIntent.setPackage(packageName);
            targetedImageIntents.add(targetedImageIntent);
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      Intent chooserIntent = Intent.createChooser(pickImageIntent, "Pick or take an image");
      if (targetedImageIntents.size() > 0) {
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
            targetedImageIntents.toArray(new Parcelable[] {}));
      }
      startActivityForResult(chooserIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
  }
}

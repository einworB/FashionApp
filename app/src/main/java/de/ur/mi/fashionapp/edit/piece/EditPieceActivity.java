package de.ur.mi.fashionapp.edit.piece;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
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
import com.woalk.apps.lib.colorpicker.ColorPickerDialog;
import com.woalk.apps.lib.colorpicker.ColorPickerSwatch;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.util.CatWrapper;
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
 *
 * This activity allows the user to create a new or edit an existing piece.
 * If an existing piece is edited, the image of the piece is loaded by the presenter.
 * Via imagesliders the user can select occasion, category and season of the piece. Via a
 * colorpicker dialog the user can select the piece's color.
 * Clicking the save menu button finishes the activity and updates the existing or creates the new
 * piece which is handled by the presenter, too.
 */
public class EditPieceActivity
    extends CBActivityMvpToolbar<LinearLayout, Object, EditPieceView, EditPiecePresenter>
    implements EditPieceView, ImageSlider.ImageSliderListener {

  private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
  public static final String KEY_ITEM = "item";

  private WardrobePieceItem editItem;

  private EditText editTitle;
  private View seasonContainer;
  private View categoryContainer;
  private View occasionContainer;
  private ImageView uploadImage;
  private ImageView colorPickerView;
  private int selectedColor;

  public final static int MAX_LENGTH_PIECE_NAME = 20;
  private String wardrobeID;

  private String fullSizeImagePath;
  int[] container = new int[] { 0, 0, 0, 0 };
  int[] drawableContainer = new int[] {
      R.drawable.ic_icon_accessoires, R.drawable.ic_icon_onepiece, R.drawable.ic_icon_shoe,
      R.drawable.ic_icon_bottom, R.drawable.ic_icon_top
  };

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    wardrobeID = getIntent().getStringExtra("WardrobeID");
    editItem = getIntent().getParcelableExtra(KEY_ITEM);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    editTitle = (EditText) findViewById(R.id.edit_piece_name);
    seasonContainer = findViewById(R.id.edit_piece_season_container);
    categoryContainer = findViewById(R.id.edit_piece_category_container);
    occasionContainer = findViewById(R.id.edit_piece_occasion_container);
    uploadImage = (ImageView) findViewById(R.id.edit_piece_image);
    colorPickerView = (ImageView) findViewById(R.id.edit_piece_color_picker);
    selectedColor = Color.BLACK;

    ImageSliderController sliderController = new ImageSliderController(this, this);
    sliderController.addSlider(seasonContainer, false, 5, ImageSliderController.SLIDER_TYPE_SEASON);
    sliderController.addSlider(categoryContainer, false, 5,
        ImageSliderController.SLIDER_TYPE_CATEGORY);
    sliderController.addSlider(occasionContainer, true, 4,
        ImageSliderController.SLIDER_TYPE_OCCASION);

    if (editItem != null) {
      setContainerValues();
      uploadImage.setTag("already set");
      getSupportActionBar().setDisplayShowTitleEnabled(true);
      getSupportActionBar().setTitle("Edit Item " + editItem.getTitle());
      presenter.loadPieceImage(editItem.getID(), editItem);
      editTitle.setText(editItem.getTitle());
      sliderController.prefill(editItem);
      selectedColor = editItem.getColor();
      colorPickerView.setImageResource(R.drawable.ic_icon_rounded_corners);
      colorPickerView.setBackgroundColor(selectedColor);
    } else {
      uploadImage.setTag("0");
    }

    uploadImage.setOnClickListener(new OnImageClickListener());

    colorPickerView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        ColorPickerDialog dialog =
            ColorPickerDialog.newInstance(R.string.edit_piece_pick_color, new int[] {
                    CatWrapper.COLOR_RED, CatWrapper.COLOR_BLUE, CatWrapper.COLOR_GREEN,
                    CatWrapper.COLOR_YELLOW, CatWrapper.COLOR_BLACK, CatWrapper.COLOR_WHITE,
                    CatWrapper.COLOR_PINK, CatWrapper.COLOR_PURPLE, CatWrapper.COLOR_ORANGE,
                    CatWrapper.COLOR_TURQUOISE
                }, selectedColor, 4, // Number of columns
                ColorPickerDialog.SIZE_SMALL);

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

          @Override public void onColorSelected(int color) {
            selectedColor = color;
            colorPickerView.setImageResource(R.drawable.ic_icon_rounded_corners);
            colorPickerView.setBackgroundColor(selectedColor);
            Log.d("COLORPICKER", "Selected color: " + selectedColor);
          }
        });

        dialog.show(getFragmentManager(), "some_tag");
      }
    });
  }

  private void setContainerValues() {
    container[0] = editItem.getCat();
    container[1] = editItem.getSeason();
    container[2] = editItem.getOccasion();
    container[3] = editItem.getColor();
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
        if (editItem == null) {
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

  @Override public void onImageLoaded() {
    uploadImage.setImageBitmap(editItem.getImage());
  }

  private void createPiece() {
    if (editTitle.getText().toString().length() > 0
        && editTitle.getText().toString().length() < MAX_LENGTH_PIECE_NAME) {
      editItem = new WardrobePieceItem();
      setItemFields();
      setResult(RESULT_OK);
      presenter.createPiece(editItem, true);
    } else {
      if (editTitle.getText().toString().length() <= 0) {
        Toast.makeText(this, "Type in a name", Toast.LENGTH_LONG).show();
      }
      if (editTitle.getText().toString().length() >= MAX_LENGTH_PIECE_NAME) {
        Toast.makeText(this, "Name too long", Toast.LENGTH_LONG).show();
      }
    }
  }

  private void updatePiece() {
    setItemFields();
    Intent intent = new Intent();
    intent.putExtra(KEY_ITEM, editItem);
    setResult(RESULT_OK, intent);
    presenter.updatePiece(editItem.getID(), editItem, true);
  }

  private void setItemFields() {
    EditText et = (EditText) findViewById(R.id.edit_piece_name);
    editItem.setTitle(et.getText().toString());
    if (wardrobeID != null) editItem.setWardrobeID(wardrobeID);
    editItem.setCat(container[0]);
    editItem.setSeason(container[1]);
    editItem.setOccasion(container[2]);
    editItem.setColor(selectedColor);
    Bitmap bitmap =
        BitmapFactory.decodeResource(this.getResources(), drawableContainer[container[0]]);
    Log.d("check", uploadImage.getTag() + "");
    if (uploadImage != null && !uploadImage.getTag().toString().equals("0")) {
      bitmap = ((BitmapDrawable) uploadImage.getDrawable()).getBitmap();
    }
    editItem.setImage(bitmap);
  }

  @Override public void onImageSelected(View root, int id) {
    if (root == seasonContainer) {
      Log.d("EDITPIECE", "Selected season #" + (id + 1));
      container[1] = id;//tag1
    } else if (root == categoryContainer) {
      Log.d("EDITPIECE", "Selected category #" + (id + 1));
      container[0] = id;//category
    } else if (root == occasionContainer) {
      Log.d("EDITPIECE", "Selected occasion #" + (id + 1));
      container[2] = id;//tag2
    }
  }

  private void beginCrop(Uri source) {
    // .asSquare() defines the ratio of the cropped image's sides!!
    Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
    Crop.of(source, destination).asSquare().start(this);
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
      uploadImage.setTag("set");
      uploadImage.invalidate();
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
        if (fullSizeImagePath != null) {
          beginCrop(Uri.fromFile(new File(fullSizeImagePath)));
        } else {
          Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
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
            targetedImageIntents.toArray(new Parcelable[targetedImageIntents.size()]));
      }
      startActivityForResult(chooserIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
  }
}

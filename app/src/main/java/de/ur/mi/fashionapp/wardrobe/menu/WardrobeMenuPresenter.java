package de.ur.mi.fashionapp.wardrobe.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuItem;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 01/03/2016.
 */
public class WardrobeMenuPresenter extends MvpBasePresenter<WardrobeMenuView> {

  private Context context;
  private  List<WardrobeMenuItem> items;
  public static final int MAX_LENGTH_WARDROBE_NAME = 20;

  public WardrobeMenuPresenter(Context context, WardrobeMenuView view) {
    this.context = context;
    attachView(view);
  }

  public void loadMenu() {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Wardrope");
    query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    if (isViewAttached()) {
      getView().showLoading(true);
    }
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, com.parse.ParseException e) {
        if (isViewAttached()) {
          getView().showLoading(false);
        }
        if (e == null) {
          items = new ArrayList<>();
          for (int i = 0; i < objects.size(); i++) {
            WardrobeMenuWardrobeItem item = new WardrobeMenuWardrobeItem();
            item.setID(objects.get(i).getObjectId());
            item.setTitle(objects.get(i).getString("Name"));
            items.add(item);

          }
          if (isViewAttached()) {
            getView().setData(items);
            getView().showContent();
          }
        } else {
          if (e.getCode() == ParseException.CONNECTION_FAILED) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
          } else if (isViewAttached()) getView().showError(e, false);
        }
      }
    });
  }

  public void getFirstWardrobeID(){
    ParseUser user = ParseUser.getCurrentUser();
    String id = user.get("currentWardrobeID").toString();
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Wardrope");
    query.whereEqualTo("objectId", id);
    if (isViewAttached()) {
      getView().showLoading(true);
    }
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects,
          com.parse.ParseException e) {//Find the wardrobes ob the user first
        if (e == null) {
          if (isViewAttached()) {
            getView().showContent();
            getView().onFirstWardrobeLoaded(objects.get(0).getObjectId(),
                objects.get(0).getString("Name"));
          }
        } else {
          if (e.getCode() == ParseException.CONNECTION_FAILED) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
          } else if (isViewAttached()) getView().showError(e, false);
        }
      }
    });
  }

  public void addNewWardrobe(){
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("Name of wardrobe");
    final EditText input = new EditText(context);
    input.setHint("Name");
    builder.setView(input);
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        String wardropeNameEdit = "failure";
        if (input.getText().toString() != null) {
          wardropeNameEdit = input.getText().toString();
        }
        if (wardropeNameEdit.length() > 50)
          Toast.makeText(context, "Failure! Name can't be longer than 50 letters",
              Toast.LENGTH_LONG).show();
        else addNewWardrobe(wardropeNameEdit);
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });
    builder.show();
  }

  private void addNewWardrobe(final String wardropeName){
    //This Method is to create a new wardrope;
    String userID = ParseUser.getCurrentUser().getObjectId();
    final ParseObject wr = new ParseObject("Wardrope");
    wr.put("Name", wardropeName);
    wr.put("UserID", userID);
    if (isViewAttached()) {
      getView().showLoading(true);
    }
    wr.saveInBackground(new SaveCallback() {
      @Override
      public void done(com.parse.ParseException e) {
        if (e == null) {
          if (isViewAttached()) {
            getView().showContent();
          }
          WardrobeMenuWardrobeItem wardrobe = new WardrobeMenuWardrobeItem();
          wardrobe.setTitle(wardropeName);
          wardrobe.setID(wr.getObjectId());
          if (isViewAttached()) {
            getView().onNewWardrobeCreated(wardrobe);
          }
        } else {
          if (e.getCode() == ParseException.CONNECTION_FAILED) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
          } else if (isViewAttached()) getView().showError(e, false);
        }
      }
    });
  }

  public void setCurrentWardrobeID(String ID){
    ParseUser user = ParseUser.getCurrentUser();
    user.put("currentWardrobeID", ID);
    if(isViewAttached())getView().showLoading(true);
    user.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e==null){
          if(isViewAttached())getView().showContent();
        }else {
          if (e.getCode() == ParseException.CONNECTION_FAILED) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
          } else if (isViewAttached()) getView().showError(e, false);
        }
      }
    });
  }

}

package de.ur.mi.fashionapp.wardrobe.menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SaveCallback;

import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuItem;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;

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
    getView().showLoading(true);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, com.parse.ParseException e) {
        getView().showLoading(false);
        if (e == null) {
          items = new ArrayList<>();
          for (int i = 0; i < objects.size(); i++) {
            WardrobeMenuWardrobeItem item = new WardrobeMenuWardrobeItem();
            item.setID(objects.get(i).getObjectId());
            item.setTitle(objects.get(i).getString("Name"));
            items.add(item);

          }
          getView().setData(items);
          getView().showContent();
        } else {
          getView().showError(e, false);
        }
      }
    });
  }

  public void getFirstWardrobeID(){
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Wardrope");
    query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    if (isViewAttached()) {
      getView().showLoading(true);
    }
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, com.parse.ParseException e) {//Find the wardrobes ob the user first
        if (e == null) {
          if (isViewAttached()) {
            getView().showContent();
            getView().onFirstWardrobeLoaded(objects.get(0).getObjectId());
          }
        } else {
          if (isViewAttached()) getView().showError(e, false);
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
      @Override
      public void onClick(DialogInterface dialog, int which) {
        String wardropeNameEdit = "failure";
        if (input.getText().toString() != null) {
          wardropeNameEdit = input.getText().toString();
        }
        final String wardropeName = wardropeNameEdit;

        //This Method is to create a new wardrope;
        String userID = ParseUser.getCurrentUser().getObjectId();
        final ParseObject wr = new ParseObject("Wardrope");
        wr.put("Name", wardropeName);
        wr.put("UserID", userID);
        getView().showLoading(true);
        wr.saveInBackground(new SaveCallback() {
          @Override
          public void done(com.parse.ParseException e) {
            if (e == null) {
              getView().showContent();
              WardrobeMenuWardrobeItem wardrobe = new WardrobeMenuWardrobeItem();
              wardrobe.setTitle(wardropeName);
              wardrobe.setID(wr.getObjectId());
              getView().onNewWardrobeCreated(wardrobe);
            } else {
              getView().showError(e, false);
            }
          }
        });
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });
    builder.show();


  }
}

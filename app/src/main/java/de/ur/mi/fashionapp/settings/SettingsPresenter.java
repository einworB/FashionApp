package de.ur.mi.fashionapp.settings;

import android.content.Context;
import android.widget.Toast;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import java.util.List;

/**
 * Created by Mario on 06.03.2016.
 *
 * this presenter performs the actual deleting or resetting actions on the parse database.
 */
public class SettingsPresenter extends MvpBasePresenter<SettingsView> {
  Context context;

  public SettingsPresenter(Context context, SettingsView view) {
    this.context = context;
    attachView(view);
  }

  public void changeMail(String newEmail) {
    ParseUser user = ParseUser.getCurrentUser();
    user.setEmail(newEmail);
    if (isViewAttached()) {
      getView().showLoading(false);
    }
    user.saveInBackground(new SaveCallback() {
      @Override public void done(com.parse.ParseException e) {
        if (e == null) {
          if (isViewAttached()) {
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

  public void deleteData() {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Wardrope");
    query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, com.parse.ParseException e) {
        for (int i = 0; i < objects.size(); i++) {
          ParseObject tempTest = objects.get(i);
          tempTest.deleteInBackground();
        }
      }
    });
    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Outfit");
    query2.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    query2.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, com.parse.ParseException e) {
        for (int i = 0; i < objects.size(); i++) {
          ParseObject tempTest = objects.get(i);
          tempTest.deleteInBackground();
        }
      }
    });
    ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Piece");
    query3.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    query3.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, com.parse.ParseException e) {
        for (int i = 0; i < objects.size(); i++) {
          ParseObject tempTest = objects.get(i);
          tempTest.deleteInBackground();
        }
      }
    });
    addFirstWardrobe();
  }

  public void addFirstWardrobe() {
    String wardropeName = "My first Wardrobe";

    //This Method is to create a new wardrobe;
    String userID = ParseUser.getCurrentUser().getObjectId();
    ParseObject wr = new ParseObject("Wardrope");
    wr.put("Name", wardropeName);
    wr.put("UserID", userID);
    if (isViewAttached()) {
      getView().showLoading(true);
    }
    wr.saveInBackground(new SaveCallback() {
      @Override public void done(com.parse.ParseException e) {
        if (e != null) {
          if (e.getCode() == ParseException.CONNECTION_FAILED) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
          } else if (isViewAttached()) getView().showError(e, false);
        }
      }
    });
  }

  public void deleteAccount() {
    ParseUser thisIsMe = ParseUser.getCurrentUser();
    thisIsMe.deleteInBackground(new DeleteCallback() {
      @Override public void done(ParseException e) {
        if (isViewAttached()) {
          getView().showLoading(false);
        }
        if (e == null) {
          if (isViewAttached()) {
            getView().onUserDeleted();
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

  public void deleteWardrobe(final WardrobeMenuWardrobeItem wardrobe) {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Wardrope");
    query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    query.whereEqualTo("objectId", wardrobe.getID());
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, com.parse.ParseException e) {
        for (int i = 0; i < objects.size(); i++) {
          ParseObject tempTest = objects.get(i);
          tempTest.deleteInBackground();
        }
      }
    });
    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Outfit");
    query2.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    query2.whereEqualTo("WardropeID", wardrobe.getID());
    query2.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, com.parse.ParseException e) {
        for (int i = 0; i < objects.size(); i++) {
          ParseObject tempTest = objects.get(i);
          tempTest.deleteInBackground();
        }
      }
    });
    ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Piece");
    query3.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    query3.whereEqualTo("WardrobeID", wardrobe.getID());
    query3.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, com.parse.ParseException e) {
        for (int i = 0; i < objects.size(); i++) {
          ParseObject tempTest = objects.get(i);
          tempTest.deleteInBackground();
        }
      }
    });
  }
}

package de.ur.mi.fashionapp.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;

/**
 * Created by Mario on 06.03.2016.
 */
public class SettingsPresenter extends MvpBasePresenter<SettingsView> {
  Context context;

  public SettingsPresenter(Context context, SettingsView view) {
    this.context = context;
    attachView(view);
  }

  public void changeMail(String newEmail) {
    // TODO: remove dialog, dialogs should be built in activity
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("Hier die Email-Adresse eingeben");

    // Set up the input
    final EditText input = new EditText(context);
    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    builder.setView(input);

    // Set up the buttons
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        final String m_Text = input.getText().toString();

        ParseUser user = ParseUser.getCurrentUser();
        user.setEmail(m_Text);
        getView().showLoading(false);
        user.saveInBackground(new SaveCallback() {
          @Override public void done(com.parse.ParseException e) {
            if (e == null) {
              getView().onMailChangeSuccess();
              getView().showContent();
            } else {
              getView().showError(e, false);
            }
          }
        });
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });

    builder.show();
  }
  public void deleteData() {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Wardrope");
    query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, com.parse.ParseException e) {
        for (int i = 0; i < objects.size(); i++) {
          ParseObject tempTest = objects.get(i);
          tempTest.deleteInBackground();
        }
      }
    });
    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Outfit");
    query2.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    query2.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, com.parse.ParseException e) {
        for (int i = 0; i < objects.size(); i++) {
          ParseObject tempTest = objects.get(i);
          tempTest.deleteInBackground();
        }
      }
    });
    ParseQuery<ParseObject> query3 = ParseQuery.getQuery("Piece");
    query3.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    query3.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, com.parse.ParseException e) {
        for (int i = 0; i < objects.size(); i++) {
          ParseObject tempTest = objects.get(i);
          tempTest.deleteInBackground();
        }
      }
    });
    addFirstWardrobe();
  }

  public void addFirstWardrobe(){
    String wardropeName = "My first Wardrobe";

    //This Method is to create a new wardrope;
    String userID = ParseUser.getCurrentUser().getObjectId();
    ParseObject wr = new ParseObject("Wardrope");
    wr.put("Name", wardropeName);
    wr.put("UserID", userID);
    getView().showLoading(true);
    wr.saveInBackground(new SaveCallback() {
      @Override
      public void done(com.parse.ParseException e) {
        if (e != null) {
          getView().showError(e, false);
        }
      }
    });
  }

  public void deleteAccount() {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("Do you really want to delete this user?");

    // Set up the buttons
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        ParseUser thisIsMe = ParseUser.getCurrentUser();
        thisIsMe.deleteInBackground(new DeleteCallback() {
          @Override
          public void done(ParseException e) {
            getView().showLoading(false);
            if (e == null) {
              getView().onUserDeleted();
              getView().showContent();
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

  public void deleteWardrobe(final WardrobeMenuWardrobeItem wardrobe) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("Do you really want to delete this wardobe?");

    // Set up the buttons
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Wardrope");
        query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
        query.whereEqualTo("objectId", wardrobe.getID());
        query.findInBackground(new FindCallback<ParseObject>() {
          @Override
          public void done(List<ParseObject> objects, com.parse.ParseException e) {
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
          @Override
          public void done(List<ParseObject> objects, com.parse.ParseException e) {
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
          @Override
          public void done(List<ParseObject> objects, com.parse.ParseException e) {
            for (int i = 0; i < objects.size(); i++) {
              ParseObject tempTest = objects.get(i);
              tempTest.deleteInBackground();
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

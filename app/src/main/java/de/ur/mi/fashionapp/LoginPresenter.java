package de.ur.mi.fashionapp;

import android.content.Context;
import android.widget.Toast;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Philip on 24/02/2016.
 */
public class LoginPresenter extends MvpBasePresenter<LoginView> {
  Context context;

  public LoginPresenter(Context context) {
    this.context = context;
  }

  /*
  *   Function for calling the login; needs the entries of the edit fields user/name and password
   */
  public void login(String mail, String p) {
    if (mail.length() > 0) {
      if (p.length() > 0) {
        // Call the Parse login method
        ParseUser.logInInBackground(mail, p, new LogInCallback() {
          @Override public void done(ParseUser user, ParseException e) {
            if (e != null) {
              Toast.makeText(context, (CharSequence) "Login hat nicht funktioniert.",
                  Toast.LENGTH_SHORT).show();
            } else {
              Toast.makeText(context, (CharSequence) "Login hat geklappt.", Toast.LENGTH_SHORT)
                  .show();
              // TODO wichtig: asynchron, bei success:
              if (isViewAttached()) {
                getView().onLoginSuccess();
              }
            }
          }
        });
      } else {
        Toast.makeText(context, (CharSequence) "Bitte Passwort eingeben", Toast.LENGTH_SHORT)
            .show();
      }
    } else {
      Toast.makeText(context, (CharSequence) "Bitte Email-Adresse eingeben!", Toast.LENGTH_SHORT)
          .show();
    }
  }

  /*
  *   Function for calling the registering; needs the entries of the edit fields user/name, password and the confirmazation of the password
   */
  public void register(String mail, String p, String p2) {
    if (mail.length() > 0) {
      if (!(p.length() > 0)) {
        Toast.makeText(context, (CharSequence) "Passwort muss mindestens ein Zeichen enthalten",
            Toast.LENGTH_SHORT).show();
      } else if (p.equals(p2)) {
        signup(mail, p);
      } else {
        Toast.makeText(context, (CharSequence) "Bitte zweimal das gleiche Passwort eingeben",
            Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(context, (CharSequence) "Bitte Email-Adresse eingeben!", Toast.LENGTH_SHORT)
          .show();
    }
  }

  // Set up a new Parse user
  private void signup(String mail, String p) {
    ParseUser user = new ParseUser();
    user.setUsername(mail);
    user.setPassword(p);
    user.signUpInBackground(new SignUpCallback() {
      @Override public void done(ParseException e) {
        if (e == null) {
          Toast.makeText(context, (CharSequence) "Registriert", Toast.LENGTH_SHORT).show();
          if (isViewAttached()) {
            getView().onRegisterSuccess();
          }
        } else {
          if (isViewAttached()) {
            getView().showError(e.getCause(), false);
          }
        }
      }
    });
  }
}


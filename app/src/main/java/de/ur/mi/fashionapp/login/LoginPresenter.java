package de.ur.mi.fashionapp.login;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;

public class LoginPresenter extends MvpBasePresenter<LoginView> {
  Context context;

  public LoginPresenter (Context context, LoginView view){
    this.context = context;
    attachView(view);
  }
  /*
  *   Function for calling the login; needs the entries of the edit fields username and password
   */
  public void login(String username, String p) {
    if(username.length()>0) {
      if(p.length()>0) {
        // Call the Parse login method
        getView().showLoading(false);
        ParseUser.logInInBackground(username, p, new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if (e == null) {//if no error occurred
              if (isViewAttached()) {
                getView().onLoginSuccess();
                getView().showContent();
              }
            } else {
              getView().showError(e,false);
            }
          }
        });
      }
      else {
        Toast.makeText(context, (CharSequence) "No valid password", Toast.LENGTH_SHORT).show();
      }
    }
    else{
      Toast.makeText(context, (CharSequence) "No valid username", Toast.LENGTH_SHORT).show();
    }
  }

  /*
  *   Function for calling the registering; needs the entries of the edit fields username, password and email adress
   */
  public void register(String username, String mail, String pw){
    if(mail.length()>0) {
      if(pw.length()>0){
        if(username.length()>0) {
          signup(username, mail, pw);
        }
        else
          Toast.makeText(context, (CharSequence) "Username can't be empty", Toast.LENGTH_SHORT).show();
      }
      else
        Toast.makeText(context, (CharSequence) "Password can't be empty", Toast.LENGTH_SHORT).show();
    }
    else{
      Toast.makeText(context, (CharSequence) "E-mail can't be empty", Toast.LENGTH_SHORT).show();
    }
  }

  // Set up a new Parse user
  private void signup(String username, String mail, String p){
    ParseUser user = new ParseUser();
    user.setEmail(mail);
    user.setUsername(username);
    user.setPassword(p);
    getView().showLoading(false);
    user.signUpInBackground(new SignUpCallback() {
      public void done(ParseException e) {
        if (e == null) {//if no error occurred
          if (isViewAttached()) {
            getView().onRegisterSuccess();
            getView().showContent();
          }
        } else {
          getView().showError(e,false);
        }
      }
    });
  }

  public void resetPassword() {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("Passwort zurücksetzen lassen?");

    // Set up the buttons
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        String email = ParseUser.getCurrentUser().getEmail();
        getView().showLoading(false);
        ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
          @Override
          public void done(com.parse.ParseException e) {
            if (e == null) {
              getView().onPasswordResetSuccess();
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
}


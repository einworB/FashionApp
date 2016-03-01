package de.ur.mi.fashionapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

/**
 * Created by Philip on 24/02/2016.
 */
public class LoginPresenter extends MvpBasePresenter<LoginView> {
    Context context;

  public LoginPresenter (Context context){
    this.context = context;
  }

  public void loadObject() {
    if (isViewAttached()) {
      getView().showLoading(false);
    }
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        if (isViewAttached()) {
          context = MainActivity.getContext();
          getView().setData("test");
          getView().showContent();
        }
      }
    }, 5000);
  }


  /*
  *   Function for calling the login; needs the entries of the edit fields username and password
   */
  public void login(String username, String p) {
    if(username.length()>0) {
      if(p.length()>0) {
        // Call the Parse login method
        ParseUser.logInInBackground(username, p, new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if (e == null) {//if no error occurred
              if (isViewAttached()) {
                getView().onLoginSuccess();
              }
            } else {
              Toast.makeText(context, (CharSequence) "Login didn't work", Toast.LENGTH_SHORT).show();
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

    user.signUpInBackground(new SignUpCallback() {
      public void done(ParseException e) {
        if (e == null) {//if no error occurred
          if (isViewAttached()) {
            getView().onRegisterSuccess();
          }
        } else {
            Toast.makeText(context, (CharSequence) "Registering didn't work", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
}


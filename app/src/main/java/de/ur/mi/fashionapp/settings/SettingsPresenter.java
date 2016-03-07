package de.ur.mi.fashionapp.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SaveCallback;

/**
 * Created by Mario on 06.03.2016.
 */
public class SettingsPresenter  extends MvpBasePresenter<SettingsView> {
    Context context;
    public SettingsPresenter(Context context){
        this.context = context;
    }

    public void changeMail(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Hier die Email-Adresse eingeben");

        // Set up the input
        final EditText input = new EditText(context);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String m_Text = input.getText().toString();

                ParseUser user = ParseUser.getCurrentUser();
                user.setEmail(m_Text);
                getView().showLoading(false);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            getView().onMailChangeSuccess();
                            getView().showContent();
                        } else {
                            getView().showError(e,false);
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

    public void resetPassword(){
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
                            getView().showError(e,false);
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
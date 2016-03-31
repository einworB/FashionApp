package de.ur.mi.fashionapp.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import de.ur.mi.fashionapp.R;

/**
 * Created by Philip on 24/02/2016.
 *
 * This fragment allows the user to login via username and password.
 * by clicking on the login button the login attempt is made by the presenter.
 * when the register button is clicked the fragment is replaced by the register fragment by the
 * activity.
 */
public class LoginFragment extends Fragment {

  private EditText usernameEdit;
  private EditText passwordEdit;
  private MaterialDialog.Builder builder;
  private MaterialDialog forgotPasswordDialog;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    return inflater.inflate(R.layout.fragment_login, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    usernameEdit = (EditText) view.findViewById(R.id.username_edit);
    passwordEdit = (EditText) view.findViewById(R.id.password_edit);
    builder = new MaterialDialog.Builder(getActivity()).title(R.string.forgot_password_dialog_title)
        .content(R.string.forgot_password_dialog_content)
        .positiveText(R.string.dialog_positive)
        .negativeText(R.string.dialog_negative);
    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
      @Override public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        ((LoginActivity) getActivity()).resetPassword(usernameEdit.toString());
      }
    });

    passwordEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
          if (usernameEdit.getText().toString().length() > 30) {
            Toast.makeText(getContext(), "Username too long", Toast.LENGTH_LONG).show();
          } else if (passwordEdit.getText().toString().length() > 30) {
            Toast.makeText(getContext(), "Password too long", Toast.LENGTH_LONG).show();
          } else {
            ((LoginActivity) getActivity()).performLogin(String.valueOf(usernameEdit.getText()),
                String.valueOf(passwordEdit.getText()));
          }
        }
        return true;
      }
    });
    view.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (usernameEdit.getText().toString().length() > 30) {
          Toast.makeText(getContext(), "Username too long", Toast.LENGTH_LONG).show();
        } else if (passwordEdit.getText().toString().length() > 30) {
          Toast.makeText(getContext(), "Password too long", Toast.LENGTH_LONG).show();
        } else {
          ((LoginActivity) getActivity()).performLogin(String.valueOf(usernameEdit.getText()),
              String.valueOf(passwordEdit.getText()));
        }
      }
    });
    view.findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ((LoginActivity) getActivity()).openRegisterFragment();
      }
    });

    view.findViewById(R.id.forgot_pswd).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (usernameEdit.getText() == null || usernameEdit.getText().toString().isEmpty()) {
          Toast.makeText(getContext(), "Insert Username please", Toast.LENGTH_LONG).show();
        } else {
          forgotPasswordDialog = builder.build();
          forgotPasswordDialog.show();
        }
      }
    });
  }
}

package de.ur.mi.fashionapp.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import de.ur.mi.fashionapp.R;

/**
 * Created by Philip on 24/02/2016.
 */
public class LoginFragment extends Fragment {

  private EditText usernameEdit;
  private EditText passwordEdit;
  private MaterialDialog.Builder builder;
  private MaterialDialog forgotPasswordDialog;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.fragment_login, container, false);
    return v;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    usernameEdit = (EditText) view.findViewById(R.id.username_edit);
    passwordEdit = (EditText) view.findViewById(R.id.password_edit);
    builder = new MaterialDialog.Builder((LoginActivity)getActivity())
            .title(R.string.forgot_password_dialog_title)
            .content(R.string.forgot_password_dialog_content)
            .positiveText(R.string.dialog_positive)
            .negativeText(R.string.dialog_negative);
    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
          ((LoginActivity) getActivity()).resetPassword(usernameEdit.toString());
          // TODO: show error if username is empty
        }
    });

    view.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((LoginActivity) getActivity()).performLogin(String.valueOf(usernameEdit.getText()),
                    String.valueOf(passwordEdit.getText()));
        }
    });
    view.findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((LoginActivity) getActivity()).openRegisterFragment();
        }
    });

    view.findViewById(R.id.forgot_pswd).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            forgotPasswordDialog = builder.build();
            forgotPasswordDialog.show();
        }
    });
  }
}

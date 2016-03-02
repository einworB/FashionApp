package de.ur.mi.fashionapp.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import de.ur.mi.fashionapp.R;

/**
 * Created by Philip on 24/02/2016.
 */
public class LoginFragment extends Fragment {

  private EditText usernameEdit;
  private EditText passwordEdit;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.fragment_login, container, false);

    return v;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    usernameEdit = (EditText) view.findViewById(R.id.username_edit);
    passwordEdit = (EditText) view.findViewById(R.id.password_edit);
    view.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ((LoginActivity) getActivity()).performLogin(String.valueOf(usernameEdit.getText()),
            String.valueOf(passwordEdit.getText()));
      }
    });
    view.findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ((LoginActivity) getActivity()).openRegisterFragment();
      }
    });

    view.findViewById(R.id.forgot_pswd).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //TODO: show "send new password dialog"
      }
    });
  }
}

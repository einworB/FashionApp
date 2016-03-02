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
public class RegisterFragment extends Fragment {

  private EditText usernameEdit;
  private EditText passwordEdit;
  private EditText confirmPasswordEdit;
  private EditText emailEdit;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.fragment_register, container, false);

    return v;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    usernameEdit = (EditText) view.findViewById(R.id.username_edit);
    passwordEdit = (EditText) view.findViewById(R.id.password_edit);
    confirmPasswordEdit = (EditText) view.findViewById(R.id.password_confirm_edit);
    emailEdit = (EditText) view.findViewById(R.id.email_edit);
    view.findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (String.valueOf(confirmPasswordEdit.getText())
            .equals(String.valueOf(passwordEdit.getText()))) {
          ((LoginActivity) getActivity()).performRegister(String.valueOf(usernameEdit.getText()),
              String.valueOf(passwordEdit.getText()), String.valueOf(emailEdit.getText()));
        }
      }
    });
  }
}

package de.ur.mi.fashionapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

    View v = inflater.inflate(R.layout.fragment_login, container, false);

    return v;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //usernameEdit = view.findViewById(R.id.usernameEdit);
    //passwordEdit = view.findViewById(R.id.passwordEdit);
    //confirmPasswordEdit = view.findViewById(R.id.confirmPasswordEdit);
    //emailEdit = view.findViewById(R.id.emailEdit);
    //view.findViewById(R.id.registerBtn).setOnClickListener(new View.OnClickListener() {
    //  @Override public void onClick(View v) {
    //    if (String.valueOf(confirmPasswordEdit.getText())
    //        .equals(String.valueOf(passwordEdit.getText()))) {
    //      ((MainActivity) getActivity()).performRegister(String.valueOf(usernameEdit.getText()),
    //          String.valueOf(passwordEdit.getText()), String.valueOf(emailEdit.getText()));
    //    }
    //  }
    //});
  }
}

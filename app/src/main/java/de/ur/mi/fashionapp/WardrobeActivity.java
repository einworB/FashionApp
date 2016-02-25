package de.ur.mi.fashionapp;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.christianbahl.appkit.core.activity.CBActivityToolbar;

/**
 * Created by Philip on 24/02/2016.
 */
public class WardrobeActivity extends CBActivityToolbar {

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    setTitle("Mein Kleiderschrank");
  }
}

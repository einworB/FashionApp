package de.ur.mi.fashionapp.util;

import android.app.Application;
import com.parse.Parse;

/**
 * Created by Philip on 10/03/2016.
 */
public class ParseApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();

    //Parse initialization
    Parse.enableLocalDatastore(this);
    Parse.initialize(this, "TWxq1vLfTKthoZe7ZT2QaWd3EVjMFB4GN2QEjfkf", "GqctpYG2rJTGmf9vQxdRfG582D8dg01i1PnbBadS");
  }
}

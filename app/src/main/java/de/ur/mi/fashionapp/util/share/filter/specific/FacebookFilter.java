package de.ur.mi.fashionapp.util.share.filter.specific;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Philip on 22/03/2016.
 */
public class FacebookFilter implements AppSpecificFilter {

  @Override public String getPackageName() {
    return "com.facebook.katana";
  }

  @Override public void fillIntent(Intent intent, String subject, String webUrl, Uri iconUri) {
    intent.putExtra(Intent.EXTRA_TEXT, subject + " " + webUrl);
  }

  @Override public void fillIntent(Intent intent, Uri iconUri) {
    if (iconUri != null) {
      intent.putExtra(Intent.EXTRA_STREAM, iconUri);
    }
  }
}

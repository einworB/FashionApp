package de.ur.mi.fashionapp.util.share.filter.specific;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Philip on 22/03/2016.
 */
public class TwitterFilter implements AppSpecificFilter {

  @Override public String getPackageName() {
    return "com.twitter.android";
  }

  @Override public void fillIntent(Intent intent, String subject, String webUrl, Uri iconUri) {
    intent.putExtra(Intent.EXTRA_TEXT, subject + " " + webUrl);
    if (iconUri != null) {
      intent.putExtra(Intent.EXTRA_STREAM, iconUri);
    }
  }
}

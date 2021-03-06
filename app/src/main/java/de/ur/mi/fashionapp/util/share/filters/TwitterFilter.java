package de.ur.mi.fashionapp.util.share.filters;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Philip on 22/03/2016.
 *
 * Specific filter for the twitter app
 */
public class TwitterFilter implements AppSpecificFilter {

  @Override public String getPackageName() {
    return "com.twitter.android";
  }

  @Override public void fillIntent(Intent intent, Uri iconUri) {
    if (iconUri != null) {
      intent.putExtra(Intent.EXTRA_STREAM, iconUri);
    }
  }
}

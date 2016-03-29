package de.ur.mi.fashionapp.util.share.filter.specific;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Philip on 22/03/2016.
 */
public interface AppSpecificFilter {

  public String getPackageName();

  public void fillIntent(Intent intent, String subject, String webUrl, Uri imageUri);

  public void fillIntent(Intent intent, Uri imageUri);
}

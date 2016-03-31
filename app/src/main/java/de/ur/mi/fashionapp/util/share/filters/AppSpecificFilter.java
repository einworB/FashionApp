package de.ur.mi.fashionapp.util.share.filters;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by Philip on 22/03/2016.
 *
 * Interface App specific filters have to implement
 */
public interface AppSpecificFilter {

  String getPackageName();

  void fillIntent(Intent intent, Uri imageUri);
}

package de.ur.mi.fashionapp.util.share.filter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import java.io.File;
import java.util.List;

/**
 * Created by Philip on 22/03/2016.
 */
public interface ShareFilter {

  public boolean containsPackageName(String packageName);

  public List<Intent> createIntentList(PackageManager manager, String subject, String text,
      Uri iconUri, File file, Context context);

}

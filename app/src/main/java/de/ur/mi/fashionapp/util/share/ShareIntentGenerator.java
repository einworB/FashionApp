package de.ur.mi.fashionapp.util.share;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import de.ur.mi.fashionapp.util.share.filter.specific.AppSpecificFilter;
import de.ur.mi.fashionapp.util.share.filter.specific.FacebookFilter;
import de.ur.mi.fashionapp.util.share.filter.specific.TwitterFilter;
import de.ur.mi.fashionapp.util.share.filter.specific.WhatsappFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 22/03/2016.
 */
public class ShareIntentGenerator {

  private List<AppSpecificFilter> specificFilters;
  private Context context;

  public ShareIntentGenerator(Context context) {

    specificFilters = new ArrayList<>();
    specificFilters.add(new TwitterFilter());
    specificFilters.add(new WhatsappFilter());
    specificFilters.add(new FacebookFilter());

    this.context = context;
  }

  public Intent generateShareChooserIntent(Uri iconUri, File file) {
    PackageManager packageManager = context.getPackageManager();
    List<Intent> targetedShareIntents = new ArrayList<>();
    targetedShareIntents.addAll(getSocialMediaIntents(iconUri, packageManager));

    if (targetedShareIntents.size() > 0) {
      Intent chooser = Intent.createChooser(targetedShareIntents.remove(0), "Share");
      LabeledIntent[] extraIntents =
          targetedShareIntents.toArray(new LabeledIntent[targetedShareIntents.size()]);
      chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
      return chooser;
    }
    else return null;
  }

  private List<Intent> getSocialMediaIntents(Uri iconUri, PackageManager packageManager) {
    List<Intent> targetedShareIntents = new ArrayList<>();
    Intent tmp = new Intent(Intent.ACTION_SEND);
    tmp.setType("image/*");
    List<ResolveInfo> resInfo = packageManager.queryIntentActivities(tmp, 0);
    if (!resInfo.isEmpty()) {
      for (ResolveInfo info : resInfo) {
        String packageName = info.activityInfo.packageName;
        if (specificFilters != null) {
          for (AppSpecificFilter filter : specificFilters) {
            if (filter.getPackageName().equals(packageName)) {
              Intent share = new Intent(Intent.ACTION_SEND);
              share.setComponent(new ComponentName(packageName, info.activityInfo.name));
              share.setType("image/*");
              share.setPackage(packageName);
              filter.fillIntent(share, iconUri);
              targetedShareIntents.add(
                  new LabeledIntent(share, packageName, info.loadLabel(packageManager), info.icon));
            }
          }
        }
      }
    }
    return targetedShareIntents;
  }
}

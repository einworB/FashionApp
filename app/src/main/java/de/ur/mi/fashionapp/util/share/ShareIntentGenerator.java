package de.ur.mi.fashionapp.util.share;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import de.ur.mi.fashionapp.util.share.filter.EmailFilter;
import de.ur.mi.fashionapp.util.share.filter.ShareFilter;
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

  private List<ShareFilter> filters;
  private List<AppSpecificFilter> specificFilters;
  private Context context;

  public ShareIntentGenerator(Context context) {
    filters = new ArrayList<>();
    filters.add(new EmailFilter());

    specificFilters = new ArrayList<>();
    specificFilters.add(new TwitterFilter());
    specificFilters.add(new WhatsappFilter());
    specificFilters.add(new FacebookFilter());

    this.context = context;
  }

  public Intent generateShareChooserIntent(String webUrl, String title, String htmlText,
      Uri iconUri, File file) {
    PackageManager packageManager = context.getPackageManager();
    List<Intent> targetedShareIntents = new ArrayList<>();
    targetedShareIntents.addAll(
        getSendIntentsWithoutFilters(webUrl, title, iconUri, packageManager));
    targetedShareIntents.addAll(getFilteredIntents(title, htmlText, iconUri, file, packageManager));

    Intent chooser = Intent.createChooser(targetedShareIntents.remove(0), "Share");
    LabeledIntent[] extraIntents =
        targetedShareIntents.toArray(new LabeledIntent[targetedShareIntents.size()]);
    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
    return chooser;
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

  private List<Intent> getFilteredIntents(String title, String htmlText, Uri iconUri, File file,
      PackageManager packageManager) {
    List<Intent> intents = new ArrayList<>();
    for (ShareFilter filter : filters) {
      intents.addAll(
          filter.createIntentList(packageManager, title, htmlText, iconUri, file, context));
    }
    return intents;
  }

  private List<Intent> getSendIntentsWithoutFilters(String webUrl, String title, Uri iconUri,
      PackageManager packageManager) {
    List<Intent> targetedShareIntents = new ArrayList<>();
    Intent tmp = new Intent(Intent.ACTION_SEND);
    tmp.setType("text/plain");
    List<ResolveInfo> resInfo = packageManager.queryIntentActivities(tmp, 0);
    if (!resInfo.isEmpty()) {
      for (ResolveInfo info : resInfo) {
        String packageName = info.activityInfo.packageName;
        // exclude email apps - they are using the email intent later
        boolean isFiltered = false;
        if (filters != null) {
          for (ShareFilter filter : filters) {
            if (filter.containsPackageName(packageName)) {
              isFiltered = true;
              break;
            }
          }
        }
        if (!isFiltered) {
          Intent share = new Intent(Intent.ACTION_SEND);
          share.setComponent(new ComponentName(packageName, info.activityInfo.name));
          share.setType("text/plain");
          share.setPackage(packageName);
          addTextBasedOnPackage(webUrl, title, packageName, share, iconUri);
          targetedShareIntents.add(
              new LabeledIntent(share, packageName, info.loadLabel(packageManager), info.icon));
        }
      }
    }
    return targetedShareIntents;
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

  private void addTextBasedOnPackage(String webUrl, String title, String packageName, Intent share,
      Uri iconUri) {
    if (specificFilters != null) {
      for (AppSpecificFilter filter : specificFilters) {
        if (filter.getPackageName().equals(packageName)) {
          filter.fillIntent(share, title, webUrl, iconUri);
          return;
        }
      }
    }
    share.putExtra(Intent.EXTRA_TEXT, webUrl);
  }
}

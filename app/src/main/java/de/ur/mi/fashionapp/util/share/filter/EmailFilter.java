package de.ur.mi.fashionapp.util.share.filter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.Html;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 22/03/2016.
 */
public class EmailFilter implements ShareFilter {

  public static final String MIMETYPE_HTML = "text/html";
  private List<String> emailApps;

  public EmailFilter() {
    initEmailFilter();
  }

  private void initEmailFilter() {
    emailApps = new ArrayList<>();
    emailApps.add("com.sec.android.email"); // SAMSUNG EMAIL APP
    emailApps.add("com.htc.android.mail"); // HTC EMAIL APP
    emailApps.add("com.google.android.gm"); // GMAIL
    emailApps.add("com.google.android.exchange"); // EMAIL NATIVE 4.0 ABOVE
    emailApps.add("com.google.android.apps.inbox"); // INBOX
    emailApps.add("com.cloudmagic.mail"); // CloudMagic Email
    emailApps.add("com.yahoo.mobile.client.android.mail"); // Yahoo Mail
    emailApps.add("com.my.mail"); // MyMail
    emailApps.add("com.appple.app.email"); // Email mail box fast mail
    emailApps.add("com.microsoft.office.outlook"); // Outlook
    emailApps.add("com.trtf.blue"); // Email TypeApp Mail
    emailApps.add("me.bluemail.mail"); // Blue Mail
    emailApps.add("com.fsck.k9"); // K-9 Mail
    emailApps.add("de.gmx.mobile.android.mail"); // GMX
    emailApps.add("de.web.mobile.android.mail"); // WEB.DE
    emailApps.add("de.telekom.mail"); // Telekom Mail
    emailApps.add("com.boxer.email"); // Boxer
    emailApps.add("com.android.email"); // SAMSUNG HAS THIS ALSO INSTALLED
  }

  @Override public boolean containsPackageName(String packageName) {
    return emailApps.contains(packageName);
  }

  @Override
  public List<Intent> createIntentList(PackageManager manager, String subject, String text,
      Uri iconUri, File file, Context context) {
    List<Intent> targetedShareIntents = new ArrayList<>();
    Intent tmp = new Intent(Intent.ACTION_SEND);
    tmp.setType(MIMETYPE_HTML);
    List<ResolveInfo> resInfo = manager.queryIntentActivities(tmp, 0);
    for (ResolveInfo info : resInfo) {
      String packageName = info.activityInfo.packageName;
      if (containsPackageName(packageName)) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(MIMETYPE_HTML);
        share.setComponent(new ComponentName(packageName, info.activityInfo.name));
        share.setPackage(packageName);
        share.putExtra(Intent.EXTRA_SUBJECT, subject);
        share.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(text));

        if (iconUri != null && file != null) {
          share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
          share.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
          String mimetype = context.getContentResolver().getType(iconUri);
          //share.setData(iconURI);
          share.setType(mimetype);
          if (packageName.equalsIgnoreCase("com.google.android.gm")) {
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
          } else {
            share.putExtra(Intent.EXTRA_STREAM, iconUri);
          }
        }
        targetedShareIntents.add(
            new LabeledIntent(share, packageName, info.loadLabel(manager), info.icon));
      }
    }

    return targetedShareIntents;
  }
}

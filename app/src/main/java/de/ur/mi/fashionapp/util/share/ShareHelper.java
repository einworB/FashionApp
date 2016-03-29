package de.ur.mi.fashionapp.util.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Philip on 22/03/2016.
 */
public class ShareHelper {
  public static Intent startSharing(Bitmap bitmap, Context context) {
    if (bitmap != null) {

      FileOutputStream out = null;
      try {
        File file =
            new File(context.getFilesDir(), "fashionShare_" + System.currentTimeMillis() + ".png");

        file.getParentFile().mkdirs();
        file.setReadable(true, false);
        file.createNewFile();

        out = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        Uri iconURI = FileProvider.getUriForFile(context, "de.ur.mi.fashionapp.FileProvider", file);

        ShareIntentGenerator generator = new ShareIntentGenerator(context);
        Intent chooser = generator.generateShareChooserIntent(iconURI, file);

        return chooser;
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          if (out != null) {
            out.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      Log.d("SHARE", "FUCKFUCKFUCK");
    }
    return null;
  }

  public static Intent startSharing(final String teaser, final String webUrl, final String title,
      Bitmap bitmap, Context context) {
    if (bitmap != null) {

      FileOutputStream out = null;
      try {
        File file =
            new File(context.getFilesDir(), "fashionShare_" + System.currentTimeMillis() + ".png");

        file.getParentFile().mkdirs();
        file.setReadable(true, false);
        file.createNewFile();

        out = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        Uri iconURI = FileProvider.getUriForFile(context, "de.ur.mi.fashionapp.FileProvider", file);

        String htmlText = getHTMLForMailSharing(context);
        htmlText = htmlText.replaceAll("\\$\\{title\\}", title);
        htmlText = htmlText.replaceAll("\\$\\{teaser\\}", teaser);
        htmlText = htmlText.replaceAll("\\$\\{webUrl\\}", webUrl);

        ShareIntentGenerator generator = new ShareIntentGenerator(context);
        Intent chooser =
            generator.generateShareChooserIntent(webUrl, title, htmlText, iconURI, file);

        return chooser;
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          if (out != null) {
            out.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      Log.d("SHARE", "FUCKFUCKFUCK");
    }
    return null;
  }

  private static String getHTMLForMailSharing(Context context) {
    try {
      InputStream inputstream = context.getAssets().open("html/mailshare.html");
      InputStreamReader inputStreamReader = new InputStreamReader(inputstream);
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        sb.append(line).append("\n");
      }
      bufferedReader.close();
      return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
  }
}

package de.ur.mi.fashionapp.extras;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import de.ur.mi.fashionapp.R;

/**
 * This activity tries to give users a short tour of the app.
 */
public class HelpActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_help);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    ImageView playVideoBtn = (ImageView) findViewById(R.id.play_video_button);
    playVideoBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String url = getResources().getString(R.string.help_video_url);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home: {
        finish();
        break;
      }
    }
    return super.onOptionsItemSelected(item);
  }
}

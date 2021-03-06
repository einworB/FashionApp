package de.ur.mi.fashionapp.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import java.util.List;

/**
 * This activity enables the user to perform changes on his user account like changing his email
 * address or deleting the data or even his account.
 * It is also possible to delete one or more of the user's wardrobes, here.
 * The database actions like deleting data or reseting email are handled by the presenter
 */
public class SettingsActivity extends
    CBActivityMvpToolbar<RecyclerView, List<WardrobeMenuWardrobeItem>, SettingsView, SettingsPresenter>
    implements SettingsView, SettingsAdapter.SettingsAdapterListener {

  public static final String EXTRAS_WARDROBES = "wardrobes";
  public static final int RESULTCODE_WARDROBE_DELETED = 201;
  private MaterialDialog changeEmailDialog;
  private SettingsAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    TableRow changeEmail = (TableRow) findViewById(R.id.change_email);
    TableRow deleteData = (TableRow) findViewById(R.id.delete_data);
    TableRow deleteAccount = (TableRow) findViewById(R.id.delete_account);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    setupDialogues(deleteData, R.string.delete_data_dialog_title,
        R.string.delete_data_dialog_content);
    setupDialogues(deleteAccount, R.string.delete_account_dialog_title,
        R.string.delete_account_dialog_content);
    MaterialDialog.Builder builder =
        new MaterialDialog.Builder(this).title(R.string.change_email_dialog_title)
            .content(R.string.change_email_dialog_content)
            .positiveText(R.string.dialog_positive)
            .negativeText(R.string.dialog_negative)
            .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            .input(R.string.change_email_input_hint, R.string.change_email_input_prefill,
                new MaterialDialog.InputCallback() {
                  @Override
                  public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    // not needed
                  }
                });
    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
      @Override public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        if (dialog.getInputEditText() != null) {
          presenter.changeMail(dialog.getInputEditText().getText().toString());
        }
      }
    });
    changeEmailDialog = builder.build();
    changeEmail.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        changeEmailDialog.show();
      }
    });

    adapter = new SettingsAdapter(this, this);
    contentView.setAdapter(adapter);
    contentView.setLayoutManager(new LinearLayoutManager(this));
    List<WardrobeMenuWardrobeItem> wardrobes =
        getIntent().getParcelableArrayListExtra(EXTRAS_WARDROBES);
    if (wardrobes != null) {
      adapter.setItems(wardrobes);
      adapter.notifyDataSetChanged();
    }
  }

  @NonNull @Override public SettingsPresenter createPresenter() {
    return new SettingsPresenter(this, this);
  }

  private void setupDialogues(TableRow dialogView, final int dialog_title,
      final int dialog_content) {
    MaterialDialog.Builder builder = new MaterialDialog.Builder(this).title(dialog_title)
        .content(dialog_content)
        .positiveText(R.string.dialog_positive)
        .negativeText(R.string.dialog_negative);
    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
      @Override public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        String title = getString(dialog_title);
        if (title.contains("Data")) {
          presenter.deleteData();
        } else if (title.contains("Account")) {
          presenter.deleteAccount();
        }
      }
    });
    final MaterialDialog finalDialog = builder.build();
    dialogView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        finalDialog.show();
      }
    });
  }

  @Override public void setData(List<WardrobeMenuWardrobeItem> data) {
    adapter.setItems(data);
  }

  @Override public void loadData(boolean pullToRefresh) {
    // not needed here
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

  public void onUserDeleted() {

  }

  // delete icon of wardrobe has been clicked
  @Override public void onWardrobeDeleted(WardrobeMenuWardrobeItem wardrobe) {
    presenter.deleteWardrobe(wardrobe);
    setResult(RESULTCODE_WARDROBE_DELETED);
  }
}


package de.ur.mi.fashionapp.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbar;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import java.util.List;

public class SettingsActivity
    extends CBActivityMvpToolbar<RecyclerView, List<WardrobeMenuWardrobeItem>, SettingsView, SettingsPresenter>
    implements SettingsView {

  private TableRow changePassword, deleteData, deleteAccount;
  private MaterialDialog changePasswordDialog, deleteDataDialog, deleteAccountDialog;
  private SettingsAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    changePassword = (TableRow) findViewById(R.id.change_password);
    deleteData = (TableRow) findViewById(R.id.delete_data);
    deleteAccount = (TableRow) findViewById(R.id.delete_account);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    setupDialogues(changePassword, changePasswordDialog, R.string.change_password_dialog_title,
        R.string.change_password_dialog_content);
    setupDialogues(deleteData, deleteDataDialog, R.string.delete_data_dialog_title,
        R.string.delete_data_dialog_content);
    setupDialogues(deleteAccount, deleteAccountDialog, R.string.delete_account_dialog_title,
        R.string.delete_account_dialog_content);
    adapter = new SettingsAdapter(this);
  }

  @NonNull @Override public SettingsPresenter createPresenter() {
    return new SettingsPresenter(this, this);
  }

  private void setupDialogues(TableRow dialogView, MaterialDialog dialog, final int dialog_title,
      final int dialog_content) {
    MaterialDialog.Builder builder = new MaterialDialog.Builder(this).title(dialog_title)
        .content(dialog_content)
        .positiveText(R.string.dialog_positive)
        .negativeText(R.string.dialog_negative);
    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
      @Override public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        // TODO use better differentiation
        String title = getString(dialog_title);
        if (title.contains("Password")) {
          // TODO: implement other dialog that has edittext for old and new password; check old password before setting new
          presenter.setNewPassword("");
        }
        else if (title.contains("Data")) {
          presenter.deleteData();
        }
        else if (title.contains("Account")) {
          presenter.deleteAccount();
        }
        // TODO: dialog for changeMail(String newEmail)
      }
    });
    dialog = builder.build();
    final MaterialDialog finalDialog = dialog;
    dialogView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        finalDialog.show();
      }
    });
  }

  @Override public void setData(List<WardrobeMenuWardrobeItem> data) {
    // this should be called from the presenter.loadWardr
    adapter.setItems(data);

  }

  @Override public void loadData(boolean pullToRefresh) {
    // not needed here (yet); wardrobes will be automatically loaded here later
  }

  @Override public void onMailChangeSuccess() {
    // TODO: do something on success or remove this function(from the view interface)
  }

  @Override public void onPasswordResetSuccess() {
    // TODO: do something on success or remove this function(from the view interface)
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

  public void onUserDeleted(){

  }
}


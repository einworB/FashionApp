package de.ur.mi.fashionapp.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import de.ur.mi.fashionapp.R;

public class SettingsActivity extends AppCompatActivity {

    private TableRow changePassword, deleteData, deleteAccount;
    private MaterialDialog changePasswordDialog, deleteDataDialog, deleteAccountDialog;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        changePassword = (TableRow) findViewById(R.id.change_password);
        deleteData = (TableRow) findViewById(R.id.delete_data);
        deleteAccount = (TableRow) findViewById(R.id.delete_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDialogues(changePassword, changePasswordDialog, R.string.change_password_dialog_title, R.string.change_password_dialog_content);
        setupDialogues(deleteData, deleteDataDialog, R.string.delete_data_dialog_title, R.string.delete_data_dialog_content);
        setupDialogues(deleteAccount, deleteAccountDialog, R.string.delete_account_dialog_title, R.string.delete_account_dialog_content);
    }

    private void setupDialogues(TableRow dialogView, MaterialDialog dialog, int dialog_title, int dialog_content) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title(dialog_title)
                .content(dialog_content)
                .positiveText(R.string.dialog_positive)
                .negativeText(R.string.dialog_negative);
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                // TODO change password / delete data / delete account
            }
        });
        dialog = builder.build();
        final MaterialDialog finalDialog = dialog;
        dialogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalDialog.show();
            }
        });
    }

}


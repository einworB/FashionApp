package de.ur.mi.fashionapp.settings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;

/**
 * Created by Philip on 10/03/2016.
 */
public class SettingsAdapter extends CBAdapterRecyclerView<WardrobeMenuWardrobeItem> {

  public SettingsAdapter(Context context) {
    super(context);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {

  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }
}

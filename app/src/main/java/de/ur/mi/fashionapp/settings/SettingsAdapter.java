package de.ur.mi.fashionapp.settings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.TextWithIconViewHolder;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;

/**
 * Created by Philip on 10/03/2016.
 *
 * this adapter is responsible for displaying the user's wardrobes
 */
public class SettingsAdapter extends CBAdapterRecyclerView<WardrobeMenuWardrobeItem>
    implements TextWithIconViewHolder.TextWithIconViewHolderListener {

  interface SettingsAdapterListener {
    void onWardrobeDeleted(WardrobeMenuWardrobeItem item);
  }

  private Drawable closeIcon;
  private SettingsAdapterListener listener;

  public SettingsAdapter(Context context, SettingsAdapterListener listener) {
    super(context);
    closeIcon = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      closeIcon =
          context.getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel, null);
    } else {
      closeIcon = context.getResources().getDrawable(android.R.drawable.ic_menu_close_clear_cancel);
    }
    this.listener = listener;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
    WardrobeMenuWardrobeItem item = getItem(position);
    ((TextWithIconViewHolder) viewHolder).bind(item.getTitle(), item.getID(), closeIcon, this);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TextWithIconViewHolder(
        inflater.inflate(R.layout.simple_text_with_icon, parent, false));
  }

  @Override public void onIconClicked(String ID) {
    int removePosition = -1;
    for (int i = 0; i < getItemCount(); i++) {
      WardrobeMenuWardrobeItem item = items.get(i);
      if (item.getID().equals(ID)) {
        removePosition = i;
        items.remove(i);
        listener.onWardrobeDeleted(item);
        // only one item can be removed at a time
        break;
      }
    }
    notifyItemRemoved(removePosition);
  }
}

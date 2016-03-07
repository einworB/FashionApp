package de.ur.mi.fashionapp.wardrobe.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.ui.LinkViewHolder;
import de.ur.mi.fashionapp.ui.NewWardrobeViewHolder;
import de.ur.mi.fashionapp.ui.WardrobeViewHolder;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuItem;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuLinkItem;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuNewWardrobeItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 29/02/2016.
 */
public class WardrobeMenuAdapter extends CBAdapterRecyclerView<WardrobeMenuItem>
    implements LinkViewHolder.LinkViewHolderListener,
    WardrobeViewHolder.WardrobeViewHolderListener,
    NewWardrobeViewHolder.NewWardrobeViewHolderListener{

  private static final int VIEWTYPE_LINK = 0;
  private static final int VIEWTYPE_WARDROBE = 1;
  private static final int VIEWTYPE_NEW_WARDROBE = 2;
  private final WardrobeMenuAdapterListener listener;
  private List<WardrobeMenuItem> defaultItems;

  public interface WardrobeMenuAdapterListener {
    void onLinkClicked(String title);

    void onWardrobeClicked(int ID, String title);

    void onNewWardrobeClicked();
  }

  public WardrobeMenuAdapter(Context context, WardrobeMenuAdapterListener listener) {
    super(context);

    this.listener = listener;

    defaultItems = new ArrayList<>();
    defaultItems.add(new WardrobeMenuNewWardrobeItem("Create new Wardrobe"));
    defaultItems.add(new WardrobeMenuLinkItem("Settings"));
    defaultItems.add(new WardrobeMenuLinkItem("Impressum"));
    defaultItems.add(new WardrobeMenuLinkItem("Help"));
    defaultItems.add(new WardrobeMenuLinkItem("Logout"));
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
    switch (viewType) {
      case VIEWTYPE_LINK:
        ((LinkViewHolder) viewHolder).bind((WardrobeMenuLinkItem) getItem(position), this);
        break;
      case VIEWTYPE_WARDROBE:
        ((WardrobeViewHolder) viewHolder).bind((WardrobeMenuWardrobeItem) getItem(position), this);
        break;
      case VIEWTYPE_NEW_WARDROBE:
        ((NewWardrobeViewHolder) viewHolder).bind((WardrobeMenuNewWardrobeItem) getItem(position), this);
        break;
    }
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case VIEWTYPE_LINK:
        return new LinkViewHolder(inflater.inflate(R.layout.simple_text, parent, false));
      case VIEWTYPE_WARDROBE:
        return new WardrobeViewHolder(inflater.inflate(R.layout.simple_text, parent, false));
      case VIEWTYPE_NEW_WARDROBE:
        return new NewWardrobeViewHolder(inflater.inflate(R.layout.simple_text, parent, false));
      default:
        return null;
    }
  }

  @Override public int getItemViewType(int position) {
    if (getItem(position) instanceof WardrobeMenuLinkItem) {
      return VIEWTYPE_LINK;
    } else if (getItem(position) instanceof WardrobeMenuWardrobeItem) {
      return VIEWTYPE_WARDROBE;
    } else if (getItem(position) instanceof WardrobeMenuNewWardrobeItem) {
      return VIEWTYPE_NEW_WARDROBE;
    }
    return super.getItemViewType(position);
  }

  @Override public void onLinkClicked(String title) {
    if (listener != null) {
      listener.onLinkClicked(title);
    }
  }

  @Override public void onWardrobeClicked(int ID, String title) {
    if (listener != null) {
      listener.onWardrobeClicked(ID, title);
    }
  }

  @Override public void setItems(List<WardrobeMenuItem> items) {
    super.setItems(items);
    addNewItems(defaultItems);
  }

  @Override public void onNewWardrobeClicked() {
    if (listener != null) {
      listener.onNewWardrobeClicked();
    }
  }

}

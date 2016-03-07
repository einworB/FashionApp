package de.ur.mi.fashionapp.wardrobe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.fragment.CBFragmentMvpRecyclerView;
import de.ur.mi.fashionapp.R;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeItem;
import java.util.List;

/**
 * Created by Philip on 29/02/2016.
 */
public class WardrobeFragment extends
    CBFragmentMvpRecyclerView<List<WardrobeItem>, WardrobeView, WardrobePresenter, WardrobeAdapter>
    implements WardrobeView {

  public static final int TYPE_PIECE = 0;
  public static final int TYPE_OUTFIT = 1;

  private int type;
  // TODO: replace dummy ids
  private int wardrobeID = 1;
  private int userID = 1;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View v = inflater.inflate(R.layout.fragment_wardrobe, container, false);

    return v;
  }

  @Override protected WardrobeAdapter createAdapter() {
    return new WardrobeAdapter(getContext());
  }

  public void setType(int type) {
    this.type = type;
  }

  @Override public void setData(List<WardrobeItem> data) {
    adapter.setItems(data);
    adapter.notifyDataSetChanged();
  }

  @Override public void loadData(boolean pullToRefresh) {
    if (type == TYPE_OUTFIT) {
      presenter.loadOutfits(userID, wardrobeID, pullToRefresh);
    } else if (type == TYPE_PIECE) {
      presenter.loadPieces(userID, wardrobeID, pullToRefresh);
    }
  }

  @Override public WardrobePresenter createPresenter() {
    return new WardrobePresenter();
  }

  public void setWardrobe(int ID) {
    wardrobeID = ID;
    loadData(true);
  }

  public int getType() {
    return type;
  }
}

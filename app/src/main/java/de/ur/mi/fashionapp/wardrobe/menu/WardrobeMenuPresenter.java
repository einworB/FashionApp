package de.ur.mi.fashionapp.wardrobe.menu;

import android.content.Context;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuItem;
import de.ur.mi.fashionapp.wardrobe.menu.model.WardrobeMenuWardrobeItem;
import de.ur.mi.fashionapp.wardrobe.model.WardrobeOutfitItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 01/03/2016.
 */
public class WardrobeMenuPresenter extends MvpBasePresenter<WardrobeMenuView> {

  private Context context;
  private  List<WardrobeMenuItem> items;

  public WardrobeMenuPresenter(Context context, WardrobeMenuView view) {
    this.context = context;
    attachView(view);
  }

  public void loadMenu() {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Wardrope");
    query.whereEqualTo("UserID", ParseUser.getCurrentUser().getObjectId());
    getView().showLoading(true);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, com.parse.ParseException e) {
        getView().showLoading(false);
        if (e == null) {
          items = new ArrayList<>();
          for( int i = 0; i<objects.size(); i++) {
            WardrobeMenuWardrobeItem item = new WardrobeMenuWardrobeItem();
            item.setID(objects.get(i).getObjectId());
            item.setTitle(objects.get(i).getString("Name"));
            items.add(item);

          }
          getView().setData(items);
          getView().showContent();
        }
        else{
          getView().showError(e, false);
        }
      }
    });
  }
}

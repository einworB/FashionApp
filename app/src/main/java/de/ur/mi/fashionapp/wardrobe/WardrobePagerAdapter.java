package de.ur.mi.fashionapp.wardrobe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Philip on 29/02/2016.
 */
public class WardrobePagerAdapter extends FragmentPagerAdapter{

  public WardrobePagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public int getCount() {
    return 2;
  }

  @Override public Fragment getItem(int position) {
    WardrobeFragment f = new WardrobeFragment();
    f.setType(position);
    return f;
  }

  @Override public CharSequence getPageTitle(int position) {
    switch (position){
      case 0:
        return "My Pieces";
      case 1:
        return "My Outfits";
      default:
        return super.getPageTitle(position);
    }
  }
}

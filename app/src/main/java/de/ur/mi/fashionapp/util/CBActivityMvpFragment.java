package de.ur.mi.fashionapp.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.christianbahl.appkit.core.R;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbarFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * A variation of the CBActivityMvpToolbarFragment from Christian Bahl's appkit which does not use a
 * toolbar
 *
 * @author Philip Braun
 * @see CBActivityMvpToolbarFragment
 */
public abstract class CBActivityMvpFragment<CV extends View, M, V extends MvpLceView<M>, P extends MvpPresenter<V>>
    extends MvpLceActivity<CV, M, V, P> {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (savedInstanceState == null) {
      Fragment fragment = createFragmentToDisplay();

      if (fragment == null) {
        throw new NullPointerException(
            "Fragment is null. Did you return null in createFragmentToDisplay()?");
      }

      getSupportFragmentManager().beginTransaction().replace(R.id.contentView, fragment).commit();
    }
  }

  /**
   * <p>
   * Returns the {@link Fragment} which should be displayed by this activity.
   * </p>
   *
   * @return {@link Fragment}
   */
  protected abstract Fragment createFragmentToDisplay();

  @Override protected String getErrorMessage(Throwable throwable, boolean isContentVisible) {
    return throwable.getLocalizedMessage();
  }
}

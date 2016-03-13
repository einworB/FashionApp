package de.ur.mi.fashionapp.util;/*
 * Copyright 2015 Christian Bahl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.R;
import com.christianbahl.appkit.core.activity.CBActivityMvpToolbarFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

/**
 * <p>
 * An activity which uses the Model-View-Presenter architecture.<br/>
 * It also has a container for the {@link Fragment}.
 * </p>
 *
 * <p>
 * The layout must have a {@link ViewGroup} for the {@link Fragment} with the id
 * <code>R.layout.contentView</code>
 * </p>
 *
 *
 * <p>
 * You have to override the {@link #createFragmentToDisplay} to create the {@link Fragment} which
 * should be displayed.
 * </p>
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
/*
 * Copyright (c) 2015. All rights reserved.
 * @author Cuong Nguyen
 */

package com.behruz.magmovie.base.presenter;

import android.content.Context;

/**
 * Interface representing a View that will use to load data.
 */
public interface LoadDataView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLineLoading();

    /**
     * Hide a loading view.
     */
    void hideLineLoading();

    /**
     * Show an error message; or sometimes it just displays a message ..
     *
     * @param message A string representing an error.
     */
    void showError(String message);

    /**
     * Get a {@link Context}.
     */
    Context getContext();
}

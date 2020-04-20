package com.behruz.magmovie.base;

import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.behruz.magmovie.base.presenter.Presenter;


public abstract class BaseFragment extends Fragment
    implements View.OnClickListener {

	protected AppApplication mApplication;

	protected FragmentManager mFragmentManager;


	protected Handler mHandler;

	public abstract Presenter getPresenter();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);




		//mApplication = (AppApplication) getActivity().getApplication();


		mFragmentManager = getFragmentManager();

		mHandler = new Handler();
	}

	@Override
	public void onStart() {
		super.onStart();
	}


	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onClick(View v) {

	}

	abstract protected void injectInjector() ;


	@Override
	public void onResume() {
		super.onResume();

		if (null != getPresenter()) {
			getPresenter().resume();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (null != getPresenter()) {
			getPresenter().pause();
		}
	}

	@Override
	public void onDestroy() {
		if (null != getPresenter()) {
			getPresenter().destroy();
		}
		super.onDestroy();
	}


	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		// UserSignUpActivity mainActivity = (UserSignUpActivity) getActivity();
		// mainActivity.setTitleText(title);
	}

	/**
	 * Init all model when onCreate activity here
	 */
	public abstract void initModels();

	/**
	 * Init all views when onCreate activity here
	 */
	public abstract void initViews(View view);

	/**
	 * Remove previous show dialog fragment by tag
	 * 
	 * @param tag
	 *            tag of dialog fragment
	 */
	protected void removePreviousDialog(String tag) {
		// DialogFragment.show() will take care of adding the fragment
		// in a transaction. We also want to remove any currently showing
		// dialog, so make our own transaction and take care of that here.
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		Fragment prev = mFragmentManager.findFragmentByTag(tag);
		if (prev != null) {
			ft.remove(prev);
		}
		ft.commit();
	}

	// /**
	// * show alert message
	// *
	// * @param message
	// */
	// public void showAlertDialog(Context context, String message) {
	// // clear all state previous
	// removePreviousDialog("alert_dialog");
	// mAlertDialog = null;
	// // create dialog
	// mAlertDialog = AlertDialogFragment.newInstance(
	// getString(R.string.app_name), message,
	// getString(android.R.string.ok), getString(android.R.string.ok));
	// mAlertDialog.showOnlyOneButton(true);
	// // show dialog
	// mAlertDialog.show(mFragmentManager, "alert_dialog");
	// }

	// /**
	// * show alert
	// *
	// * @param context
	// * @param title
	// * @param message
	// * @param leftText
	// * @param rightText
	// * @param listener
	// */
	// public void showAlertDialog(Context context, String title, String
	// message,
	// String leftText, String rightText, AlertListener listener) {
	// // clear all state previous
	// removePreviousDialog("alert_dialog");
	// mAlertDialog = null;
	// // create dialog
	// mAlertDialog = AlertDialogFragment.newInstance(context, title, message,
	// leftText, rightText, listener);
	// // show dialog
	// mAlertDialog.show(mFragmentManager, "alert_dialog");
	// }

	/**
	 * Finish current fragment
	 */
	public void finishFragment() {
		try {
			mFragmentManager.popBackStack();
		} catch (Exception e) {
		//	LogUtils.e(e.getMessage());
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
//		super.onCreateOptionsMenu(menu, inflater);
	}
}

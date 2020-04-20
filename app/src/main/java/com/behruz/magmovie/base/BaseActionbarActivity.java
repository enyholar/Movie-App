package com.behruz.magmovie.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;

import com.behruz.magmovie.base.presenter.Presenter;
import com.behruz.magmovie.utils.PreferenUtil.PreferenUtil;


/**
 * Created by Enny on 29/11/2016.
 */
public abstract class BaseActionbarActivity
    extends AppCompatActivity
    implements View.OnClickListener {

  private FragmentManager mFragmentManager;
  public PreferenUtil mPreferenUtil;

  public abstract void initView();

  public abstract void initModel();

  public abstract Presenter getPresenter();


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
 //   NightOwl.owlBeforeCreate(this);
    super.onCreate(savedInstanceState);
 //  NightOwl.owlAfterCreate(this);
//    initTransaction();
    mFragmentManager = getSupportFragmentManager();
    mPreferenUtil = PreferenUtil.getInstant(this);

  }

  /**
   * Switch content tab
   */
  public void switchContent(int contentId, Fragment fragment) {
    FragmentTransaction transaction = mFragmentManager.beginTransaction();
    transaction.replace(contentId, fragment);
    transaction.commit();
  }

  private void initTransaction() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Transition enterTrans = new Explode();
      getWindow().setEnterTransition(enterTrans);
    }
  }


  @Override
  public void onClick(View v) {

  }

  abstract protected void injectInjector();


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

  public boolean isPermissionGranted(Context context, String permission) {
    boolean granted = ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || (
        ContextCompat.checkSelfPermission(context, permission)
            == PackageManager.PERMISSION_GRANTED));
    return granted;
  }

  /**
   * @param context current Context
   * @param permissions String[] permission to ask
   * @return boolean true/false
   */
  public boolean isPermissionsGranted(Context context, String permissions[]) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return true;
    }

    boolean granted = true;

    for (String permission : permissions) {
      if (!(ActivityCompat.checkSelfPermission(context, permission)
          == PackageManager.PERMISSION_GRANTED)) {
        granted = false;
      }
    }

    return granted;
  }


}

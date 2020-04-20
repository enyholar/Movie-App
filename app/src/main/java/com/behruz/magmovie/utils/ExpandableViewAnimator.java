package com.behruz.magmovie.utils;

//import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
//import com.github.aakira.expandablelayout.ExpandableLinearLayout;
//import com.github.aakira.expandablelayout.Utils;

/**
 * Created by vietthangif on 11/29/17.
 */

public class ExpandableViewAnimator {

//  private View arrowView;
//
//  public ExpandableViewAnimator(View arrowView) {
//    this.arrowView = arrowView;
//  }
//
//  public ExpandableViewAnimator(ExpandableLinearLayout expandableLinearLayout, View arrowView) {
//    this.arrowView = arrowView;
//    expandableLinearLayout.setListener(new ExpandableLayoutListenerAdapter() {
//      @Override
//      public void onPreOpen() {
//        openSmooth();
//      }
//
//      @Override
//      public void onPreClose() {
//        closeSmooth();
//      }
//    });
//    arrowView.setRotation(expandableLinearLayout.isExpanded() ? 180f : 0f);
//  }
//
//  public void openSmooth() {
//    createRotateAnimator(arrowView, 0f, 180f, 300).start();
//  }
//
//  public void closeSmooth() {
//    createRotateAnimator(arrowView, 180f, 0f, 300).start();
//  }
//
//  public void open() {
//    createRotateAnimator(arrowView, 0f, 180f, 0).start();
//  }
//
//  public void close() {
//    createRotateAnimator(arrowView, 180f, 0f, 0).start();
//  }
//
//  public ObjectAnimator createRotateAnimator(final View target, final float from, final float to,
//                                             long duration) {
//    ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
//    animator.setDuration(duration);
//    animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
//    return animator;
//  }
}

package com.riocallos.itunessearch.utils;

import android.content.Context;

import com.riocallos.itunessearch.R;

/**
 * Animation Util
 * for proper page transition in arabic
 * and english
 */
public class AnimUtil {

    public static Context context;

    public AnimUtil(Context context) {
        this.context = context;
    }

    /**
     * 1st anim for going from left to right
     *
     * @return
     */
    public static int inF() {
        return R.anim.slide_in_left;
    }

    /**
     * 2nd anim for going from left to right
     *
     * @return
     */
    public static int inS() {
        return R.anim.slide_out_left;
    }

    /**
     * 1st anim for going from left to right
     *
     * @return
     */
    public static int outF() {
        return android.R.anim.slide_out_right;
    }

    /**
     * 2nd anim for going from left to right
     *
     * @return
     */
    public static int outS() {
        return R.anim.slide_in_right;
    }

}
package com.droidcoder.wordsrun;

import android.app.Application;
import timber.log.Timber;

/**
 * Created by athanasioskarpouzis on 10/12/14.
 */
public class WordsRunApplication extends Application{

  @Override public void onCreate() {
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
  }
}

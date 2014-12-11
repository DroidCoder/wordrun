package com.droidcoder.wordsrun.service;

import android.content.Context;
import com.droidcoder.wordsrun.domainModel.QuestionUnit;
import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import timber.log.Timber;

/**
 * Created by athanasioskarpouzis on 10/12/14.
 */
public class LocalTranslationSetService implements QuestionSetService {

  private List<QuestionUnit> questionUnits;

  private int currentPos;

  public LocalTranslationSetService(Context context) {
    questionUnits = Lists.newArrayList();
    currentPos = -1;
    populateQuestionUnits(context);
  }

  @Override public int getSize() {
    return questionUnits.size();
  }

  @Override public QuestionUnit getNext() {
    currentPos++;
    if (getSize() <= currentPos) return null;
    return questionUnits.get(currentPos);
  }

  @Override public int getCurrentPosition() {
    return currentPos;
  }

  private void populateQuestionUnits(Context context) {
    try {
      JSONObject obj = new JSONObject(loadJSONFromAsset(context));
      JSONArray array = obj.getJSONArray("words");
      for (int i = 0; i < array.length(); i++) {
        JSONObject translation = array.getJSONObject(i);
        questionUnits.add(new QuestionUnit(translation.getString("l1"), translation.getString("l2"),
            getRandomWrongAnswer(array, i)));
      }
    } catch (Exception e) {
      Timber.e(e, "Generate translationList failed");
    }
    Collections.shuffle(questionUnits);
  }

  private String getRandomWrongAnswer(JSONArray array, int skipPos) {
    try {
      int pos = new Random().nextInt(array.length());
      return pos == skipPos ? getRandomWrongAnswer(array, skipPos)
          : array.getJSONObject(pos).getString("l2");
    } catch (Exception e) {
      return "";
    }
  }

  private String loadJSONFromAsset(Context context) {
    StringBuilder buf = new StringBuilder();
    try {
      InputStream json = context.getAssets().open("raw/words.json");
      BufferedReader in = new BufferedReader(new InputStreamReader(json, "UTF-8"));
      String str;
      while ((str = in.readLine()) != null) {
        buf.append(str);
      }
      in.close();
    } catch (Exception e) {
      Timber.e(e, "load translation file failed");
    }
    return buf.toString();
  }
}

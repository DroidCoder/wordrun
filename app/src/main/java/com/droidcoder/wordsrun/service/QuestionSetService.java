package com.droidcoder.wordsrun.service;

import com.droidcoder.wordsrun.domainModel.QuestionUnit;

/**
 * Created by athanasioskarpouzis on 10/12/14.
 */
public interface QuestionSetService {

  public int getSize();

  public QuestionUnit getNext();

  public int getCurrentPosition();
}

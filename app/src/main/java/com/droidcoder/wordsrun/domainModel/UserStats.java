package com.droidcoder.wordsrun.domainModel;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Created by athanasioskarpouzis on 11/12/14.
 */
@Data @FieldDefaults(level = AccessLevel.PUBLIC)
public class UserStats {
  int correctAnswers = 0;
  int wrongAnswers = 0;
  int timeOuts = 0;
}

package com.droidcoder.wordsrun.domainModel;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Created by athanasioskarpouzis on 10/12/14.
 */
@Value @EqualsAndHashCode(of = { "question", "correctAnswer" })
public class QuestionUnit {

  String question;
  String correctAnswer;
  String wrongAnswer;
}

package com.droidcoder.wordsrun.activity;

import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.droidcoder.wordsrun.R;
import com.droidcoder.wordsrun.domainModel.AnswerType;
import com.droidcoder.wordsrun.domainModel.QuestionUnit;
import com.droidcoder.wordsrun.domainModel.UserStats;
import com.droidcoder.wordsrun.service.LocalTranslationSetService;
import com.droidcoder.wordsrun.service.QuestionSetService;
import com.droidcoder.wordsrun.view.AnswerFeedbackView;
import com.droidcoder.wordsrun.view.QuestionView;
import com.droidcoder.wordsrun.view.ResultsView;

/**
 * Created by athanasioskarpouzis on 10/12/14.
 */
public class MainActivity extends BaseActivity {

  @InjectView(R.id.label_result) AnswerFeedbackView answerFeedbackView;
  @InjectView(R.id.questionView) QuestionView questionView;
  @InjectView(R.id.resultsView) ResultsView sessionResultsView;

  private QuestionSetService questionSet;
  private UserStats userStats;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);

    questionSet = new LocalTranslationSetService(this);
    userStats = new UserStats();
    setupView();
  }

  private void setupView() {

    sessionResultsView.setListener(new ResultsView.Listener() {
      @Override public void onResetClicked() {

        questionSet = new LocalTranslationSetService(MainActivity.this);
        userStats = new UserStats();
        displayNextQuestion();
      }
    });

    answerFeedbackView.setListener(new AnswerFeedbackView.Listener() {
      @Override public void onEnd() {
        displayNextQuestion();
      }
    });

    questionView.setListener(new QuestionView.Listener() {
      @Override public void onAnswer(AnswerType type) {
        answerFeedbackView.displayFeedback(type);
        switch (type) {
          case correct:
            userStats.setCorrectAnswers(1 + userStats.getCorrectAnswers());
            break;
          case wrong:
            userStats.setWrongAnswers(1 + userStats.getWrongAnswers());
            break;
          case timeout:
            userStats.setTimeOuts(1 + userStats.getTimeOuts());
            break;
        }
      }
    });
  }

  @Override public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (!hasFocus) return;

    displayNextQuestion();
  }

  private void displayNextQuestion() {
    QuestionUnit questionUnit = questionSet.getNext();

    if (questionUnit == null) {
      sessionResultsView.displayResults(userStats);
      return;
    }

    questionView.displayQuestion(questionUnit);
  }
}

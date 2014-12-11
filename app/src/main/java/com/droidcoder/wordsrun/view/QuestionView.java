package com.droidcoder.wordsrun.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.droidcoder.wordsrun.R;
import com.droidcoder.wordsrun.domainModel.AnswerType;
import com.droidcoder.wordsrun.domainModel.QuestionUnit;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by athanasioskarpouzis on 11/12/14.
 */
public class QuestionView extends LinearLayout {

  @InjectView(R.id.words_container) View wordsContainer;
  @InjectView(R.id.words_fall_area) View wordsFallArea;
  @InjectView(R.id.label_given_text) TextView givenWord;
  @InjectView(R.id.label_left_word) TextView leftWord;
  @InjectView(R.id.label_right_word) TextView rightWord;
  @InjectView(R.id.button_left_choice) Button leftButton;
  @InjectView(R.id.button_right_choice) Button rightButton;

  @Setter Listener listener;

  private ObjectAnimator animator;

  private QuestionUnit questionUnit;

  public QuestionView(Context context) {
    super(context);
    init();
  }

  public QuestionView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public QuestionView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    inflate(getContext(), R.layout.view_question_waterfall, this);
    ButterKnife.inject(this);

    leftButton.setOnClickListener(new OnAnswerClickListener(leftWord));
    rightButton.setOnClickListener(new OnAnswerClickListener(rightWord));
  }

  public void displayQuestion(QuestionUnit questionUnit) {
    this.questionUnit = questionUnit;
    animator = ObjectAnimator.ofFloat(wordsContainer, "y", 0, wordsFallArea.getHeight());
    animator.setDuration(5000);
    animator.addListener(new AnswerDropAnimationListener());
    animator.start();

    givenWord.setText(questionUnit.getQuestion());

    if (new Random().nextBoolean()) {
      leftWord.setText(questionUnit.getCorrectAnswer());
      rightWord.setText(questionUnit.getWrongAnswer());
    } else {
      rightWord.setText(questionUnit.getCorrectAnswer());
      leftWord.setText(questionUnit.getWrongAnswer());
    }
  }

  @RequiredArgsConstructor
  private class OnAnswerClickListener implements View.OnClickListener {
    private final TextView linkedAnswerView;

    @Override public void onClick(View v) {
      if (animator != null) {
        animator.cancel();
      }
      if (listener != null) {
        listener.onAnswer(
            questionUnit.getCorrectAnswer().equals(linkedAnswerView.getText().toString())
                ? AnswerType.correct : AnswerType.wrong);
      }
    }
  }

  private class AnswerDropAnimationListener implements Animator.AnimatorListener {
    @Override public void onAnimationStart(Animator animation) {

    }

    @Override public void onAnimationEnd(Animator animation) {
      animator.removeAllListeners();
      if (listener != null) {
        listener.onAnswer(AnswerType.timeout);
      }
    }

    @Override public void onAnimationCancel(Animator animation) {
      animator.removeAllListeners();
    }

    @Override public void onAnimationRepeat(Animator animation) {
    }
  }

  public static abstract class Listener {
    public abstract void onAnswer(AnswerType type);
  }
}

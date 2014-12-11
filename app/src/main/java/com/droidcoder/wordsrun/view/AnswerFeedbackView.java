package com.droidcoder.wordsrun.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.TextView;
import com.droidcoder.wordsrun.R;
import com.droidcoder.wordsrun.domainModel.AnswerType;
import lombok.Setter;

/**
 * Created by athanasioskarpouzis on 11/12/14.
 */
public class AnswerFeedbackView extends TextView {

  @Setter private Listener listener;

  public AnswerFeedbackView(Context context) {
    super(context);
  }

  public AnswerFeedbackView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public AnswerFeedbackView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public void displayFeedback(AnswerType type) {
    switch (type) {
      case correct:
        displayCorrect();
        break;
      case wrong:
        displayWrong();
        break;
      case timeout:
      default:
        displayTimeout();
    }

    animateView();
  }

  private void animateView() {
    setVisibility(View.VISIBLE);

    AnimationSet anim = new AnimationSet(true);
    Animation in = new AlphaAnimation(0, 1);
    in.setDuration(250);
    Animation out = new AlphaAnimation(1, 0);
    out.setStartOffset(1000);
    out.setDuration(250);
    anim.addAnimation(in);
    anim.addAnimation(out);
    anim.setAnimationListener(new MyAnimationListener());
    startAnimation(anim);
  }

  private void displayCorrect() {
    setTextColor(getContext().getResources().getColor(R.color.correct));
    setText(getContext().getString(R.string.correct));
  }

  private void displayWrong() {
    setTextColor(getContext().getResources().getColor(R.color.wrong));
    setText(getContext().getString(R.string.wrong));
  }

  private void displayTimeout() {
    setTextColor(getContext().getResources().getColor(R.color.timeout));
    setText(getContext().getString(R.string.timeout));
  }

  public static abstract class Listener {
    public abstract void onEnd();
  }

  private class MyAnimationListener implements Animation.AnimationListener {
    @Override public void onAnimationStart(Animation animation) {
    }

    @Override public void onAnimationEnd(Animation animation) {
      setVisibility(View.GONE);
      if (listener != null) {
        listener.onEnd();
      }
    }

    @Override public void onAnimationRepeat(Animation animation) {
    }
  }
}

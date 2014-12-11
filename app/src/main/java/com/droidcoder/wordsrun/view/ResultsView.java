package com.droidcoder.wordsrun.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.droidcoder.wordsrun.R;
import com.droidcoder.wordsrun.domainModel.UserStats;
import lombok.Setter;

/**
 * Created by athanasioskarpouzis on 11/12/14.
 */
public class ResultsView extends LinearLayout {

  @InjectView(R.id.results_label_correct) TextView labelCorrect;
  @InjectView(R.id.results_label_wrong) TextView labelWrong;
  @InjectView(R.id.results_label_skip) TextView labelSkip;

  @Setter private Listener listener;

  public ResultsView(Context context) {
    super(context);
    init();
  }

  public ResultsView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public ResultsView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    inflate(getContext(), R.layout.view_session_results, this);
    ButterKnife.inject(this);
  }

  public void displayResults(UserStats userStats) {
    labelCorrect.setText(String.format("%s %s", getContext().getString(R.string.correct_answers),
        userStats.getCorrectAnswers()));

    labelWrong.setText(String.format("%s %s", getContext().getString(R.string.wrong_answers),
        userStats.getWrongAnswers()));

    labelSkip.setText(
        String.format("%s %s", getContext().getString(R.string.timeouts), userStats.getTimeOuts()));

    setVisibility(VISIBLE);
  }

  @OnClick(R.id.results_bnt_restart)
  public void onClickRestart() {
    setVisibility(GONE);
    if (listener != null) {
      listener.onResetClicked();
    }
  }

  public static abstract class Listener {
    public abstract void onResetClicked();
  }
}

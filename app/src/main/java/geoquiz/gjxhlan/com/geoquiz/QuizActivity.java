package geoquiz.gjxhlan.com.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private final static String TAG = "QuizActivity";
    private final static String KEY_INDEX = "index";
    private final static String KEY_ANSWERED_QUESTIONS = "answered questions";
    private final static String KEY_RIGHT_ANSWERS = "right answers";
    private final static String KEY_WRONG_ANSWERS = "wrong answers";

    ArrayList<Integer> mAnsweredQuestions = new ArrayList<>();
    private int mRightAnswers = 0;
    private int mWrongAnswers = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;

    private Question[] mQuestions = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = findViewById(R.id.question_text_view);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mAnsweredQuestions = savedInstanceState.getIntegerArrayList(KEY_ANSWERED_QUESTIONS);
            mRightAnswers = savedInstanceState.getInt(KEY_RIGHT_ANSWERS, 0);
            mWrongAnswers = savedInstanceState.getInt(KEY_WRONG_ANSWERS, 0);
        }

        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        updateQuestion();

        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
            }
        });


        Button nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
                updateQuestion();
            }
        });

    }

    private void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        updateButton();
    }

    /** change the state of buttons **/
    private void updateButton() {
        if (mAnsweredQuestions.contains(mCurrentIndex)) {
            blockQuestionButton();
        } else {
            permitQuestionButton();
        }
    }

    private void blockQuestionButton() {
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    private void permitQuestionButton() {
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }

    private void checkAnswer(boolean selectedAnswer) {
        mAnsweredQuestions.add(mCurrentIndex);
        int msgResId;
        if (mQuestions[mCurrentIndex].isAnswerTrue() == selectedAnswer) {
            msgResId = R.string.correct_toast;
            mRightAnswers++;
        } else {
            msgResId = R.string.incorrect_toast;
            mWrongAnswers++;
        }
        Toast.makeText(QuizActivity.this, msgResId, Toast.LENGTH_SHORT)
                .show();
        if (mAnsweredQuestions.size() == mQuestions.length) {
            Toast.makeText(QuizActivity.this, "The points are " + mRightAnswers + " / " + mQuestions.length + ".", Toast.LENGTH_SHORT).show();
        }
        updateButton();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState " + mCurrentIndex);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putIntegerArrayList(KEY_ANSWERED_QUESTIONS, mAnsweredQuestions);
        savedInstanceState.putInt(KEY_RIGHT_ANSWERS, mRightAnswers);
        savedInstanceState.putInt(KEY_WRONG_ANSWERS, mWrongAnswers);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}

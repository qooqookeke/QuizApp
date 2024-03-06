package com.qooke.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qooke.quizapp.model.Quiz;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 멤버변수, 클래스 변수
    TextView txtQuiz;
    ProgressBar progressBar;
    TextView txtResult;
    Button btnTrue;
    Button btnFalse;
    ArrayList<Quiz> quizArrayList = new ArrayList<>();

    int currentIndex = 0;

    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰 연결(클래스 변수 연결)
        txtQuiz = findViewById(R.id.txtQuiz);
        progressBar = findViewById(R.id.progressBar);
        txtResult = findViewById(R.id.txtResult);
        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);

        // 퀴즈는 문제와 정답으로 되어있다. 문제는 문자열이고 정답은 true, false로 할거다(boolean)
        // 퀴즈 하나씩 묶음 처리해야되서 퀴즈 클래스를 설계한다.
        // 설계한 클래스에 데이터를 넣어준다. => 객체를 생성한다.(메모리에 공간 확보)

        // 객체생성
//        Quiz q = new Quiz();
//        // 첫번째 문제 연결
//        q.question = R.string.q1;
//        q.answer = true;

//        Quiz q2 = new Quiz();
//        q2.question = R.string.q2;
//        q2.answer = false;

        // 여러개의 데이터를 하나의 변수에 처리(arrayList)
        // 메모리 객체 생성, 생성자 만들기
        // 함수 호출
        setQuiz();

        // 1번 퀴즈문제를 화면에 보여준다.
        Quiz quiz = quizArrayList.get(currentIndex);
        txtQuiz.setText(quiz.question);

        // 프로그래스바를 하나 증가시켜준다.
        progressBar.setProgress(currentIndex+1);

        // 참 버튼 눌렀을때 처리
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 문제의 정답을 가져온다.
                Quiz quiz = quizArrayList.get(currentIndex);
                if (quiz.answer == true) {
                    // 화면에 정답입니다.를 보여줍니다.
                    txtResult.setText("정답입니다.");
                    count = count + 1;
                } else {
                    // 화면에 오답입니다.를 보여줍니다.
                    txtResult.setText("오답입니다.");
                }
                getNextQuestion();
            }
        });

        // 거짓 버튼 눌렀을때 처리
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quiz quiz = quizArrayList.get(currentIndex);
                if (quiz.answer == false) {
                    txtResult.setText("정답입니다.");
                    count = count + 1;
                } else {
                    txtResult.setText("오답입니다.");
                }
                getNextQuestion();
            }
        });
    }

    private void getNextQuestion() {
        // 현재 인덱스가 리스트에 들어있는 퀴즈의 갯수랑 같으면 에러니까 처리해준다.
        // currentIndex가 9이상 되지 않도록 처음부터 셋팅해준 후 해당 문법을 걸쳐 다음 인덱스로 넘어가게 해준다.
        if(currentIndex == quizArrayList.size()-1) {
            showAlertDialog();
            return;
        }

        // 그 다음 문제 제출
        currentIndex = currentIndex+1;

        Quiz quiz = quizArrayList.get(currentIndex);
        txtQuiz.setText(quiz.question);

        // 프로그래스바 이동
        progressBar.setProgress(currentIndex+1);
    }

    // 함수 만들어서 데이터 저장만 함(저장하는 용도, 유지 보수를 위해, 메모리 stack에 저장)
    void setQuiz() {
        // ArrayList 만들기(quizArrayList 변수로 stack 만듬=> heap에 데이터가 저장된 후 stack에서 변수가 사라짐)
        // 위에서 멤버변수로 만들어줘야 클래스에서 사용가능함
//        ArrayList<Quiz> quizArrayList = new ArrayList<>();
        // 생성자 잡아주기, 문제 생성 ArrayList 추가
        Quiz q = new Quiz(R.string.q1, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q2, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q3, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q4, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q5, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q6, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q7, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q8, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q9, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q10, false);
        quizArrayList.add(q);
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // 이 다이얼로그의 외곽 부분을 눌렀을 때 사라지지 않도록 하는 코드
        builder.setCancelable(false);
        builder.setTitle("퀴즈가 모두 끝났습니다.");
        builder.setMessage("맞춘 정답은 "+count+"개 입니다. 다시 시작하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 첫번재 문제부터 다시 시작
                currentIndex = 0;
                count = 0;

                Quiz quiz = quizArrayList.get(currentIndex);

                txtQuiz.setText(quiz.question);
                progressBar.setProgress(currentIndex+1);
                txtResult.setText("결과");
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 현재 이 액티비티 종료한다. => 액티비티가 1개면 앱이 종료
                finish();
            }
        });
        builder.show();
    }
}
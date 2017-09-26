package com.example.dhafin.logoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dhafin.logoquiz.Adapter.GridViewAnswerAdapter;
import com.example.dhafin.logoquiz.Adapter.GridViewSuggestAdapter;
import com.example.dhafin.logoquiz.Common.Common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public List<String> suggestSource=new ArrayList<>();
    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;
    public Button btnSubmit;
    public GridView gridViewAnswer,gridViewSuggest;
    public ImageView imgViewQuestion;

    int[]image_list={
      R.drawable.a1,
            R.drawable.a2,
    R.drawable.a3,
    R.drawable.a4

};
public char[] answer;
    String correct_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }
    private void initView(){
        gridViewAnswer=(GridView)findViewById(R.id.gridViewAnswer);
        gridViewSuggest=(GridView)findViewById(R.id.gridViewSuggest);
        imgViewQuestion=(ImageView) findViewById(R.id.imgLogo);
        //ADd setup
        setupList();;
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result="";
                for(int i=0;i< Common.user_submit_answer.length;i++)
                    result+=String.valueOf(Common.user_submit_answer[i]);
                if(result.equals(correct_answer))
                {
                    Toast.makeText(getApplicationContext(),"FINISH"+result,Toast.LENGTH_SHORT).show();

                    //Reset
                    Common.count=0;
                    Common.user_submit_answer=new char[correct_answer.length()];

                    //SEt adapter
                    GridViewAnswerAdapter answerAdapter=new GridViewAnswerAdapter(setupNullList(),getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    GridViewSuggestAdapter suggestAdapter=new GridViewSuggestAdapter(suggestSource,getApplicationContext(),MainActivity.this);
                    gridViewSuggest.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                    setupList();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"INCOREECT",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupList() {
        Random random=new Random();
        int imageSelected=image_list[random.nextInt(image_list.length)];
        imgViewQuestion.setImageResource(imageSelected);

        correct_answer=getResources().getResourceName(imageSelected);
        correct_answer=correct_answer.substring(correct_answer.lastIndexOf("/")+1);

        answer=correct_answer.toCharArray();

        Common.user_submit_answer=new char[answer.length];

        suggestSource.clear();
        for(char item:answer)
        {
            suggestSource.add(String.valueOf(item));
        }

        for(int i=answer.length;i<answer.length*2;i++)
            suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

        Collections.shuffle(suggestSource);

        answerAdapter=new GridViewAnswerAdapter(setupNullList(),this);
        suggestAdapter=new GridViewSuggestAdapter(suggestSource,this,this);

        answerAdapter.notifyDataSetChanged();
        suggestAdapter.notifyDataSetChanged();

        gridViewSuggest.setAdapter(suggestAdapter);
        gridViewAnswer.setAdapter(answerAdapter);
    }

    private char[] setupNullList() {
        char result[]=new char[answer.length];
        for(int i=0;i<answer.length;i++)
            result[i]=' ';
        return result;
    }
}

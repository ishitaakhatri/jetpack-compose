package my.first.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition : Int = 1
    private var mQuestionsList : ArrayList<Question>? = null
    private var mSelectedOptionPosition : Int = 0
    private var mUserName : String? = null
    private var mCorrectAnswers : Int = 0

    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tv_Question: TextView? = null
    private var iv_Image: ImageView? = null

    private var tv_option_one: TextView? = null
    private var tv_option_two: TextView? = null
    private var tv_option_three: TextView? = null
    private var tv_option_four: TextView? = null
    private var btnSubmit : Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)


        mUserName = intent.getStringExtra(Constants.USER_NAME)

        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tv_Question = findViewById(R.id.tv_Question)
        iv_Image = findViewById(R.id.iv_Image)
        tv_option_one = findViewById(R.id.tv_option_one)
        tv_option_two = findViewById(R.id.tv_option_two)
        tv_option_three = findViewById(R.id.tv_option_three)
        tv_option_four = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)

        tv_option_one?.setOnClickListener (this)
        tv_option_two?.setOnClickListener (this)
        tv_option_three?.setOnClickListener (this)
        tv_option_four?.setOnClickListener (this)
        btnSubmit?.setOnClickListener (this)

        mQuestionsList = Constants.getQuestion()

        setQuestions()
    }

    private fun setQuestions() {

        defaultOptionsView()
        val question: Question = mQuestionsList!![mCurrentPosition - 1]
        iv_Image?.setImageResource(question.Image)
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        tv_Question?.text = question.question
        tv_option_one?.text = question.optionOne
        tv_option_two?.text = question.optionTwo
        tv_option_three?.text = question.optionThree
        tv_option_four?.text = question.optionFour

        if (mCurrentPosition == mQuestionsList!!.size){
            btnSubmit?.text = "FINISH"
        }else{
            btnSubmit?.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        tv_option_one?.let {
            options.add(0,it)
        }
        tv_option_two?.let {
            options.add(1,it)
        }
        tv_option_three?.let {
            options.add(2,it)
        }
        tv_option_four?.let {
            options.add(3,it)
        }

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_bg
            )
        }
    }

    private fun selectedOptionView(tv:TextView,selectedOptionNum:Int){
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tv_option_one -> {
                tv_option_one?.let {
                    selectedOptionView(it,1)
                }
            }

            R.id.tv_option_two -> {
                tv_option_two?.let {
                    selectedOptionView(it,2)
                }
            }

            R.id.tv_option_three -> {
                tv_option_three?.let {
                    selectedOptionView(it,3)
                }
            }

            R.id.tv_option_four -> {
                tv_option_four?.let {
                    selectedOptionView(it,4)
                }
            }


            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++
                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestions()
                        }else -> {

                            val intent = Intent(this,resultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS,mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS,mQuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition - 1)
                    if (question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size){
                        btnSubmit?.text = "FINISH"
                    }else{
                        btnSubmit?.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer:Int,drawableView: Int){
        when(answer){
            1-> {
                tv_option_one?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }

            2-> {
                tv_option_two?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }

            3-> {
                tv_option_three?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }

            4-> {
                tv_option_four?.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    drawableView
                )
            }

        }
    }
}
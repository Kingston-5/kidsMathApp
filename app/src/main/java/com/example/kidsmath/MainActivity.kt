package com.example.kidsmath

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.util.*

class MainActivity : AppCompatActivity() {

    // Declare user interface elements
    var TimeTextView : TextView? = null
    var ScoreTextView : TextView? = null
    var QuestionTextView : TextView? = null
    var AlertTextView : TextView? = null
    var FinalScoreTextView : TextView? = null
    var button0 : Button? = null
    var button1 : Button? = null
    var button2 : Button? = null
    var button3 : Button? = null

    // Declare Logic variables
    var countDownTimer: CountDownTimer? = null
    var random :Random = Random()
    var a = 0
    var b = 0
    var indexOfCorrectAnswer = 0
    var answers = ArrayList<Int>()
    var points = 0
    var totalQuestions = 0
    var cals = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val calInt = intent.getStringExtra("cals")
        cals = calInt!!
        TimeTextView = findViewById(R.id.TimeTextView)
        QuestionTextView = findViewById(R.id.QuestionTextView)
        AlertTextView = findViewById(R.id.AlertTextView)
        ScoreTextView = findViewById(R.id.ScoreTextView)

        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        start()
    }

    /**
    * nextQuestion function
    *   - used to move to the next question
    *   - first generates the 2 operands of the question and the displays them
    *   in the text view
    *   - after clearing the options from the previous question, it
    *   evaluates the answer for this question and adds it to the answers array
    *   - thereafter it generates 3 incorrect answers and adds them to the array
    *   - lastly it displays all 4 option on the screen
    * */
    private fun nextQuestion(cal:String){
        a = random.nextInt(10)
        b = random.nextInt(10)

        QuestionTextView!!.text = "$a $cal $b"
        indexOfCorrectAnswer = random.nextInt(4)

        answers.clear()

        for (i in 0..3){
            if (indexOfCorrectAnswer == i){
                when(cal){
                    "+"->{answers.add(a+b)}
                    "-"->{answers.add(a-b)}
                    "x"->{answers.add(a*b)}
                    "÷"->{answers.add(a/b)}
                }
            }
            else {
                var wrongAnswer = random.nextInt(20)
                try{
                    while(
                        wrongAnswer == a+b
                        || wrongAnswer == a-b
                        || wrongAnswer == a*b
                        || wrongAnswer == a/b
                    ){
                        wrongAnswer = random.nextInt(20)
                    }
                    answers.add(wrongAnswer)
                }
                catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }

        try {
            button0!!.text = "${answers[0]}"
            button1!!.text = "${answers[1]}"
            button2!!.text = "${answers[2]}"
            button3!!.text = "${answers[3]}"
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    /**
    * optionSelect function
    *   - once the user selects an option it checks if that option is correct
    *   and displays the proper response on the screen.
    *   - it also increases the users points if the option was correct
    *   - it then calls the nexQuestion function
    * */
    fun optionSelect(view: View?){
        totalQuestions++
        if (indexOfCorrectAnswer.toString() == view!!.tag.toString()){
            points++
            AlertTextView!!.setTextColor(Color.parseColor("#42C22D"))
            AlertTextView!!.text = "✔"
        }
        else {
            AlertTextView!!.setTextColor(Color.parseColor("#FF0000"))
            AlertTextView!!.text = "✘"
        }
        ScoreTextView!!.text = "$points/$totalQuestions"
        nextQuestion(cals)
    }

    /** playAgain
    *   - just resets all the points, questions and timer.
    *   - starts the timer afresh
    * */
    private fun playAgain(view:View?){
        points = 0
        totalQuestions = 0
        ScoreTextView!!.text = "$points/$totalQuestions"
        countDownTimer!!.start()
    }

    /** start function
    *  - calls nextQuestion to start off game
    *  - updates countdown timer as time goes
    *  - also displays qame over UI when time has elapsed
    * */
    private fun start(){
        nextQuestion(cals)
        countDownTimer = object:CountDownTimer(30000, 1000){
            override fun onTick(p0: Long) {
                TimeTextView!!.text = (p0 / 1000).toString() + "s"
            }

            override fun onFinish() {
                TimeTextView!!.text = "Time Up"
                openDialog()
            }
        }.start()
    }

    /**
     * openDialog
     * - used to open game over dialog when the time runs out
     * - displays users final score
     * - also wait for play again or go back response
     */
    private fun openDialog() {
        val inflate = LayoutInflater.from(this)
        val winDialog = inflate.inflate(R.layout.win_layout, null)
        FinalScoreTextView = winDialog.findViewById(R.id.FinalScoreTextView)
        val btnPlayAgain = winDialog.findViewById<Button>(R.id.buttonPlayAgain)
        val btnBack = winDialog.findViewById<Button>(R.id.btnBack)

        val dialog = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setView(winDialog)

        val showDialog = dialog.create()

        FinalScoreTextView!!.text = "$points/$totalQuestions"
        btnPlayAgain.setOnClickListener{
            showDialog.dismiss()
            playAgain(it)
        }
        btnBack.setOnClickListener { onBackPressed() }
        showDialog.show()
    }
}
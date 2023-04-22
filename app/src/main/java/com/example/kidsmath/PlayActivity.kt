package com.example.kidsmath

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView


class PlayActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        // Declaring option buttons
        val addition = findViewById<ImageView>(R.id.addition)
        val sub = findViewById<ImageView>(R.id.sub)
        val multiply = findViewById<ImageView>(R.id.multi)
        val divide = findViewById<ImageView>(R.id.div)

        // once any the 4 buttons is clicked the user is moved to the main game screen
        addition.setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)

            calInt.putExtra("cals", "+")
            startActivity(calInt)
        }
        sub.setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)

            calInt.putExtra("cals", "-")
            startActivity(calInt)
        }
        multiply.setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)

            calInt.putExtra("cals", "x")
            startActivity(calInt)
        }
        divide.setOnClickListener {
            val calInt = Intent(this@PlayActivity, MainActivity::class.java)

            calInt.putExtra("cals", "÷")
            startActivity(calInt)
        }

    }
}


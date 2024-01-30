package com.example.quotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import com.example.quotesapp.models.Quotes

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(applicationContext)
    }

    private lateinit var quoteText: TextView
    private lateinit var quoteAuthor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize TextViews after the layout is set
        quoteText = findViewById(R.id.quoteText)
        quoteAuthor = findViewById(R.id.quoteAuthor)

        setQuote(mainViewModel.getQuote())
    }

    private fun setQuote(quotes: Quotes) {
        quoteText.text = quotes.text
        quoteAuthor.text = quotes.author
    }

    fun onShare(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, mainViewModel.getQuote().text)
        startActivity(intent)
    }


    fun onPrevious(view: View) {
        setQuote(mainViewModel.previousQuote())
    }

    fun onNext(view: View) {
        setQuote(mainViewModel.nextQuote())
    }
}

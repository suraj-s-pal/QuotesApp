package com.example.quotesapp

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.quotesapp.models.Quotes
import com.google.gson.Gson

class MainViewModel(val context: Context) : ViewModel() {

    private var quoteList: Array<Quotes> = emptyArray()
    private var index = 0

    init {
        quoteList = loadQuoteFromAssets()
    }

    // Load quotes from quotes.json by converting JSON to Java objects using Gson converter
    private fun loadQuoteFromAssets(): Array<Quotes> {
        try {
            // Reading from files logic:
            val inputStream = context.assets.open("quotes.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            // Converting ByteArray data to String and passing it to the Quotes class
            val json = String(buffer, Charsets.UTF_8)
            val gson = Gson()
            return gson.fromJson(json, Array<Quotes>::class.java)
        } catch (exception: Exception) {
            // Handle exception, e.g., log or throw a custom exception
            throw RuntimeException("Error loading quotes from assets", exception)
        }
    }

    fun getQuote(): Quotes = quoteList[index]

    fun nextQuote(): Quotes {
        if (quoteList.isNotEmpty()) {
            index = (index + 1) % quoteList.size
            return quoteList[index]
        } else {
            // Handle the case where quoteList is empty
            throw IllegalStateException("Quote list is empty")
        }
    }

    fun previousQuote(): Quotes {
        if (quoteList.isNotEmpty()) {
            index = (index - 1 + quoteList.size) % quoteList.size
            return quoteList[index]
        } else {
            // Handle the case where quoteList is empty
            throw IllegalStateException("Quote list is empty")
        }
    }
}

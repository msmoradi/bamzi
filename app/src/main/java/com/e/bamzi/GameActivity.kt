package com.e.bamzi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_game.*
import android.graphics.Typeface
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader



class GameActivity : AppCompatActivity() {

    private lateinit var mViewModel: GameViewModel
    var selectedId = arrayListOf<Int>()
    var selectedWord = arrayListOf<Char>()
    private var finalWord: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        mViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        val boggle = Boggle()
        val words = mViewModel.generateRandomWord(faLetter.keys.toMutableList())
        initWords(boggle, words)

        val typeface = Typeface.createFromAsset(assets, "font/IRANSansLight.ttf")
        gridView?.apply {
            adapter = GridAdapter(applicationContext, words, typeface)
            setOnItemClickListener { _, view, position, id ->
                view?.let {
                    handleGridItemClicked(view, position, id)
                }
            }
        }
        btn_check.setOnClickListener {
            if (boggle.getList().contains(finalWord)) {
                Toast.makeText(this, "درست", Toast.LENGTH_SHORT).show()
                addCorrectWordChip(finalWord)
                reset()
            } else {
                Toast.makeText(this, "نادرست", Toast.LENGTH_SHORT).show()
                addWrongWordChip(finalWord)
                reset()
            }
        }
        btn_help.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("کلمات : ")
                setItems(boggle.getList().toTypedArray(), null)
                setPositiveButton("باشه", null)
                create()
                show()
            }

        }
    }


    private fun addWrongWordChip(finalWord: String) {
        chipGroup.addView(Chip(this).apply {
            text = finalWord
            isClickable = false
            setTextColor(Color.RED)
        })
    }

    private fun addCorrectWordChip(finalWord: String) {
        chipGroup.addView(Chip(this).apply {
            text = finalWord
            isClickable = false
            setTextColor(Color.GREEN)
        })
    }

    private fun reset() {
        finalWord = "کلمه ..."
        txv_word.text = finalWord
        for (i in 0 until gridView.size) {
            val view = gridView[i]
            view.background = ContextCompat.getDrawable(this, R.drawable.corner_dark)
        }
        selectedId.clear()
        selectedWord.clear()
    }

    private fun initWords(boggle: Boggle, words: List<Char>) {

        val root = boggle.TrieNode()
        val assetManager = assets

        try {
            val inputStream = assetManager.open("moin.txt")
            val in2 = BufferedReader(InputStreamReader(inputStream))
            while (in2.ready()) {
                boggle.insert(root, in2.readLine())
            }
        } catch (e: IOException) {
            print(e)
        }

        val gridWordArray = arrayOfNulls<CharArray>(5)
        var r = 0
        for (i in 0..4) {
            val char = CharArray(5)
            for (j in 0..4) {
                char[j] = words[r]
                r++
            }
            gridWordArray[i] = char
        }

        boggle.findWords(gridWordArray, root)
        println(boggle.getList())

    }

    private fun handleGridItemClicked(view: View, position: Int, id: Long) {
        val textView: TextView = view.findViewById(R.id.grid_item_txv) as TextView

        if (!selectedId.contains(position)) {
            view.background = ContextCompat.getDrawable(this, R.drawable.corner_green)
            selectedId.add(position)
            selectedWord.add(textView.text[0])

        } else {
            view.background = ContextCompat.getDrawable(this, R.drawable.corner_dark)
            selectedId.remove(position)
            selectedWord.remove(textView.text[0])
        }

        finalWord = String(selectedWord.toCharArray())
        txv_word.text = finalWord
    }
}

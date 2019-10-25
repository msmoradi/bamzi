package com.e.bamzi

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    fun generateRandomWord(faLetter: MutableList<Char>) = faLetter.shuffled().take(25)

}
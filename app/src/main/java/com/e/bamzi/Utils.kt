package com.e.bamzi

val faLetter = mutableMapOf<Char, Int>(
    'ا' to 0,
    'ب' to 1,
    'پ' to 2,
    'ت' to 3,
    'ث' to 4,
    'ج' to 5,
    'چ' to 6,
    'ح' to 7,
    'خ' to 8,
    'د' to 9,
    'ذ' to 10,
    'ر' to 11,
    'ز' to 12,
    'ژ' to 13,
    'س' to 14,
    'ش' to 15,
    'ص' to 16,
    'ض' to 17,
    'ط' to 18,
    'ظ' to 19,
    'ع' to 20,
    'غ' to 21,
    'ف' to 22,
    'ق' to 23,
    'ک' to 24,
    'گ' to 25,
    'ل' to 26,
    'م' to 27,
    'ن' to 28,
    'و' to 29,
    'ه' to 30,
    'ی' to 31
)

fun getIntFromChar(char: Char) = faLetter[char]

internal fun getCharFromInt(i : Int) = faLetter.filter { it.value == i }.keys.toCharArray()[0]
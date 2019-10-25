package com.e.bamzi;

import java.util.ArrayList;

import static com.e.bamzi.UtilsKt.getCharFromInt;
import static com.e.bamzi.UtilsKt.getIntFromChar;

// Java program for Boggle game
public class Boggle {

    // Alphabet size
    final int SIZE = 32;

    final int M = 5;
    final int N = 5;

    // trie Node
    class TrieNode {
        TrieNode[] Child = new TrieNode[SIZE];

        // isLeaf is true if the node represents
        // end of a word
        boolean leaf;

        //constructor
        public TrieNode() {
            leaf = false;
            for (int i = 0; i < SIZE; i++)
                Child[i] = null;
        }
    }

    // If not present, inserts a key into the trie
    // If the key is a prefix of trie node, just
    // marks leaf node
    void insert(TrieNode root, String Key) {
        int n = Key.length();
        TrieNode pChild = root;

        for (int i = 0; i < n; i++) {
            // int index = Key.charAt(i) - 'A';

            if (getIntFromChar(Key.charAt(i)) != null) {
                int index = getIntFromChar(Key.charAt(i));

                if (pChild.Child[index] == null)
                    pChild.Child[index] = new TrieNode();

                pChild = pChild.Child[index];
            }
        }

        // make last node as leaf node
        pChild.leaf = true;
    }

    // function to check that current location
    // (i and j) is in matrix range
    boolean isSafe(int i, int j, boolean visited[][]) {
        return (i >= 0 && i < M && j >= 0 &&
                j < N && !visited[i][j]);
    }


    ArrayList<String> list = new ArrayList<>();

    public ArrayList<String> getList() {
        return list;
    }

    // A recursive function to print all words present on boggle
    void searchWord(TrieNode root, char boggle[][], int i,
                    int j, boolean visited[][], String str) {
        // if we found word in trie / dictionary
        if (root.leaf == true) {
            System.out.println(str);
            list.add(str);
        }

        // If both I and j in range and we visited
        // that element of matrix first time
        if (isSafe(i, j, visited)) {
            // make it visited
            visited[i][j] = true;

            // traverse all child of current root
            for (int K = 0; K < SIZE; K++) {
                if (root.Child[K] != null) {
                    // current character
                    //char ch = (char) (K + 'A') ;
                    char ch = getCharFromInt(K);

                    // Recursively search reaming character of word
                    // in trie for all 8 adjacent cells of
                    // boggle[i][j]
                    if (isSafe(i + 1, j + 1, visited) && boggle[i + 1][j + 1]
                            == ch)
                        searchWord(root.Child[K], boggle, i + 1, j + 1,
                                visited, str + ch);
                    if (isSafe(i, j + 1, visited) && boggle[i][j + 1]
                            == ch)
                        searchWord(root.Child[K], boggle, i, j + 1,
                                visited, str + ch);
                    if (isSafe(i - 1, j + 1, visited) && boggle[i - 1][j + 1]
                            == ch)
                        searchWord(root.Child[K], boggle, i - 1, j + 1,
                                visited, str + ch);
                    if (isSafe(i + 1, j, visited) && boggle[i + 1][j]
                            == ch)
                        searchWord(root.Child[K], boggle, i + 1, j,
                                visited, str + ch);
                    if (isSafe(i + 1, j - 1, visited) && boggle[i + 1][j - 1]
                            == ch)
                        searchWord(root.Child[K], boggle, i + 1, j - 1,
                                visited, str + ch);
                    if (isSafe(i, j - 1, visited) && boggle[i][j - 1]
                            == ch)
                        searchWord(root.Child[K], boggle, i, j - 1,
                                visited, str + ch);
                    if (isSafe(i - 1, j - 1, visited) && boggle[i - 1][j - 1]
                            == ch)
                        searchWord(root.Child[K], boggle, i - 1, j - 1,
                                visited, str + ch);
                    if (isSafe(i - 1, j, visited) && boggle[i - 1][j]
                            == ch)
                        searchWord(root.Child[K], boggle, i - 1, j,
                                visited, str + ch);
                }
            }

            // make current element unvisited
            visited[i][j] = false;
        }
    }

    // Prints all words present in dictionary.
    void findWords(char boggle[][], TrieNode root) {
        // Mark all characters as not visited
        boolean[][] visited = new boolean[M][N];
        TrieNode pChild = root;

        String str = "";

        // traverse all matrix elements
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                // we start searching for word in dictionary
                // if we found a character which is child
                // of Trie root
                // if (pChild.Child[(boggle[i][j]) - 'A'] != null)
                if (getIntFromChar((boggle[i][j])) != null && pChild.Child[getIntFromChar((boggle[i][j]))] != null) {
                    str = str + boggle[i][j];
                    searchWord(pChild.Child[getIntFromChar((boggle[i][j]))],
                            boggle, i, j, visited, str);
                    str = "";
                }
            }
        }
    }
}
// This code is contributed by Sumit Ghosh

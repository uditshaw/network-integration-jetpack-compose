package com.example.marvelcharactersapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelcharactersapp.model.api.MarvelApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryApiViewModel @Inject constructor(
    private val repo: MarvelApiRepo
): ViewModel() {
    val result = repo.characters
    val queryText = MutableStateFlow("")   // the queryText is the text whatever user searches for.
    private val queryInput = Channel<String>(Channel.CONFLATED)      // Whenever we type something, it goes to the channel and then we search on the results

    init {
        retrieveCharacters()
    }

    private fun retrieveCharacters(){
//  we use viewModelScope.launch() {  } for launching it in the background as it is a coroutine. Dispatchers.IO -> is an IO communication, so we put it on the IO dispatcher.
        viewModelScope.launch(Dispatchers.IO) {
            queryInput.receiveAsFlow().filter { validateQuery(it) }.debounce(1000).collect{repo.query(it)}          // filter the flow with validateQuery fun
        }
    }

    private fun validateQuery(query: String) : Boolean = query.length >= 2

//    function to retrieve the actual query from the UI
//    queryInput.trySend(input) will send the input to queryInput channel, then it will be received as flow, then we'll filter and debounce it and then we invoke the repo.query(it)
    fun onQueryUpdate(input: String){
        queryText.value = input
        queryInput.trySend(input)
    }
}
package com.project.mentalhealth.ui.main.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.project.mentalhealth.data.model.ArticlesItem
import com.project.mentalhealth.data.repo.MainRepository
import com.project.mentalhealth.utils.Event
import com.project.mentalhealth.utils.Result
import kotlinx.coroutines.launch

class HomeViewModel(private val mainRepository: MainRepository) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>(false)
    val newsList = MutableLiveData<ArrayList<ArticlesItem>>(arrayListOf())
    val toastMessage = MutableLiveData<Event<String>>()

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            mainRepository.getNews().asFlow().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        isLoading.postValue(true)
                    }

                    is Result.Success -> {
                        isLoading.postValue(false)
                        newsList.postValue(result.data.articles as ArrayList<ArticlesItem>?)
                    }

                    is Result.Error -> {
                        isLoading.postValue(false)
                        toastMessage.postValue(Event(result.error))
                    }
                }
            }
        }
    }
}
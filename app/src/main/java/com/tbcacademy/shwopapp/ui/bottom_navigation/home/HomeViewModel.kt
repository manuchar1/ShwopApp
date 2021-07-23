package com.tbcacademy.shwopapp.ui.bottom_navigation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbcacademy.shwopapp.data.Post
import com.tbcacademy.shwopapp.repositories.MainRepositoryImpl
import com.tbcacademy.shwopapp.ui.bottom_navigation.sell_product.BasePostViewModel
import com.tbcacademy.shwopapp.utils.Event
import com.tbcacademy.shwopapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepositoryImpl,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main

) : BasePostViewModel(repository, dispatcher) {

    private val _posts = MutableLiveData<Event<Resource<List<Post>>>>()

    override val posts: LiveData<Event<Resource<List<Post>>>>
        get() = _posts

    init {
        getPosts()
    }

    override fun getPosts(uid: String) {
        _posts.postValue(Event(Resource.Loading()))
        viewModelScope.launch(dispatcher) {
            val result = repository.getPostsForFollows()
            _posts.postValue(Event(result))

        }

    }


}
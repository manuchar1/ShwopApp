package com.tbcacademy.shwopapp.ui.bottom_navigation.sell_product

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbcacademy.shwopapp.R
import com.tbcacademy.shwopapp.repositories.MainRepositoryImpl
import com.tbcacademy.shwopapp.utils.Event
import com.tbcacademy.shwopapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(

    private val repository: MainRepositoryImpl,
    private val applicationContext: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main

) : ViewModel() {
    private val _createPostStatus = MutableLiveData<Event<Resource<Any>>>()
    val createPostStatus: LiveData<Event<Resource<Any>>> = _createPostStatus

    private val _curImageUri = MutableLiveData<Uri>()
    val curImageUri: LiveData<Uri> = _curImageUri

    fun setCurImageUri(uri: Uri) {
        _curImageUri.postValue(uri)

    }


    fun createPost(imageUri: Uri,
                   secondImageUrl: Uri,
                   thirdImageUrl: Uri,
                   fourImageUrl: Uri,
                   text: String,
                   price: String,
                   product:String) {
        if (text.isEmpty() && price.isEmpty()) {
            val error = applicationContext.getString(R.string.error_input_empty)
            _createPostStatus.postValue(Event(Resource.Error(error)))
        } else {
            _createPostStatus.postValue(Event(Resource.Loading()))
            viewModelScope.launch(dispatcher) {
                val result = repository.createPost(
                    imageUri,
                    secondImageUrl,
                    thirdImageUrl,
                    fourImageUrl,
                    text,
                    price,
                    product)
                _createPostStatus.postValue(Event(result))
            }
        }
    }
}
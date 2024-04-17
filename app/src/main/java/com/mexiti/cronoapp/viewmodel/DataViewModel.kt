package com.mexiti.cronoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexiti.cronoapp.model.Cronos
import com.mexiti.cronoapp.repository.CronosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(

    private val repository:CronosRepository):ViewModel() {
        private val _cronoList = MutableStateFlow<List<Cronos>>(emptyList())
    val cronolist = _cronoList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {

            repository.getAllcronos().collect(){
                item  ->
                if (item.isNullOrEmpty()){
                    _cronoList.value = emptyList()
                }else{
                    _cronoList.value = item
                }
            }
        }

    }
    fun addCrono(crono:Cronos) = viewModelScope.launch { repository.addCrono(crono) }
    fun updateCrono(crono:Cronos) = viewModelScope.launch { repository.updateCrono(crono) }
    fun deleteCrono(crono: Cronos) = viewModelScope.launch { repository.deleteCrono(crono) }

}
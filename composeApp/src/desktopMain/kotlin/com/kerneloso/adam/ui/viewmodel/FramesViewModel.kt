package com.kerneloso.adam.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerneloso.adam.domain.model.Frame
import com.kerneloso.adam.domain.model.FramesDB
import com.kerneloso.adam.domain.repository.FramesRepository
import kotlinx.coroutines.launch

class FramesViewModel : ViewModel() {

    private val _framesDB = mutableStateOf(FramesDB())
    val framesDB: State<FramesDB> get() = _framesDB

    init {
        loadFrames()
    }

    private fun loadFrames() {
        viewModelScope.launch {
            _framesDB.value = FramesRepository.loadFrames()
        }
    }

    fun searchFrames(query: String): List<Frame> {
        val normalizedQuery = query.lowercase().split(" ").filter {
            it.isNotBlank()
        }

        return _framesDB.value.frames.filter { product ->
            val normalizedName = product.name.lowercase()

            val cleanedName = normalizedName.replace(Regex("[^a-z0-9\\s]") , "")

            val nameWords = cleanedName.split(" ").filter { it.isNotBlank() }

            normalizedQuery.all { searchWord ->
                nameWords.any { nameWord ->
                    nameWord.contains(searchWord)
                }
            }
        }
    }

    fun addFrame(frame: Frame) {
        val current = _framesDB.value
        val updatedFramesDB = current.copy(
            lastID = current.lastID + 1,
            frames = current.frames + frame
        )
        _framesDB.value = updatedFramesDB
        viewModelScope.launch {
            FramesRepository.saveFrames(updatedFramesDB)
        }
    }

    fun updateFrame(frame: Frame) {
        val current = _framesDB.value
        val updatedFramesDB = current.copy(frames =
            current.frames.map {
                if ( it.id == frame.id ) {
                    frame
                } else  {
                    it
                }
            }
        )
        _framesDB.value = updatedFramesDB
        viewModelScope.launch {
            FramesRepository.saveFrames(updatedFramesDB)
        }
    }

    fun deleteFrame(frame: Frame) {
        val current = _framesDB.value
        val updatedFramesDB = current.copy(
            frames = current.frames.filter { it.id != frame.id }
        )
        _framesDB.value = updatedFramesDB
        viewModelScope.launch {
            FramesRepository.saveFrames(updatedFramesDB)
        }
    }

}
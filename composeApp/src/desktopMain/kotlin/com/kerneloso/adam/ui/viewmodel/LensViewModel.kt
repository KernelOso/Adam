package com.kerneloso.adam.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerneloso.adam.domain.model.Lens
import com.kerneloso.adam.domain.model.LensDB
import com.kerneloso.adam.domain.repository.LensRepository
import kotlinx.coroutines.launch

class LensViewModel : ViewModel() {

    private val _lensDB = mutableStateOf(LensDB())
    val lensDB: State<LensDB> get() = _lensDB

    init {
        loadLens()
    }

    private fun loadLens() {
        viewModelScope.launch {
            _lensDB.value = LensRepository.loadLens()
        }
    }

    fun searchLens(query: String): List<Lens> {
        val normalizedQuery = query.lowercase().split(" ").filter {
            it.isNotBlank()
        }

        return _lensDB.value.lens.filter { product ->
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

    fun addLens(lens: Lens) {
        val current = _lensDB.value
        val updatedLensDB = current.copy(
            lastID = current.lastID + 1,
            lens = current.lens + lens
        )
        _lensDB.value = updatedLensDB
        viewModelScope.launch {
            LensRepository.saveLens(updatedLensDB)
        }
    }

    fun updateLens(lens: Lens) {
        val current = _lensDB.value
        val updatedLensDB = current.copy(lens =
            current.lens.map {
                if ( it.id == lens.id ) {
                    lens
                } else  {
                    it
                }
            }
        )
        _lensDB.value = updatedLensDB
        viewModelScope.launch {
            LensRepository.saveLens(updatedLensDB)
        }
    }

    fun deleteLens(lens: Lens ) {
        val current = _lensDB.value
        val updatedLensDB = current.copy(
            lens = current.lens.filter { it.id != lens.id }
        )
        _lensDB.value = updatedLensDB
        viewModelScope.launch {
            LensRepository.saveLens(updatedLensDB)
        }
    }
}
package ec.edu.puce.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.models.RepositoryPayload
import ec.edu.puce.githubclient.models.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoFormViewModel: ViewModel() {
    private val _isLoading = MutableStateFlow(value = false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSuccess = MutableStateFlow(value = false)
    val isSuccess: StateFlow<Boolean> = _isSuccess.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(value = null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()

    // Variable para saber si estamos editando un repositorio existente
    private val _repositoryToEdit = MutableStateFlow<Repository?>(value = null)
    val repositoryToEdit: StateFlow<Repository?> = _repositoryToEdit.asStateFlow()

    fun setRepositoryToEdit(repository: Repository?) {
        _repositoryToEdit.value = repository
    }

    fun createRepository(name: String, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                val repositoryBody = RepositoryPayload(name, description)
                RetrofitClient.apiService.createRepository(payload = repositoryBody)
                _isSuccess.value = true
            } catch (e: Exception) {
                _isSuccess.value = false
                _errorMsg.value = "Error al crear repositorio: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // LLAMADA PATCH para actualizar nombre o descripción
    fun updateRepository(owner: String, repoName: String, name: String, description: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                val repositoryBody = RepositoryPayload(name, description)
                RetrofitClient.apiService.updateRepository(owner = owner, repo = repoName, payload = repositoryBody)
                _isSuccess.value = true
            } catch (e: Exception) {
                _isSuccess.value = false
                _errorMsg.value = "Error al actualizar repositorio: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetSuccess() {
        _isSuccess.value = false
    }

    fun resetError() {
        _errorMsg.value = null
    }
}
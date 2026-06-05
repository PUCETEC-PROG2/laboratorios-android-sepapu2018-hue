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

class RepoListViewModel : ViewModel() {
    private val _repos = MutableStateFlow<List<Repository>>(emptyList())
    val repos: StateFlow<List<Repository>> = _repos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _errorMsg.asStateFlow()

    init {
        fetchRepos()
    }

    fun fetchRepos() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                _repos.value = RetrofitClient.apiService.getRepositories()
            } catch (e: Exception) {
                _errorMsg.value = "Error al cargar repositorios: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Este metodo lo agregue para eliminar un repositorio usando el owner y el nombre del repo
    fun deleteRepo(owner: String, repoName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                val response = RetrofitClient.apiService.deleteRepository(owner, repoName)
                if (response.isSuccessful) {
                    // Si se elimina con éxito en el servidor, refrescamos la lista automáticamente
                    fetchRepos()
                } else {
                    _errorMsg.value = "No se pudo eliminar el repositorio xd: Código ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMsg.value = "Error al eliminar repositorio: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Método para actualizar (PATCH) un repositorio
    fun updateRepo(owner: String, oldRepoName: String, payload: RepositoryPayload) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                // Hacemos la petición PATCH pasándole el cuerpo de actualización
                RetrofitClient.apiService.updateRepository(owner, oldRepoName, payload)
                // Refrescamos la lista para ver los cambios reflejados
                fetchRepos()
            } catch (e: Exception) {
                _errorMsg.value = "Error al actualizar repositorio: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
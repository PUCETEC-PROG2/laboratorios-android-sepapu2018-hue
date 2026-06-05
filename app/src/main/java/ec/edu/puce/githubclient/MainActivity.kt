package ec.edu.puce.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.viewmodels.RepoFormViewModel
import ec.edu.puce.githubclient.viewmodels.RepoListViewModel
import ec.edu.puce.githubclient.ui.screens.RepoForm
import ec.edu.puce.githubclient.ui.screens.RepoList
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubClientTheme {
                var currentScreen by remember { mutableStateOf("repoList") }

                // Variable para repositorio seleccionado para editar
                var selectedRepoForEdit by remember { mutableStateOf<Repository?>(null) }

                val repoListViewModel: RepoListViewModel = viewModel()
                val formViewModel: RepoFormViewModel = viewModel()

                when (currentScreen) {
                    "repoList" -> RepoList(
                        viewModel = repoListViewModel,
                        onNavigateToForm = { repo: Repository? ->
                            selectedRepoForEdit = repo // Guarda el repo (o null si es creación)
                            currentScreen = "repoForm"
                        }
                    )
                    "repoForm" -> RepoForm(
                        repoToEdit = selectedRepoForEdit, // Se lo pasa al formulario
                        viewModel = formViewModel,
                        onBackClick = { currentScreen = "repoList" },
                        onSaveSuccess = {
                            repoListViewModel.fetchRepos() // Refresca mi lista de git automáticamente
                            currentScreen = "repoList"
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GithubClientTheme {
        Greeting("Android")
    }
}
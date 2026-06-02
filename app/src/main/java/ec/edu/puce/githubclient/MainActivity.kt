package ec.edu.puce.githubclient

import android.os.Bundle
import android.widget.ListView
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
import ec.edu.puce.githubclient.models.viewmodels.RepoListViewModel
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
                val repoListViewModel: RepoListViewModel = viewModel()
                val formViewModel: RepoListViewModel = viewModel()
                when (currentScreen) {
                    "repoList" -> RepoList(
                        onNavigateToForm = { currentScreen = "repoForm" }
                    )
                    "repoForm" -> RepoForm(
                        onBackClick = { currentScreen = "repoList" },
                        onSaveSuccess = {
                            repoListViewModel.fetchRepos()
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
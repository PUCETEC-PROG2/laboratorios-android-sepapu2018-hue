package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.viewmodels.RepoListViewModel
import ec.edu.puce.githubclient.ui.components.RepoItem

@Composable
fun RepoList(
    modifier: Modifier = Modifier,
    viewModel: RepoListViewModel = viewModel(),
    onNavigateToForm: (Repository?) -> Unit = {} // Cambiado para que pueda enviar el repositorio a editar o null para uno nuevo
) {
    val repos by viewModel.repos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    //  para controlar el diálogo de confirmación
    var showDeleteDialog by remember { mutableStateOf(false) }
    var repoToDelete by remember { mutableStateOf<Repository?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToForm(null) }, // Al pulsar el +, enviamos null (modo creación)
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir repositorio"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(all = 16.dp)
                )
            }

            if (!isLoading && error == null) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(repos, key = { repo -> "${repo.id}-${repo.name}" }) { repo ->
                        RepoItem(
                            repository = repo,
                            onEdit = { selectedRepo ->
                                onNavigateToForm(selectedRepo) // CAMBIO AQUÍ: Envía el repositorio seleccionado
                            },
                            onDelete = { selectedRepo ->
                                // activa el cuadro de diálogo guardando el repo seleccionado
                                repoToDelete = selectedRepo
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }

            // cuadro de diálogo de confirmación
            if (showDeleteDialog && repoToDelete != null) {
                AlertDialog(
                    onDismissRequest = {
                        showDeleteDialog = false
                        repoToDelete = null
                    },
                    title = { Text(text = "Eliminar Repositorio") },
                    text = { Text(text = "¿Estás seguro de que deseas eliminar el repositorio \"${repoToDelete?.name}\"?") },
                    confirmButton = {
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            onClick = {
                                repoToDelete?.let { repo ->
                                    val ownerName = repo.owner?.login ?: "owner"
                                    viewModel.deleteRepo(owner = ownerName, repoName = repo.name)
                                }
                                showDeleteDialog = false
                                repoToDelete = null
                            }
                        ) {
                            Text(text = "Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDeleteDialog = false
                                repoToDelete = null
                            }
                        ) {
                            Text(text = "Cancelar")
                        }
                    }
                )
            }
        }
    }
}
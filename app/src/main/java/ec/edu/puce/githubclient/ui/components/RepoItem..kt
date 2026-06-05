package ec.edu.puce.githubclient.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ec.edu.puce.githubclient.models.GithubUser
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoItem(
    repository: Repository,
    onEdit: (Repository) -> Unit = {},
    onDelete: (Repository) -> Unit = {}
) {
    // Controla el deslizamiento de la tarjeta
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    // Deslizar de Izquierda a Derecha -> Editar
                    onEdit(repository)
                    false // Se devuelve false para que la tarjeta regrese a su posición original
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    // Deslizar de Derecha a Izquierda -> Activa la confirmación del examen
                    onDelete(repository)
                    false // devuelve false para que la tarjeta regrese a su sitio mientras el diálogo está abierto
                }
                else -> false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            // Fondo que aparece detrás de la tarjeta mientras se arrastra
            val direction = dismissState.dismissDirection
            val color = when (direction) {
                SwipeToDismissBoxValue.StartToEnd -> Color(0xFF4CAF50) // Verde para Editar
                SwipeToDismissBoxValue.EndToStart -> Color(0xFFF44336) // Rojo para Eliminar
                else -> Color.Transparent
            }

            Box(
                modifier = Modifier
                    .padding(8.dp) // Alineado con el padding de tu Card
                    .fillMaxSize()
                    .background(color, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 24.dp),
                contentAlignment = if (direction == SwipeToDismissBoxValue.StartToEnd) Alignment.CenterStart else Alignment.CenterEnd
            ) {
                if (direction == SwipeToDismissBoxValue.StartToEnd) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar", tint = Color.White)
                } else if (direction == SwipeToDismissBoxValue.EndToStart) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.White)
                }
            }
        },
        content = {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = repository.owner?.avatarUrl,
                        contentDescription = "Imagen de repositorio \"${repository.name}\"",
                        modifier = Modifier.size(60.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(width = 16.dp))
                    Column(modifier = Modifier.weight(weight = 1f)) {
                        Text(
                            text = repository.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(height = 4.dp))

                        repository.description?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 3
                            )
                        }
                        repository.language?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.labelSmall,
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview() {
    GithubClientTheme {
        val repository = Repository(
            id = "123",
            name = "Nombre del repositorio",
            description = "Descripcion del repositorio",
            language = "kotlin",
            owner = GithubUser(
                id = "123",
                login = "user",
                avatarUrl = "https://static.vecteezy.com/system/resources/previews/077/675/681/non_2x/simple-outline-round-user-account-profile-avatar-sign-icon-vector.jpg"
            )
        )
        RepoItem(repository)
    }
}
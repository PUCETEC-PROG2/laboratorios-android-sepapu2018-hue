package ec.edu.puce.githubclient.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme

@Composable
fun RepoItem (
    name: String,
    description: String,
    avatarUrl: String,
    language: String
) {
    Card (
        modifier = Modifier
            .padding( all = 8.dp )
            .fillMaxWidth()
    ) {
        Row (
            modifier = Modifier
                .padding( all = 16.dp )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            AsyncImage(
                model = avatarUrl,
                contentDescription = "Imagen de repositorio \"$name\"",
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium
                )
                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                if (language.isNotEmpty()) {
                    Text(
                        text = language,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoItempreview(){
    GithubClientTheme {
        RepoItem(
            "Nombre del Repositorio",
            description = "Description del respositorio",
            avatarUrl = "https://i.pinimg.com/236x/d1/e3/d2/d1e3d2a12bc3d0221898c4391dffcfff.jpg",
            language = "Languaje"
        )
    }
}
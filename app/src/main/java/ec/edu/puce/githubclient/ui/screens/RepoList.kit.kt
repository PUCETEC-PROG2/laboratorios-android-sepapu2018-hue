package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ec.edu.puce.githubclient.ui.components.RepoItem

@Composable
fun RepoList (
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {

        RepoItem(
            name = "Repositorio ANdrtoid",
            description = "Repositorio usando el lenguaje kotlin para4to nivel de PUCETEC",
            avatarUrl ="https://i.pinimg.com/236x/d1/e3/d2/d1e3d2a12bc3d0221898c4391dffcfff.jpg",
            "Kotlin"
        )

        RepoItem(
            name = "Repositorio ANdrtoid",
            description = "Repositorio usando el lenguaje kotlin para4to nivel de PUCETEC",
            avatarUrl ="https://i.pinimg.com/236x/d1/e3/d2/d1e3d2a12bc3d0221898c4391dffcfff.jpg",
            "JavaSceript"

        )
        RepoItem(
            name = "Repositorio ANdrtoid",
            description = "Repositorio usando el lenguaje kotlin para4to nivel de PUCETEC",
            avatarUrl ="https://i.pinimg.com/236x/d1/e3/d2/d1e3d2a12bc3d0221898c4391dffcfff.jpg",
            "Python"

        )
        RepoItem(
            name = "Repositorio ANdrtoid",
            description = "Repositorio usando el lenguaje kotlin para4to nivel de PUCETEC",
            avatarUrl ="https://i.pinimg.com/236x/d1/e3/d2/d1e3d2a12bc3d0221898c4391dffcfff.jpg",
            "Swift"

        )
        RepoItem(
            name = "Repositorio ANdrtoid",
            description = "Repositorio usando el lenguaje kotlin para4to nivel de PUCETEC",
            avatarUrl ="https://i.pinimg.com/236x/d1/e3/d2/d1e3d2a12bc3d0221898c4391dffcfff.jpg",
            "Kotlin"
        )
    }


}
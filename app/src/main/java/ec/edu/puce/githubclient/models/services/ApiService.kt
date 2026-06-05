package ec.edu.puce.githubclient.models.services

import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.models.RepositoryPayload
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET( value = "/user/repos")
    suspend fun getRepositories (
        @Query( value = "sort") sort: String = "created",
        @Query( value = "order") order: String = "desc",
        @Query( value = "affiliation") affiliation: String = "owner",
        @Query( value = "per_page") perPage: Int = 100,
        @Query( value = "t") t: String = "${System.currentTimeMillis()}",
    ) : List<Repository>

    @POST(value = "/user/repos")
    suspend fun createRepository(@Body payload: RepositoryPayload): Repository

    // agregue nuevos metodos

    @PATCH("/repos/{owner}/{repo}")
    suspend fun updateRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body payload: RepositoryPayload
    ): Repository

    @DELETE("/repos/{owner}/{repo}")
    suspend fun deleteRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): retrofit2.Response<Unit>

}
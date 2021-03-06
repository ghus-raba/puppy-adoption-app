/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.network

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

data class TokenResponse(val token_type: String, val expires_in: Int, val access_token: String)

data class AnimalsResponse(val animals: List<AnimalDTO>)

data class AnimalDTO(
    val id: String,
    val name: String,
    val url: String,
    val description: String?,
    val photos: List<PhotosDTO>,
    val attributes: Map<String, Boolean?>,
    val environment: Map<String, Boolean?>,
    val tags: List<String>
)

fun AnimalDTO.escapeDescription(): String {
    fun String.escape() = replace("&amp;", "&")
        .replace("&#39;", "'")
        .replace("&#039;", "'")
        .replace("&#34;", "\"")
        .replace("&quot;", "\"")

    var result = description.orEmpty()
    var escaped = result.escape()

    while (result != escaped) {
        result = escaped
        escaped = result.escape()
    }

    return result
}

@OptIn(ExperimentalStdlibApi::class)
fun AnimalDTO.tagSet(): List<String> = buildSet {
    val attrs = attributes.filter { it.value == true }.keys.map {
        when (it) {
            "spayed_neutered" -> "Spayed/Neutered"
            "house_trained" -> "House trained"
            "declawed" -> "Declawed"
            "special_needs" -> "Special needs"
            "shots_current" -> "Shots current"
            else -> TODO()
        }
    }
    addAll(attrs)
    addAll(tags)
}.take(3)

data class PhotosDTO(val small: String, val medium: String, val large: String, val full: String)

interface PetFinderService {

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getNewToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String = "DwBuoF8MfHgErRS3zV1AHCq1m0zDtBW1D88QM6WymomOx6lc0y",
        @Field("client_secret") clientSecret: String = "GNcPdNvmTVXXkTzbwYdvfSY2qgWVPr882v5ErDsw"
    ): TokenResponse

    @GET("animals")
    suspend fun getAnimals(@Header("Authorization") auth: String, @Query("limit") limit: Int = 100): AnimalsResponse
}

class PetFinderRepo {

    sealed class State {
        object Loading : State()
        object Error : State()
        data class Data(val animals: List<AnimalDTO>) : State()
    }

    private val service = run {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.petfinder.com/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        retrofit.create(PetFinderService::class.java)
    }

    fun getAnimals(): Flow<State> = flow {
        emit(State.Loading)

        try {
            val token = service.getNewToken()
            val response = service.getAnimals(auth = "Bearer ${token.access_token}")
            Log.d(
                "FERI",
                "curl -H \"Authorization: Bearer ${token.access_token}\" https://api.petfinder.com/v2/animals"
            )
            emit(State.Data(response.animals))
        } catch (e: Throwable) {
            emit(State.Error)
        }
    }
}

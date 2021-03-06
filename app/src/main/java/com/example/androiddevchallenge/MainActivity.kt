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
package com.example.androiddevchallenge

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.network.PetFinderRepo
import com.example.androiddevchallenge.ui.theme.MyTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyTheme {
                ProvideWindowInsets {
                    MyApp(::openUrl)
                }
            }
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}

// Start building your app here!
@Composable
fun MyApp(openUrl: (String) -> Unit) {

    val navController = rememberNavController()
    val repo = remember { PetFinderRepo() }
    val listState = remember { mutableStateOf<PetFinderRepo.State>(PetFinderRepo.State.Loading) }
    val favorites = remember { mutableStateOf(emptySet<String>()) }

    val scope = rememberCoroutineScope()
    repo.getAnimals()
        .onEach { listState.value = it }
        .launchIn(scope)

    NavHost(navController, startDestination = "puppy_list") {
        composable("puppy_list") { DogListScreen(navController, listState, favorites) }
        composable(
            "puppy_detail/{puppyId}",
            arguments = listOf(navArgument("puppyId") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("puppyId"))
            val animals = listState.value as PetFinderRepo.State.Data
            val dog = animals.animals.first { it.id == id }
            DogDetailScreen(navController, dog, favorites) { openUrl(it.url) }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp {}
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp {}
    }
}

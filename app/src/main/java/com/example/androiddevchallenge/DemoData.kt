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

import com.example.androiddevchallenge.network.AnimalDTO
import com.example.androiddevchallenge.network.PhotosDTO

val demoData = listOf(
    AnimalDTO(
        id = "1",
        name = "Rex",
        url = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
        description = "This is Rex",
        photos = listOf(
            PhotosDTO(
                small = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
                medium = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
                large = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
                full = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
            )
        ),
        attributes = emptyMap(),
        environment = emptyMap(),
        tags = listOf("Tag1", "Tag2")
    ),
    AnimalDTO(
        id = "2",
        name = "Rex",
        url = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
        description = "This is Rex",
        photos = listOf(
            PhotosDTO(
                small = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
                medium = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
                large = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
                full = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
            )
        ),
        attributes = emptyMap(),
        environment = emptyMap(),
        tags = listOf("Tag1", "Tag2")
    ),
    AnimalDTO(
        id = "3",
        name = "Rex",
        url = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
        description = "This is Rex",
        photos = listOf(
            PhotosDTO(
                small = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
                medium = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
                large = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
                full = "https://images.dog.ceo/breeds/poodle-standard/n02113799_983.jpg",
            )
        ),
        attributes = emptyMap(),
        environment = emptyMap(),
        tags = listOf("Tag1", "Tag2")
    ),
)

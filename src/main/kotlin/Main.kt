// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
@Preview
fun App() {

    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){

            val resultsManager = remember {
                val tmp = ResultsManager()
                tmp.getAllResults()
                tmp
            }
            val game = remember { Game(resultsManager) }
            Row()
            {
                Column(
                    modifier = Modifier.weight(3f).background(Color(255, 240, 255)).fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GameDisplay(
                        formattedTime = game.formattedTime,
                        onStartClick = game::start,
                        onResetClick = game::reset,
                        question = game.question,
                        onSubmitClick = game::submit,
                        isActive = game.isActive
                    )
                }
                Column(
                    modifier = Modifier.weight(2f).background(Color(230, 220, 255)).fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ResultsDisplay(
                        results = resultsManager.results,
                        sortResults = resultsManager::sortResults,
                        pointsText = resultsManager.pointsText,
                        playerText = resultsManager.playerText
                    )
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {

        val results = mutableListOf(Result("Jan",12,54),
                                     Result("Kowal", 6, 23))





        App()
    }
}

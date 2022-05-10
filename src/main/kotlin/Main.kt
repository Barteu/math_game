// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
@Preview
fun App() {
    var playerName by remember { mutableStateOf("") }

    MaterialTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){
            val resultsManager = remember {
                getAllResults()
                ResultsManager
            }
            val game = remember { Game() }
            Row()
            {
                if (game.askForName){
                    Column(
                        modifier = Modifier.weight(3f).background(Color(255, 240, 255)).fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            OutlinedTextField(
                                textStyle = TextStyle(color = Color.Blue, fontSize = 40.sp, textAlign = TextAlign.Center),
                                singleLine = true,
                                value = playerName,
                                onValueChange = {
                                    playerName = it
                                },
                                modifier = Modifier.width(180.dp),
                                label = { Text("Player Name") }
                            )
                            Spacer(Modifier.width(16.dp))
                            Button(onClick = {
                                game.confirmName(playerName)
                                playerName = ""
                            },
                                enabled = playerName.isNotEmpty()) {
                                Text("Submit")
                            }
                        }
                    }
                }
                else {
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
                            isActive = game.isActive,
                            lives = game.lives,
                            points = game.points
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(2f).background(Color(230, 220, 255)).fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ResultsDisplay(
                        results = resultsManager.results,
                        sortResults = ::sortResults,
                        pointsText = resultsManager.pointsText,
                        playerText = resultsManager.playerText,
                        filterResults = ::filterResults
                    )
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}

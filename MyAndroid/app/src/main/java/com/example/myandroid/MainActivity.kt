package com.example.myandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myandroid.ui.theme.MyAndroidTheme
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

class MainActivity : ComponentActivity() {
    private lateinit var flutterEngine: FlutterEngine

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flutterEngine = FlutterEngine(this)

        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        setContent {
            MyAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting("Android")
                        var textInputState by remember { mutableStateOf(TextFieldValue()) }

                        TextField(
                            value = textInputState,
                            onValueChange = {
                                textInputState = it
                            },
                            label = { Text("Enter Text") },
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(textInputState.text)
                        val channel = MethodChannel(
                            flutterEngine.dartExecutor.binaryMessenger,
                            "com.example.myandroid/text_channel"
                        )
                        Button(
                            onClick = {
                                //val inputText = textInputState.text
                                openFlutterActivity()
                                channel.invokeMethod("setText", textInputState.text)
                            },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Open Flutter Page")
                        }
                    }
                }
            }
        }
    }

    private fun openFlutterActivity() {
        val intent = FlutterActivity.createDefaultIntent(this)
        startActivity(intent)
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyAndroidTheme {
        Greeting("Android")
    }
}
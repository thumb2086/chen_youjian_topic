package com.example.chen_youjian_topic

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.chen_youjian_topic.ui.theme.Chen_youjian_topicTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class MainActivity : ComponentActivity() {
    private val client = OkHttpClient()
    // 重要：請將此處的IP位址換成您ESP32-CAM的實際IP位址
    private val esp32IpAddress = "192.168.4.1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Chen_youjian_topicTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EspCamController(
                        streamUrl = "http://$esp32IpAddress/stream",
                        onSendCommand = { command -> sendCommand(command) }
                    )
                }
            }
        }
    }

    private fun sendCommand(command: String) {
        val request = Request.Builder()
            .url("http://$esp32IpAddress/$command")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                // 處理失敗情況
            }

            override fun onResponse(call: Call, response: Response) {
                // 收到回應，但我們不需要處理它
                response.close()
            }
        })
    }
}

@Composable
@SuppressWarnings("UiComposable")
fun EspCamController(streamUrl: String, onSendCommand: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 用於顯示攝影機串流的WebView
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                }
            },
            update = { webView ->
                webView.loadUrl(streamUrl)
            },
            modifier = Modifier.weight(1f)
        )

        // 控制按鈕
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { onSendCommand("up") }) {
                Text("Up")
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(80.dp) // 增加左右按鈕的間距
            ) {
                Button(onClick = { onSendCommand("left") }) {
                    Text("Left")
                }
                Button(onClick = { onSendCommand("right") }) {
                    Text("Right")
                }
            }
            Button(onClick = { onSendCommand("down") }) {
                Text("Down")
            }
        }
    }
}

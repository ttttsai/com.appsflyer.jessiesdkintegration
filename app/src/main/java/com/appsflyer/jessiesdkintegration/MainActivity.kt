package com.appsflyer.jessiesdkintegration

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.RectangleShape
import com.appsflyer.jessiesdkintegration.ui.theme.JessieSDKIntegrationTheme
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.appsflyer.AFInAppEventType // Predefined event names
import com.appsflyer.AFInAppEventParameterName // Predefined parameter names

const val DEV_KEY = "REaW4oazBFH6zuBNihvAWE"
class MainActivity : ComponentActivity() {
    companion object {
        const val LOG_TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appsFlyer: AppsFlyerLib = AppsFlyerLib.getInstance()
        appsFlyer.setDebugLog(true)
        appsFlyer.init(DEV_KEY, null, this)
        appsFlyer.start(this, DEV_KEY, object: AppsFlyerRequestListener {
            override fun onSuccess() {
                Log.d(LOG_TAG, "Launch sent successfully")
            }

            override fun onError(errorCode: Int, errorDesc: String) {
                Log.d(LOG_TAG, "Launch failed to be sent:\n" +
                        "Error code: " + errorCode + "\n"
                        + "Error description: " + errorDesc)
            }
        })
        setContent {
            JessieSDKIntegrationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting("World")
                    Purchase(this)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Composable
fun Purchase(context: Context, modifier: Modifier = Modifier) {
    Button(onClick = {
        val eventValues = HashMap<String, Any>()
        eventValues.put(AFInAppEventParameterName.CONTENT_ID, "fake_content_id")
        eventValues.put(AFInAppEventParameterName.CONTENT_TYPE, "fake_content_type")
        eventValues.put(AFInAppEventParameterName.REVENUE, 200)

        AppsFlyerLib.getInstance().logEvent(context,
            AFInAppEventType.PURCHASE, eventValues)
    }, modifier = modifier) {
        Text(
            text = "Simulate purchase event"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JessieSDKIntegrationTheme {
        Greeting("123")
    }
}
@file:OptIn(ExperimentalMaterial3Api::class)

package io.hirarki.jetpackcodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.hirarki.jetpackcodelab.ui.theme.JetpackCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackCodelabTheme {
                // A surface container using the 'background' color from the theme
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun Greeting(name: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp),
    ) { CardContent(name = name) }
}

@Composable
private fun CardContent(name: String) {
    val expanded = rememberSaveable {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold)
            )

            if (expanded.value) {
                Text(
                    text = ("Composem ipsum color sit lazy, " + "padding theme elit, sed do bouncy").repeat(
                        4
                    ),
                )
            }
        }
        IconButton(onClick = {
            expanded.value = !expanded.value
        }) {
            Icon(
                imageVector = if (expanded.value) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded.value) stringResource(R.string.show_less) else stringResource(
                    R.string.show_more
                )
            )
        }
    }
}

@Composable
private fun MyApp(
    modifier: Modifier = Modifier
) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnBoardingScreen(onContinueClicked = { shouldShowOnboarding = false })
            return@Surface
        }

        Greetings(modifier = modifier)
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Darks"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    JetpackCodelabTheme {
        Greetings()
    }
}

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier, onContinueClicked: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Welcome to the Basic Codelab!")
        Button(
            onClick = onContinueClicked,
            modifier = Modifier.padding(vertical = 24.dp)
        ) {
            Text(text = "Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnBoardingPreview() {
    JetpackCodelabTheme {
        OnBoardingScreen(onContinueClicked = {})
    }
}

@Composable
private fun Greetings(modifier: Modifier = Modifier, names: List<String> = List(1000) { "$it" }) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Preview
@Composable
private fun GreetingsPreview() {
    JetpackCodelabTheme {
        Greetings()
    }
}
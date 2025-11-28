package temirlan.com.calculator

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import temirlan.com.calculator.ui.theme.MainTheme

private val nameButton = listOf(
    listOf("AC", "()", "%", "÷"),
    listOf("7", "8", "9", "\u00D7"),
    listOf("4", "5", "6", "-"),
    listOf("1", "2", "3", "+"),
    listOf("0", ".", "⌫", "="))
@Composable
fun CalculateScreen() {
    var calculateTxt by remember { mutableStateOf("") }
    var outputTxt by remember { mutableStateOf("") }
    val ioModifier =
        Modifier
            .padding(2.dp, 10.dp)
            .fillMaxWidth()
            .background(MainTheme.colors.BackgroundColor)
    val mainModifier = Modifier.Companion.background(
        MainTheme.colors.BackgroundColor)
    val btnColumnModifier =
        Modifier.Companion
            .background(MainTheme.colors.BackgroundColor)
            .padding(bottom = 10.dp)
    val calcBtnModifier = Modifier
        .padding(2.dp)
        .fillMaxHeight(1f)
        .aspectRatio(1f)

    val handleButtonClick: (String) -> Unit = { buttonLabel ->
        when (buttonLabel) {
            "=" -> outputTxt = inputEqual(calculateTxt)
            "AC" -> {
                outputTxt = ""
                bracketsOpen = false
                calculateTxt = ""
            }

            else -> calculateTxt = buttonClick(buttonLabel, calculateTxt)
        }
    }

    Column(modifier = mainModifier) {
        Column(
            modifier = ioModifier.weight(4f)
        ) {
            CalculatorIO(
                calculateTxt, onValueChange = { calculateTxt = it },
                Modifier.weight(1f), colors = listOf(MainTheme.colors.BackgroundColor,MainTheme.colors.BtnMainTextColor)
            )
            CalculatorIO(
                outputTxt, onValueChange = { outputTxt = it },
                Modifier.weight(1f), colors = listOf(MainTheme.colors.BackgroundColor,MainTheme.colors.BtnMainTextColor)
            )
        }
        Column(
            modifier = btnColumnModifier.weight(6f)
        ) {
            nameButton.forEach { it ->
                Row(
                    modifier = Modifier
                        .padding(2.dp)
                        .weight(1f).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    it.forEach {
                        CalculatorButton(
                            label = it,
                            onClick = { handleButtonClick(it) },
                            modifier = calcBtnModifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = changeColor(label)[0]
        )
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(2.dp),
            fontSize = if (label == "AC") 25.sp else 40.sp,
            color = changeColor(label)[1]
        )
    }
}

@Composable
fun CalculatorIO(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    colors:List <Color>
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(value) {
        coroutineScope.launch {
            kotlinx.coroutines.delay(50)
            scrollState.animateScrollTo(
                value = scrollState.maxValue,
                animationSpec = androidx.compose.animation.core.tween(
                    durationMillis = 200,
                    easing = androidx.compose.animation.core.FastOutSlowInEasing
                )
            )
        }
    }
    CompositionLocalProvider(LocalContentColor provides colors[1].copy(alpha = 1f)) {
        TextField(
            value = value,
            onValueChange = { newValue ->
                onValueChange(newValue)
            },
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 60.dp, max = 150.dp)
                .verticalScroll(scrollState),
            textStyle = TextStyle(
                fontSize = 50.sp,
                color = colors[1],
                textAlign = TextAlign.End
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.None),  // Нет клавиатуры
            enabled = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colors[0],
                unfocusedContainerColor = colors[0],
                disabledContainerColor = colors[0],


            ),
            placeholder = {
                Text(
                    text = "0",
                    fontSize = 50.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            singleLine = false,
            maxLines = Int.MAX_VALUE

        )
    }
}


@Preview(name = "Phone", uiMode = Configuration.UI_MODE_NIGHT_YES, device = "id:pixel_4")
@Composable
fun GreetingPreview() {
    MainTheme {
        CalculateScreen()
    }
}

@Composable
fun changeColor(btnText: String): List<Color> {
    when (btnText) {
        "AC" -> return listOf(
            MainTheme.colors.BtnACBackgroundColor,
            MainTheme.colors.BtnACTextColor
        )

        "=" -> return listOf(
            MainTheme.colors.BtnEqualBackgroundColor,
            MainTheme.colors.BtnEqualTextColor
        )

        "()", "%", "÷", "\u00D7", "-", "+" -> return listOf(
            MainTheme.colors.BtnSymbolBackgroundColor,
            MainTheme.colors.BtnSymbolTextColor
        )

        else -> return listOf(
            MainTheme.colors.BtnMainBackgroundColor,
            MainTheme.colors.BtnMainTextColor
        )

    }
}
package temirlan.com.calculator.ui.theme

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import temirlan.com.calculator.bracketsOpen

private val nameButton = listOf(
    listOf("AC", "()", "%", "÷"),
    listOf("7", "8", "9", "\u00D7"),
    listOf("4", "5", "6", "-"),
    listOf("1", "2", "3", "+"),
    listOf("0", ".", "⌫", "="))
@Composable
fun CalculateScreen(name: String, modifier: Modifier = Modifier) {
    var calculateTxt by remember { mutableStateOf("") }
    var outputTxt by remember { mutableStateOf("") }
    val ioModifier =
        Modifier
            .padding(2.dp, 10.dp)
            .fillMaxWidth()
            .background(MainTheme.colors.BackgroundColor)
    val mainModifier = Modifier.background(MainTheme.colors.BackgroundColor)
    val btnColumnModifier =
        Modifier
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
            CalculatorInput(
                calculateTxt, onValueChange = { calculateTxt = it },
                Modifier.weight(1f), color = MainTheme.colors.BackgroundColor
            )
            CalculatorInput(
                outputTxt, onValueChange = { outputTxt = it },
                Modifier.weight(1f), color = MainTheme.colors.BackgroundColor
            )

        }
        Column(
            modifier = btnColumnModifier.weight(6f)
        ) {
            nameButton.forEach {
                Row(
                    modifier = Modifier
                        .padding(2.dp)
                        .weight(1f),
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
            fontSize = if (label == "AC") 31.sp else 40.sp,
            color = changeColor(label)[1]
        )
    }
}


@Composable
fun CalculatorOutput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    color: Color
) {
    TextField(
        textStyle = TextStyle(fontSize = 20.sp),
        value = value,
        onValueChange = { value },
        modifier = Modifier,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MainTheme.colors.BackgroundColor,
            unfocusedContainerColor = MainTheme.colors.BackgroundColor,
            focusedTextColor = MainTheme.colors.BtnMainTextColor,
            unfocusedTextColor = MainTheme.colors.BtnMainTextColor
        )
    )

}

@Composable
fun CalculatorInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    color: Color
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
            color = Color(0xFF343434),
            textAlign = TextAlign.End
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = color,
            unfocusedContainerColor = color,
            disabledContainerColor = color,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,

        ),
        placeholder = {
            Text(
                text = "0",
                fontSize = 50.sp,
                color = Color.Gray.copy(alpha = 0.3f),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        },
        singleLine = false,
        maxLines = Int.MAX_VALUE,
        readOnly = true // Только программный ввод через кнопки
    )
}


@Preview(name = "Phone", device = "id:pixel_4")
@Composable
fun GreetingPreview() {
    MainTheme {
        CalculateScreen("Android")
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
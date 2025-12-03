package temirlan.com.calculator
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import temirlan.com.calculator.ui.theme.MainTheme

@Composable
fun CalculateScreen() {

    val viewModel: CalculateViewModel = viewModel()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(modifier = Modifier
        .background(
            MainTheme.colors.BackgroundColor)
        ) {
        Column(
            modifier = Modifier
                .padding(2.dp, 10.dp)
                .fillMaxWidth()
                .background(MainTheme.colors.BackgroundColor)
                .weight(4f)
        ) {
            CalculatorIO(
                viewModel.stateInput,
                Modifier.weight(1f),
                MainTheme.colors.BackgroundColor,
                MainTheme.colors.BtnMainTextColor
            )
            CalculatorIO(
                viewModel.stateOutput,
                Modifier.weight(1f),
                MainTheme.colors.BackgroundColor,
                MainTheme.colors.BtnMainTextColor
            )
        }
        Column(
            modifier = Modifier
                .background(MainTheme.colors.BackgroundColor)
                .padding(bottom = 10.dp).weight(6f)
        ) {
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .weight(1f).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                CalculatorButton(
                    label = stringResource(R.string.buttonAc),
                    isLandScape = isLandscape,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }.weight(1f)
                ) {
                    viewModel.onAction(CalculateAction.AC)
                }
                CalculatorButton(
                isLandScape = isLandscape,
                    label = stringResource(R.string.buttonBrackets),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                        if (isLandscape) it else it.aspectRatio(1f) }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.Brackets)
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.buttonPercent),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                        if (isLandscape) it else it.aspectRatio(1f)
                    }
                    .weight(1f)
            ){
                    viewModel.onAction(CalculateAction.Operation(operation = CalculateOperation.Percent))
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.buttonDivide),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                        if (isLandscape) it else it.aspectRatio(1f)
                    }
                    .weight(1f)
            ){
                    viewModel.onAction(CalculateAction.Operation(CalculateOperation.Divide))
                }
            }
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .weight(1f).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.button7),
                    modifier = Modifier
                            .padding(2.dp)
                            .fillMaxHeight()
                            .let {
                                if (isLandscape) it else it.aspectRatio(1f)
                            }.weight(1f)
                ){
                    viewModel.onAction(CalculateAction.InputNumber(7))
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.button8),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.InputNumber(8))
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.button9),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else {it.aspectRatio(1f)}
                        }.weight(1f)
                ){
                    viewModel.onAction(CalculateAction.InputNumber(9))
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.buttonMultiply),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.Operation(CalculateOperation.Multiply))
                }
            }
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .weight(1f).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.button4),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.InputNumber(4))
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.button5),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.InputNumber(5))
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.button6),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.InputNumber(6))
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.buttonMinus),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.Operation(CalculateOperation.Minus))
                }
            }
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .weight(1f).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.button1),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.InputNumber(1))
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.button2),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.InputNumber(2))
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.button3),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.InputNumber(3))
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.buttonPlus),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.Operation(CalculateOperation.Plus))
                }
            }
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .weight(1f).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.button0),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.InputNumber(0))
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.buttonDot),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.Dot)
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.buttonBackspace),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it else it.aspectRatio(1f)
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.Delete)
                }
                CalculatorButton(
                    isLandScape = isLandscape,
                    label = stringResource(R.string.buttonEquals),
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxHeight()
                        .let {
                            if (isLandscape) it
                            else {
                                it.aspectRatio(1f)

                            }
                        }
                        .weight(1f)
                ){
                    viewModel.onAction(CalculateAction.Equal)
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    isLandScape: Boolean,
    modifier: Modifier ,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .fillMaxHeight()
            .clickable { onClick() }
            .background ( changeColor(label)[0]),
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = label,
            modifier = Modifier.padding(2.dp),
            color = changeColor(label)[1],
            style = if (isLandScape) MainTheme.typography.labelSmall
                    else MainTheme.typography.labelLarge
        )
    }
}

@Composable
fun CalculatorIO(
    value: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(value) {
        coroutineScope.launch {
            delay(50)
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }

    Text(
        text = value.ifEmpty { "0" },
        style = MainTheme.typography.displayMedium.copy(
            textAlign = TextAlign.End
        ),
        color = textColor,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp, max = 150.dp)
            .background(backgroundColor)
            .verticalScroll(scrollState)
            .padding(16.dp)
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,device = Devices.PIXEL_4, showSystemUi = true)
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
        "()", "%", "÷", "×", "-", "+" -> return listOf(
            MainTheme.colors.BtnSymbolBackgroundColor,
            MainTheme.colors.BtnSymbolTextColor
        )
        else -> return listOf(
            MainTheme.colors.BtnMainBackgroundColor,
            MainTheme.colors.BtnMainTextColor
        )
    }
}
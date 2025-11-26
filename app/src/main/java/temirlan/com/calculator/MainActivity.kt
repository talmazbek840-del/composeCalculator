package temirlan.com.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import temirlan.com.calculator.ui.theme.MainTheme


private val nameButton = listOf(
    listOf("AC", "()", "%", "÷"),
    listOf("7", "8", "9", "\u00D7"),
    listOf("4", "5", "6", "-"),
    listOf("1", "2", "3", "+"),
    listOf("0", ".", "⌫", "=")
)
var bracketsOpen: Boolean = false

private data class Brackets(val open: Boolean, val returnText: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculateScreen(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculateScreen(name: String, modifier: Modifier = Modifier) {
    var calculateTxt by remember { mutableStateOf("") }
    var outputTxt by remember { mutableStateOf("") }
    Column(modifier = Modifier.background(MainTheme.colors.BackgroundColor)) {
        Column(
            modifier = Modifier
                .weight(4f)
                .padding(2.dp, 10.dp)
                .fillMaxWidth()
                .background(MainTheme.colors.BackgroundColor)
        ) {
            TextField(
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
                value = calculateTxt,
                onValueChange = { calculateTxt }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MainTheme.colors.BackgroundColor, shape = RectangleShape),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MainTheme.colors.BackgroundColor,
                    unfocusedContainerColor = MainTheme.colors.BackgroundColor,
                    focusedTextColor = MainTheme.colors.BtnMainTextColor,
                    unfocusedTextColor = MainTheme.colors.BtnMainTextColor
                )
            )

            TextField(
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 20.sp),
                value = outputTxt,
                onValueChange = { outputTxt },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MainTheme.colors.BackgroundColor,
                    unfocusedContainerColor = MainTheme.colors.BackgroundColor,
                    focusedTextColor = MainTheme.colors.BtnMainTextColor,
                    unfocusedTextColor = MainTheme.colors.BtnMainTextColor

                )
            )
        }
        Column(
            modifier = Modifier
                .weight(6f)
                .background(MainTheme.colors.BackgroundColor)
                .padding(bottom = 10.dp)
        ) {
            nameButton.forEach {
                Row(
                    modifier = Modifier
                        .padding(2.dp)
                        .weight(1f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    it.forEach {
                        Button(

                            shape = CircleShape, modifier = Modifier
                                .padding(2.dp)
                                .weight(1f)
                                .fillMaxHeight(1f)
                                .aspectRatio(1f), colors = ButtonDefaults.buttonColors(
                                containerColor = changeColor(it)[0]
                            ), onClick = {
                                if (it == "=") {
                                    outputTxt = inputEqual(calculateTxt)
                                } else if (it == "AC") {
                                    outputTxt = ""
                                    bracketsOpen = false
                                    calculateTxt = ""
                                } else calculateTxt = buttonClick(it, calculateTxt)
                            }) {
                            Text(
                                text = it,
                                modifier = Modifier.padding(2.dp),
                                fontSize = if (it == "AC") 31.sp else 40.sp,
                                color = changeColor(it)[1]
                            )
                        }
                    }
                }
            }
        }
    }
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

private fun buttonClick(btnTxt: String, calculateTxt: String): String {

    when (btnTxt) {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" ->
            return inputDigit(calculateTxt, btnTxt.toInt())

        "." -> return inputDot(calculateTxt)
        "+", "-", "×", "÷", "%" -> return addOperation(btnTxt, calculateTxt)
        "⌫" -> return deleteOperation(calculateTxt)
        "()" -> return addBrackets(calculateTxt)
        else -> return calculateTxt
    }
}

private fun inputDigit(str: String, digit: Int): String {
    if (str.isNotEmpty() && str.last() == ')') {
        return str + "×$digit"

    } else {
        return str + "$digit"
    }
}

private fun inputDot(calculateTxt: String): String {
    val numberLIST = calculateTxt.split("+", "-", "×", "÷", "%", "(", ")")
    val lastPart = numberLIST.lastOrNull() ?: ""
    return if (calculateTxt != "" && calculateTxt.last() != '.'
        && calculateTxt.last().isDigit() && !lastPart.contains(".")
    ) {
        calculateTxt + "."

    } else calculateTxt
}

private fun inputAC(): String {
    return ""
}

private fun addOperation(op: String, calculateTxt: String): String {
    if (calculateTxt != "" && canAddOperation(calculateTxt)) {
        return calculateTxt + op
    }
    return calculateTxt
}

private fun addBrackets(calculateTxt: String): String {
    when {

        calculateTxt.isEmpty() || (isOperation(calculateTxt.last())
                && !bracketsOpen && calculateTxt.last() != '(') -> {
            bracketsOpen = true
            return calculateTxt + "("

        }

        (calculateTxt.last().isDigit() || calculateTxt.last() == ')') && !bracketsOpen -> {
            bracketsOpen = true
            return calculateTxt + "×("

        }

        calculateTxt.last() == '.' && !bracketsOpen -> {
            bracketsOpen = true
            return calculateTxt + "0×("


        }

        calculateTxt.last().isDigit() && bracketsOpen && canCloseBracket(calculateTxt) -> {
            bracketsOpen = false
            return calculateTxt + ")"

        }

        (calculateTxt.last() == '.' && bracketsOpen) -> {
            bracketsOpen = false
            return calculateTxt + "0)"


        }

        else -> return calculateTxt
    }


}


private fun canCloseBracket(str: String): Boolean {
    if (str == "") return false
    var counter = 0
    for (i in str.length - 1 downTo 0) {
        if (str[i] == '(') {
            counter = i
        }
    }
    val listToken =
        str.slice(counter + 1 until str.length).split("+", "-", "×", "÷", "%", "(", ")")
    return listToken.size > 1
}

private fun isOperation(lastChar: Char): Boolean {
    return lastChar == '+' || lastChar == '-' || lastChar == '×' || lastChar == '÷'
            || lastChar == '%'
}

private fun canAddOperation(calculateTxt: String): Boolean {
    return calculateTxt.isNotEmpty() && (calculateTxt.last()
        .isDigit() || calculateTxt.last() == ')') && calculateTxt.last() != '('
}

private fun deleteOperation(calculateTxt: String): String {
    return calculateTxt.dropLast(1)
}

private fun inputEqual(calculateTxt: String): String {
    try {
        val str = calculateTxt
        val rpn = infixToRPN(str)
        val result = evaluateRPN(rpn)
        if (kotlin.math.abs(result % 1.0) == 0.0) {
            return result.toInt().toString()
        } else {
            return result.toString()
        }
    } catch (e: Exception) {
        return "Error"
    }
}


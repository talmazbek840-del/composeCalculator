package temirlan.com.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.math.abs

class CalculateViewModel() : ViewModel() {
    var stateInput by mutableStateOf("")

    var stateOutput by mutableStateOf("")

    private var bracketsOpen: Boolean = false
//    fun buttonClick(btnTxt: String, stateInput: String): String {
//        return when (btnTxt) {
//            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> inputDigit(
//                stateInput,
//                btnTxt.toInt()
//            )
//
//            "." -> inputDot(stateInput)
//            "+", "-", "×", "÷", "%" -> addOperation(btnTxt, stateInput)
//            "⌫" -> deleteOperation(stateInput)
//            "()" -> addBrackets(stateInput)
//            else -> stateInput
//        }
//    }

    fun onAction(action: CalculateAction) {
        println(stateInput)
        when (action) {
            is CalculateAction.InputNumber -> inputDigit(
                stateInput,
                action.number
            )

            is CalculateAction.Dot -> inputDot(stateInput)
            is CalculateAction.Operation -> addOperation(action.operation.symbol, stateInput)
            is CalculateAction.Delete -> deleteOperation()
            is CalculateAction.Brackets -> addBrackets(stateInput)
            is CalculateAction.Equal -> inputEqual(stateInput)
            is CalculateAction.AC -> inputAC()

        }
    }

    private fun inputAC() {
        stateInput = ""
        stateOutput = ""
        bracketsOpen = false
    }

    private fun inputDigit(str: String, digit: Int) {
        stateInput = if (str.isNotEmpty() && str.last() == ')') {
            "$str×$digit"
        } else {
            str + "$digit"
        }
    }

    private fun inputDot(stateInputParam: String) {
        val numberLIST = stateInputParam.split("+", "-", "×", "÷", "%", "(", ")")
        val lastPart = numberLIST.lastOrNull() ?: ""
        if (stateInputParam != "" && stateInputParam.last() != '.' && stateInputParam.last()
                .isDigit() && !lastPart.contains(".")
        ) {
            stateInput = "$stateInputParam."
        }
    }

    private fun addOperation(op: String, stateInputParam: String) {
        if (stateInputParam != "" && canAddOperation(stateInputParam)) {
            stateInput = stateInputParam + op
        }
    }

    private fun addBrackets(stateInputParam: String) {
        when {
            stateInputParam.isEmpty() || (isOperation(stateInputParam.last()) && !bracketsOpen && stateInputParam.last() != '(') -> {
                bracketsOpen = true
                stateInput = "$stateInputParam("
            }

            (stateInputParam.last()
                .isDigit() || stateInputParam.last() == ')') && !bracketsOpen -> {
                bracketsOpen = true
                stateInput = "$stateInputParam×("
            }

            stateInputParam.last() == '.' && !bracketsOpen -> {
                bracketsOpen = true
                stateInput = stateInputParam + "0×("
            }

            stateInputParam.last()
                .isDigit() && bracketsOpen && canCloseBracket(stateInputParam) -> {
                bracketsOpen = false
                stateInput = "$stateInputParam)"
            }

            (stateInputParam.last() == '.' && bracketsOpen) -> {
                bracketsOpen = false
                stateInput = stateInputParam + "0)"
            }
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
        return lastChar == '+' || lastChar == '-' || lastChar == '×' || lastChar == '÷' || lastChar == '%'
    }

    private fun canAddOperation(stateInput: String): Boolean {
        return stateInput.isNotEmpty() && (stateInput.last()
            .isDigit() || stateInput.last() == ')') && stateInput.last() != '('
    }

    private fun deleteOperation() {
        stateInput.dropLast(1)
    }

    private fun inputEqual(stateInput: String) {
        try {
            val str = stateInput
            val rpn = infixToRPN(str)
            val result = evaluateRPN(rpn)
            stateOutput = if (abs(result % 1.0) == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }
        } catch (e: Exception) {
            stateOutput = "Error"
        }
    }
}
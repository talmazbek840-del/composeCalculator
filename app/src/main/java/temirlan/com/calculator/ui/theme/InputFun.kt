package temirlan.com.calculator.ui.theme

import temirlan.com.calculator.bracketsOpen
import temirlan.com.calculator.evaluateRPN
import temirlan.com.calculator.infixToRPN

fun buttonClick(btnTxt: String, calculateTxt: String): String {
    when (btnTxt) {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> return inputDigit(
            calculateTxt,
            btnTxt.toInt()
        )

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
    return if (calculateTxt != "" && calculateTxt.last() != '.' && calculateTxt.last()
            .isDigit() && !lastPart.contains(".")
    ) {
        calculateTxt + "."
    } else calculateTxt
}

private fun addOperation(op: String, calculateTxt: String): String {
    if (calculateTxt != "" && canAddOperation(calculateTxt)) {
        return calculateTxt + op
    }
    return calculateTxt
}

private fun addBrackets(calculateTxt: String): String {
    when {

        calculateTxt.isEmpty() || (isOperation(calculateTxt.last()) && !bracketsOpen && calculateTxt.last() != '(') -> {
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
    val listToken = str.slice(counter + 1 until str.length).split("+", "-", "×", "÷", "%", "(", ")")
    return listToken.size > 1
}

private fun isOperation(lastChar: Char): Boolean {
    return lastChar == '+' || lastChar == '-' || lastChar == '×' || lastChar == '÷' || lastChar == '%'
}

private fun canAddOperation(calculateTxt: String): Boolean {
    return calculateTxt.isNotEmpty() && (calculateTxt.last()
        .isDigit() || calculateTxt.last() == ')') && calculateTxt.last() != '('
}

private fun deleteOperation(calculateTxt: String): String {
    return calculateTxt.dropLast(1)
}

fun inputEqual(calculateTxt: String): String {
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
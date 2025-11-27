package temirlan.com.calculator

import kotlin.math.abs

fun buttonClick(btnTxt: String, calculateTxt: String): String {
    return when (btnTxt) {
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> inputDigit(
            calculateTxt,
            btnTxt.toInt()
        )

        "." -> inputDot(calculateTxt)
        "+", "-", "×", "÷", "%" -> addOperation(btnTxt, calculateTxt)
        "⌫" -> deleteOperation(calculateTxt)
        "()" -> addBrackets(calculateTxt)
        else -> calculateTxt
    }
}

private fun inputDigit(str: String, digit: Int): String {
    return if (str.isNotEmpty() && str.last() == ')') {
        "$str×$digit"

    } else {
        str + "$digit"
    }
}

private fun inputDot(calculateTxt: String): String {
    val numberLIST = calculateTxt.split("+", "-", "×", "÷", "%", "(", ")")
    val lastPart = numberLIST.lastOrNull() ?: ""
    return if (calculateTxt != "" && calculateTxt.last() != '.' && calculateTxt.last()
            .isDigit() && !lastPart.contains(".")
    ) {
        "$calculateTxt."
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
            return "$calculateTxt("

        }

        (calculateTxt.last().isDigit() || calculateTxt.last() == ')') && !bracketsOpen -> {
            bracketsOpen = true
            return "$calculateTxt×("

        }

        calculateTxt.last() == '.' && !bracketsOpen -> {
            bracketsOpen = true
            return calculateTxt + "0×("
        }

        calculateTxt.last().isDigit() && bracketsOpen && canCloseBracket(calculateTxt) -> {
            bracketsOpen = false
            return "$calculateTxt)"

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
    return try {
        val str = calculateTxt
        val rpn = infixToRPN(str)
        val result = evaluateRPN(rpn)
        if (abs(result % 1.0) == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    } catch (e: Exception) {
        "Error"
    }
}
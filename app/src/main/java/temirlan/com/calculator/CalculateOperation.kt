package temirlan.com.calculator

sealed class CalculateOperation(val symbol: String) {
        object Plus: CalculateOperation("+")
        object Minus: CalculateOperation("-")
        object Multiply: CalculateOperation("×")
        object Divide: CalculateOperation("÷")
        object Percent: CalculateOperation("%")
}

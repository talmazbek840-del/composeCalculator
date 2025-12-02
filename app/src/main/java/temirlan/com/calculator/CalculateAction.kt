package temirlan.com.calculator

sealed class CalculateAction {
    data class InputNumber(val number : Int): CalculateAction()
    object AC: CalculateAction()
    object Delete: CalculateAction()
    data class Operation(val operation: CalculateOperation): CalculateAction()
    object Equal: CalculateAction()
    object Dot: CalculateAction()
    object Brackets: CalculateAction()
}



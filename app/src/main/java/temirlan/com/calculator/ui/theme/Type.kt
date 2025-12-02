package temirlan.com.calculator.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val CalculatorTypography = Typography(
    labelLarge =  TextStyle(
        fontSize = 36.sp,
        textAlign = TextAlign.Center
    ),
    labelSmall = TextStyle(
        fontSize = 20.sp,
        textAlign = TextAlign.Center
    ),
    displayMedium = TextStyle(
        fontSize = 50.sp,
        textAlign = TextAlign.End
    )
)

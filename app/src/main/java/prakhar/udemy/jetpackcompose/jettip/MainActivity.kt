package prakhar.udemy.jetpackcompose.jettip

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import prakhar.udemy.jetpackcompose.jettip.components.InputField
import prakhar.udemy.jetpackcompose.jettip.ui.theme.JetTipTheme
import prakhar.udemy.jetpackcompose.jettip.widgets.RoundIconButton
import kotlin.math.log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
//                TopHeader()
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    JetTipTheme {
        content()
    }
}

//@Preview
@Composable
fun TopHeader(totalPerPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(20.dp)
            .clip(
                shape = CircleShape.copy(
                    all = CornerSize(12.dp)
                )
            )
            //OR------------------------------------------------
            .clip(RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Total Per Person",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "$$total", style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainContent() {
    BillForm() { billAmt ->
        Log.d("Bill Amount", "MainContent: $billAmt")
    }
}

@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    onValChange: (String) -> Unit = {
    }
) {

    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

//    val keyboardController: LocalSoftwareKeyboardController

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InputField(
                valueState = totalBillState,
                lableId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())

//                    keyboardController?.hide()  /Not Working
                    //This is for to hide keyboard after we are done entering value and pressing enter.
                }
            )
//            if (validState) {
            Row(
                modifier = Modifier.padding(3.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Split", modifier = Modifier.align(
                        alignment = Alignment.CenterVertically
                    )
                )
                Spacer(modifier = Modifier.width(120.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 3.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    RoundIconButton(imageVector = Icons.Default.Remove, onClick = { })

                    Text(
                        text = "3", modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 9.dp, end = 9.dp)
                    )

                    RoundIconButton(imageVector = Icons.Default.Add, onClick = { })

                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "Tip",
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(200.dp))

                Text(
                    text = "$33.00",
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "33%")

                Spacer(modifier = Modifier.height(14.dp))

                //Slider
                Slider(
                    value = sliderPositionState.value,
                    onValueChange = { newVal ->
                        sliderPositionState.value = newVal //To see the slider moving :)
                        Log.d(
                            "Slider",
                            "BillForm: $newVal"
                        )
                    },
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp
                    ),
                    steps = 5,
                    onValueChangeFinished = {
//                        Log.d("Slider Stopped", "BillForm: Finished...")
                    }
                )
            }
        }
//            } else {
//                Box {}
//            }
    }
}


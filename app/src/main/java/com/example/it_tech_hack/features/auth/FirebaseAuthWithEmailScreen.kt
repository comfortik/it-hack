package com.example.hz.features.firebase.firebaseAuthWithEmail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hz.features.firebase.firebaseAuthWithEmail.models.FirebaseAuthWithEmailAction
import com.example.hz.features.firebase.firebaseAuthWithEmail.models.FirebaseAuthWithEmailIntent
import com.example.hz.features.firebase.firebaseAuthWithEmail.models.FirebaseAuthWithEmailState
import com.example.it_tech_hack.R
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.koinViewModel


@Composable
fun FirebaseAuthWithEmailScreen(viewModel: FirebaseAuthWithEmailViewModel = koinViewModel(),
                                onSignIn: ()->Unit
                                ) {
    val state = viewModel.screenState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(key1 = viewModel) {
        viewModel.action.collect{
            when(it){
                is FirebaseAuthWithEmailAction.ShowError->{
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    Log.d("sd", it.message)
                }
                is FirebaseAuthWithEmailAction.SignIn->{
                    Log.d("view", "signin")
                    viewModel.processIntent(FirebaseAuthWithEmailIntent.SignIn)
                }
                is FirebaseAuthWithEmailAction.NavigateToProfile->{
                    onSignIn()
                }
            }
        }
    }
    Scaffold {
        println(it)
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.enter_email_and_password),
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center ,
                modifier = Modifier.padding(16.dp)
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .background(Color.White, RoundedCornerShape(4.dp)),
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_email),
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            color = Color.LightGray)
                    )
                },
                value = state.value.email,
                textStyle = TextStyle(color = if(state.value.email.isEmpty())Color.Gray else Color.DarkGray,
                    fontFamily = FontFamily.Monospace),
                onValueChange = { viewModel.processIntent(FirebaseAuthWithEmailIntent.ChangeEmail(it))  },
            )



            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(Color.White, RoundedCornerShape(4.dp)),
                placeholder = {
                    Text(text = stringResource(id = R.string.enter_password),
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            color = Color.LightGray)
                    )
                },
                value = state.value.password,
                textStyle = TextStyle(
                    color = Color.Gray,
                    fontFamily = FontFamily.Monospace
                ),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { viewModel.processIntent(FirebaseAuthWithEmailIntent.ChangePassword(it)) },
            )
            Button(
                onClick = { viewModel.processIntent(FirebaseAuthWithEmailIntent.Auth) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.DarkGray
                )
            ) {
                Text(text = stringResource(id = R.string.sign_in),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }


}




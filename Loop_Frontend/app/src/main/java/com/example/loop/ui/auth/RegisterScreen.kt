package com.example.loop.ui.auth


import com.example.loop.network.sendTokenToBackend
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun registerScreen(
    onLogin: () -> Unit,
    navController: NavController
) {
    val auth = FirebaseAuth.getInstance()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 20.dp, bottom = 20.dp)
                .align(Alignment.Center)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Loop",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(15.dp))
            Card(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        text = "SignUp",
                        style = MaterialTheme.typography.displaySmall,
                        fontSize = 30.sp
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = {name = it},
                        label = {Text(text = "Name")},
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = {email = it},
                        label = {Text(text = "Email")},
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = {password = it},
                        label = {Text(text = "Password")},
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {passwordVisible = !passwordVisible}) {
                                Icon(
                                    imageVector = if(passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                    contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                                )
                            }
                        }
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {confirmPassword = it},
                        label = {Text(text = "Confirm Password")},
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {passwordVisible = !passwordVisible}) {
                                Icon(
                                    imageVector = if(passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                    contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                                )
                            }
                        },
                        isError = confirmPassword.isNotEmpty() && password != confirmPassword
                    )

                    if(errorMessage != null){
                        Text(
                            text = errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    if(successMessage != null){
                        Text(
                            text = successMessage!!,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = {
                            errorMessage = null
                            successMessage = null
                            isLoading = true

                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if(task.isSuccessful){
                                        val currentUser = auth.currentUser ?: return@addOnCompleteListener
                                        val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                                        val db = FirebaseFirestore.getInstance()

                                        val user = hashMapOf(
                                            "name" to name,
                                            "email" to email,
                                        )

                                        db.collection("users")
                                            .document(userId)
                                            .set(user).addOnSuccessListener {
                                                val profileUpdates = userProfileChangeRequest {
                                                    displayName = name
                                                }

                                                currentUser.updateProfile(profileUpdates)
                                                    .addOnCompleteListener {
                                                        currentUser.getIdToken(true)
                                                            .addOnSuccessListener { result ->
                                                                val idToken = result.token
                                                                if(idToken != null){
                                                                    sendTokenToBackend(idToken);
                                                                }
                                                                successMessage = "Registration Successful"
                                                                isLoading = false;
                                                                navController.navigate(route = "Login")
                                                            }
                                                    }

                                            }
                                            .addOnFailureListener { e ->
                                                errorMessage = e.message ?: "Failed Saving Data"
                                                isLoading = false
                                            }
                                    }else{
                                        isLoading = false;
                                        errorMessage = task.exception?.message ?: "Registration Failed"
                                    }
                                }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary

                            )
                        } else {
                            Text(
                                text = "SignUp",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Already have an Account?",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        TextButton(
                            onClick = onLogin
                        ) {
                            Text(
                                text = "SignIn",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Being Better Everyday",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


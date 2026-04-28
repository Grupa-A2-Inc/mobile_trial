package com.adaptive_tutor_mobile.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.adaptive_tutor_mobile.data.remote.dto.RegisterRequest
import com.adaptive_tutor_mobile.domain.model.User

private val emailRegexRegister = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
private val organizationTypes = listOf("SCHOOL", "UNIVERSITY", "COMPANY", "OTHER")

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: (User) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var firstName by remember { mutableStateOf("") }
    var lastName  by remember { mutableStateOf("") }
    var email     by remember { mutableStateOf("") }
    var password  by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible  by remember { mutableStateOf(false) }

    var organizationName by remember { mutableStateOf("") }
    var organizationType by remember { mutableStateOf(organizationTypes[0]) }
    var orgTypeExpanded  by remember { mutableStateOf(false) }
    var country  by remember { mutableStateOf("") }
    var city     by remember { mutableStateOf("") }
    var address  by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }

    var errors by remember { mutableStateOf<Map<String, String>>(emptyMap()) }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Success) {
            onRegisterSuccess((uiState as AuthUiState.Success).user)
            viewModel.resetState()
        }
    }

    Scaffold { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Înregistrare", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Date personale", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("Prenume") },
                    isError = errors["firstName"] != null,
                    supportingText = errors["firstName"]?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            item {
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Nume") },
                    isError = errors["lastName"] != null,
                    supportingText = errors["lastName"]?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            item {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isError = errors["email"] != null,
                    supportingText = errors["email"]?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            item {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Parolă") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None
                                           else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility
                                              else Icons.Filled.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    },
                    isError = errors["password"] != null,
                    supportingText = errors["password"]?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            item {
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmă parola") },
                    visualTransformation = if (confirmVisible) VisualTransformation.None
                                           else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { confirmVisible = !confirmVisible }) {
                            Icon(
                                imageVector = if (confirmVisible) Icons.Filled.Visibility
                                              else Icons.Filled.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    },
                    isError = errors["confirmPassword"] != null,
                    supportingText = errors["confirmPassword"]?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Organizație", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                OutlinedTextField(
                    value = organizationName,
                    onValueChange = { organizationName = it },
                    label = { Text("Numele organizației") },
                    isError = errors["organizationName"] != null,
                    supportingText = errors["organizationName"]?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            item {
                ExposedDropdownMenuBox(
                    expanded = orgTypeExpanded,
                    onExpandedChange = { orgTypeExpanded = it }
                ) {
                    OutlinedTextField(
                        value = organizationType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipul organizației") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = orgTypeExpanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = orgTypeExpanded,
                        onDismissRequest = { orgTypeExpanded = false }
                    ) {
                        organizationTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = { organizationType = type; orgTypeExpanded = false }
                            )
                        }
                    }
                }
            }
            item {
                OutlinedTextField(
                    value = country,
                    onValueChange = { country = it },
                    label = { Text("Țară") },
                    isError = errors["country"] != null,
                    supportingText = errors["country"]?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            item {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Oraș") },
                    isError = errors["city"] != null,
                    supportingText = errors["city"]?.let { { Text(it) } },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            item {
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Adresă (opțional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
            item {
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Telefon (opțional)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            if (uiState is AuthUiState.Error) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = (uiState as AuthUiState.Error).message,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val errs = mutableMapOf<String, String>()
                        if (firstName.isBlank()) errs["firstName"] = "Câmp obligatoriu"
                        if (lastName.isBlank())  errs["lastName"]  = "Câmp obligatoriu"
                        if (!emailRegexRegister.matches(email)) errs["email"] = "Email invalid"
                        if (password.length < 8) errs["password"] = "Minim 8 caractere"
                        if (confirmPassword != password) errs["confirmPassword"] = "Parolele nu coincid"
                        if (organizationName.isBlank()) errs["organizationName"] = "Câmp obligatoriu"
                        if (country.isBlank()) errs["country"] = "Câmp obligatoriu"
                        if (city.isBlank()) errs["city"] = "Câmp obligatoriu"
                        errors = errs
                        if (errs.isEmpty()) {
                            viewModel.register(
                                RegisterRequest(
                                    firstName = firstName.trim(),
                                    lastName  = lastName.trim(),
                                    email     = email.trim(),
                                    password  = password,
                                    confirmPassword = confirmPassword,
                                    organizationName = organizationName.trim(),
                                    country  = country.trim(),
                                    city     = city.trim(),
                                    organizationType = organizationType,
                                    address     = address.trim().takeIf { it.isNotEmpty() },
                                    phoneNumber = phoneNumber.trim().takeIf { it.isNotEmpty() }
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = uiState !is AuthUiState.Loading
                ) {
                    if (uiState is AuthUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Creează cont")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = onNavigateToLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ai deja cont? Autentifică-te")
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

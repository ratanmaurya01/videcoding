package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.VibeViewModel
import com.example.ui.components.PrimaryButton
import com.example.ui.components.VibeTextField
import com.example.ui.theme.VibeMutedText
import com.example.ui.theme.VibePrimary
import com.example.ui.theme.VibeSecondary
import kotlinx.coroutines.launch

@Composable
fun AuthFlowScreen(
    viewModel: VibeViewModel,
    modifier: Modifier = Modifier
) {
    val screenState by viewModel.authScreen.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (screenState) {
            "login" -> LoginScreen(
                viewModel = viewModel,
                onNavigateToRegister = { viewModel.setAuthScreen("register") },
                onNavigateToForgot = { viewModel.setAuthScreen("forgot") }
            )
            "register" -> RegisterScreen(
                viewModel = viewModel,
                onNavigateToLogin = { viewModel.setAuthScreen("login") }
            )
            "forgot" -> ForgotPasswordScreen(
                onNavigateToLogin = { viewModel.setAuthScreen("login") }
            )
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: VibeViewModel,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgot: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // App Logo Icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    brush = Brush.linearGradient(colors = listOf(VibePrimary, VibeSecondary)),
                    shape = MaterialTheme.shapes.large
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.TrendingUp,
                contentDescription = "VibeTrading Logo",
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "VibeTrading",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Ride the market momentum",
            style = MaterialTheme.typography.bodyMedium,
            color = VibeMutedText,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        VibeTextField(
            value = username,
            onValueChange = { username = it },
            label = "Username or Email",
            placeholder = "Enter your username"
        )

        Spacer(modifier = Modifier.height(16.dp))

        VibeTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "••••••••",
            isPassword = true,
            keyboardType = KeyboardType.Password
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.clickable { onNavigateToForgot() }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Login",
            onClick = {
                keyboard?.hide()
                if (username.isBlank() || password.isBlank()) {
                    scope.launch {
                        snackbarHostState.showSnackbar("Please enter username and password")
                    }
                } else {
                    val success = viewModel.login(username, password)
                    if (!success) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Authentication failed")
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            text = "Skip Login",
            onClick = {
                keyboard?.hide()
                viewModel.skipLogin()
            },
            gradient = false
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                color = VibeMutedText,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Register",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.clickable { onNavigateToRegister() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        SnackbarHost(hostState = snackbarHostState)
    }
}

@Composable
fun RegisterScreen(
    viewModel: VibeViewModel,
    onNavigateToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Join VibeTrading and start learning",
            style = MaterialTheme.typography.bodyMedium,
            color = VibeMutedText,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        VibeTextField(
            value = username,
            onValueChange = { username = it },
            label = "Username",
            placeholder = "Enter your username"
        )

        Spacer(modifier = Modifier.height(16.dp))

        VibeTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email Address",
            placeholder = "name@example.com",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        VibeTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "••••••••",
            isPassword = true,
            keyboardType = KeyboardType.Password
        )

        Spacer(modifier = Modifier.height(16.dp))

        VibeTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            placeholder = "••••••••",
            isPassword = true,
            keyboardType = KeyboardType.Password
        )

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Register",
            onClick = {
                keyboard?.hide()
                when {
                    username.isBlank() || email.isBlank() || password.isBlank() -> {
                        scope.launch { snackbarHostState.showSnackbar("All fields are required") }
                    }
                    password != confirmPassword -> {
                        scope.launch { snackbarHostState.showSnackbar("Passwords do not match") }
                    }
                    else -> {
                        viewModel.register(username, email, password)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account? ",
                color = VibeMutedText,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Login",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.clickable { onNavigateToLogin() }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        SnackbarHost(hostState = snackbarHostState)
    }
}

@Composable
fun ForgotPasswordScreen(
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var resetSent by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Reset Password",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-0.5).sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "We'll send you recovery instructions",
            style = MaterialTheme.typography.bodyMedium,
            color = VibeMutedText,
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        if (!resetSent) {
            VibeTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email Address",
                placeholder = "name@example.com",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(32.dp))

            PrimaryButton(
                text = "Send Recovery Email",
                onClick = {
                    keyboard?.hide()
                    if (email.isBlank()) {
                        scope.launch { snackbarHostState.showSnackbar("Please enter email address") }
                    } else {
                        resetSent = true
                    }
                }
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp, // Placeholder indicator
                        contentDescription = "Email sent",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Reset Link Sent!",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Check your email $email for instructions to reset your password.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = VibeMutedText,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Back to Login",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .clickable { onNavigateToLogin() }
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))
        SnackbarHost(hostState = snackbarHostState)
    }
}

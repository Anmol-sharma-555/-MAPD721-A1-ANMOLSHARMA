package com.example.mapd721_a1_anmolsharma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mapd721_a1_anmolsharma.ui.theme.MAPD721A1AnmolSharmaTheme
import com.example.mapd721_a1_anmolsharma.DataStoreManager.StoreUserData
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.datastore.core.DataStore
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.Preferences
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAPD721A1AnmolSharmaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    // context
    val context = LocalContext.current
    // scope
    val scope = rememberCoroutineScope()
    // datastore
    val dataStore = StoreUserData(context)

    val savedNameState = dataStore.getName.collectAsState(initial = "")
    val savedEmailState = dataStore.getEmail.collectAsState(initial = "")
    val savedIdState = dataStore.getId.collectAsState(initial = "")

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(15.dp))

        // Username field
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Username", color = Color.Gray, fontSize = 12.sp) },
        )
        Spacer(modifier = Modifier.height(15.dp))

        // Email field
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email", color = Color.Gray, fontSize = 12.sp) },
        )
        Spacer(modifier = Modifier.height(15.dp))

        // ID field
        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            value = id,
            onValueChange = { id = it },
            label = { Text(text = "ID", color = Color.Gray, fontSize = 12.sp) },
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Save button
            Button(
                modifier = Modifier
                    .height(60.dp),
                onClick = {
                    //launch the class in a coroutine scope
                    scope.launch {
                        dataStore.saveData(name, email, id)
                        // Clear input fields after saving
                        name = ""
                        email = ""
                        id = ""
                    }
                },
            ) {
                // button text
                Text(
                    text = "Save",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            // Load button
            Button(
                modifier = Modifier
                    .height(60.dp),
                onClick = {
                    // Load data from DataStore
                    scope.launch {
                        val (loadedName, loadedEmail, loadedId) = dataStore.loadData()
                        // Update input fields with loaded data
                        name = loadedName ?: ""
                        email = loadedEmail ?: ""
                        id = loadedId ?: ""
                    }
                },
            ) {
                // button text
                Text(
                    text = "Load",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            // Clear button
            Button(
                modifier = Modifier
                    .height(60.dp),
                onClick = {
                    // Clear data from DataStore
                    scope.launch {
                        dataStore.clearData()
                        name = ""
                        email = ""
                        id = ""
                    }
                },
            ) {
                // button text
                Text(
                    text = "Clear",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))


    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MAPD721A1AnmolSharmaTheme {
        MainScreen()
    }
}
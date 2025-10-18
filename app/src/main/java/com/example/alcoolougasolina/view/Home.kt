package com.example.alcoolougasolina.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alcoolougasolina.datasource.Calculations
import com.example.alcoolougasolina.ui.theme.AlcoolOuGasolinaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    var alcool by rememberSaveable { mutableStateOf("") }
    var gasolina by rememberSaveable { mutableStateOf("") }
    var posto by rememberSaveable { mutableStateOf("") }
    var textFieldError by rememberSaveable { mutableStateOf(false) }
    var textResult by rememberSaveable { mutableStateOf("") }
    var checked by rememberSaveable { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Alcool ou Gasolina") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = alcool,
                onValueChange = { if (it.length <= 4) alcool = it },
                label = { Text(text = "Preço do Álcool (R$)") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = textFieldError
            )

            OutlinedTextField(
                value = gasolina,
                onValueChange = { if (it.length <= 4) gasolina = it },
                label = { Text(text = "Preço da Gasolina (R$)") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = textFieldError
            )

            OutlinedTextField(
                value = posto,
                onValueChange = { posto = it },
                label = { Text(text = "Posto (Opicional)") },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 20.dp, 0.dp)
            ) {
                Switch(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    thumbContent = if (checked) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    } else { null }
                )
                Text(
                    text = if (checked) "75%" else "70%",
                    color = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Button(
                onClick = {
                    Calculations.calculate(alcool = alcool, gasolina = gasolina, posto = posto, porcentagem = checked, response = { result, textFieldState ->
                        textResult = result
                        textFieldError = textFieldState
                    })
                },
                modifier = Modifier.fillMaxWidth().height(100.dp).padding(20.dp)
            ) {
                Text(
                    text = "Calcular",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = textResult,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
        }
    }
}

@Preview
@Composable
private fun HomePreview() {
    AlcoolOuGasolinaTheme {
        Home()
    }
}
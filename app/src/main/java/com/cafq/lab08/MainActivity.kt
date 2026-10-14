package com.cafq.lab08

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels

class MainActivity : ComponentActivity() {

    // El ViewModel sobrevive a rotaciones; la BD es un singleton
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskDatabase.getInstance(applicationContext).taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContent {
            MotoTheme {
                TaskScreen(viewModel)
            }
        }
    }
}

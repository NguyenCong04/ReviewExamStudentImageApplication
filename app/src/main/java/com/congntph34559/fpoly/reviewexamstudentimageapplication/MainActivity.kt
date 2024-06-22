package com.congntph34559.fpoly.reviewexamstudentimageapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.congntph34559.fpoly.reviewexamstudentimageapplication.room.db.AppDatabase
import com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.navigation.AppNavigation
import com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.theme.ReviewExamStudentImageApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "students"
        ).allowMainThreadQueries().build()
        setContent {
           AppNavigation(db)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReviewExamStudentImageApplicationTheme {
        Greeting("Android")
    }
}
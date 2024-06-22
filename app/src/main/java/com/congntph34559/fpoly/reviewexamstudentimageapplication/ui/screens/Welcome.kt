package com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.congntph34559.fpoly.reviewexamstudentimageapplication.R
import com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.navigation.ROUTE
import kotlinx.coroutines.delay

@Composable
fun GetLayoutWelcome(navController: NavHostController) {

    LaunchedEffect(key1 = true) {
        delay(2000)


        navController.navigate(ROUTE.LIST.name) {
            popUpTo(ROUTE.WELCOME.name) {
                inclusive = true
            }
        }


    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(
                id = R.drawable
                    .ic_launcher_foreground
            ), contentDescription = null
        )
        Text(
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Serif,
            text = "Nguyen Tat Cong | PH34559 | 17/6"
        )

    }


}
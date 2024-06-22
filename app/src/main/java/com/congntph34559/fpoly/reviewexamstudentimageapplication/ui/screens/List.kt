package com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.congntph34559.fpoly.reviewexamstudentimageapplication.room.model.StudentModel
import com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.navigation.ROUTE
import java.io.File

@Composable
fun GetLayoutList(
    navController: NavHostController,
    studentViewModel: StudentViewModel
) {


    var list by remember {
        mutableStateOf(studentViewModel.getAll())
    }

    var isShowDialog by remember {
        mutableStateOf(false)
    }

    var isShowDialogDelete by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    var stDetail by remember {
        mutableStateOf(StudentModel(0, "", 0f, "", ""))
    }


    if (isShowDialog) {
        AlertDialog(
            containerColor = Color.White,
            title = {
                Image(
                    painter = rememberImagePainter(
                        data = File(stDetail.image_ph34559)
                    ),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(200.dp)
                        .clip(
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            },
            text = {
                Text(
                    fontFamily = FontFamily.Serif,
                    text = stDetail.toString()
                )
            },
            onDismissRequest = {
                isShowDialog = false
            },
            confirmButton = {
                Text(
                    fontFamily = FontFamily.Serif,
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        isShowDialog = false
                    },
                    text = "Close"
                )
            },
            shape = RoundedCornerShape(8.dp)
        )

    }


    if (isShowDialogDelete) {
        AlertDialog(
            containerColor = Color.White,
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    text = "Thong bao !",
                )
            },
            text = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Serif,
                    text = "Ban co chac chan muon xoa khong ?"
                )
            },
            onDismissRequest = {
                isShowDialogDelete = false
            },
            confirmButton = {
                Text(
                    fontFamily = FontFamily.Serif,
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        studentViewModel.delete(stDetail)
                        Toast.makeText(
                            context, "Xoa thanh cong", Toast
                                .LENGTH_SHORT
                        ).show()
                        list = studentViewModel.getAll()
                        isShowDialogDelete = false
                    },
                    text = "Ok"
                )
            },
            shape = RoundedCornerShape(8.dp)
        )

    }



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()

    ) {

        Text(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = FontFamily.Serif,
            text = "List student"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)

        ) {

            LazyColumn {

                items(list ?: emptyList()) {

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                isShowDialog = true
                                stDetail = it
                            }
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)

                        ) {
                            Text(
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Serif,
                                text = it.id.toString()
                            )
                            Spacer(modifier = Modifier.width(15.dp))

                            Image(
                                painter = rememberImagePainter(
                                    data = File(it.image_ph34559)
                                ),
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Column {
                                Text(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Serif,
                                    text = "Name: ${it.name_ph34559}"
                                )
                                Text(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Serif,
                                    text = "Point: ${it.point_ph34559}"
                                )
                                Text(
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily.Serif,
                                    text = "Class: ${it.klass_ph34559}"
                                )

                            }
                        }

                        Row(
                            modifier = Modifier.weight(0.3f)
                        ) {
                            IconButton(onClick = {
                                navController.navigate(
                                    "${ROUTE.CUD.name}/${it.id}"
                                ) {
                                    popUpTo(ROUTE.LIST.name) {
                                        inclusive = true
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null
                                )

                            }
                            IconButton(onClick = {
                                isShowDialogDelete = true
                                stDetail = it
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null
                                )

                            }
                        }


                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()


                }


            }


        }


    }


    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize()
    ) {

        FloatingActionButton(
            containerColor = Color.Black,
            modifier = Modifier.padding(
                end = 20.dp, bottom = 20.dp
            ),
            onClick = {
                navController.navigate("${ROUTE.CUD.name}/0") {
                    popUpTo(ROUTE.LIST.name) { inclusive = true }
                }
            }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.White
            )
        }


    }


}
package com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.screens

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.congntph34559.fpoly.reviewexamstudentimageapplication.room.model.StudentModel
import com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.navigation.ROUTE
import java.io.File
import java.io.FileOutputStream

@Composable
fun GetLayoutCreateAndUpdate(
    navController: NavHostController,
    studentViewModel: StudentViewModel,
    int: Int?
) {


    val context = LocalContext.current

    val objProd by remember {
        mutableStateOf(studentViewModel.getById(int!!))
    }

    var name by remember {
        mutableStateOf(objProd?.name_ph34559 ?: "")
    }

    var point by remember {
        mutableStateOf(objProd?.point_ph34559?.toString() ?: "")
    }


    var klass by remember {
        mutableStateOf(objProd?.klass_ph34559 ?: "")
    }
    var image by remember {
        mutableStateOf(objProd?.image_ph34559 ?: "")
    }

    //Mo thu vien cua may
    fun saveBitmapToInternalStorage(// cats uri
        context: Context,
        bitmap: Bitmap,
        imageName: String
    ): String {
        val directory = context.filesDir
        val file = File(directory, "$imageName.png")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.close()
        return file.absolutePath
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }



    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            text = if (int == 0) "Add student" else "Update student"
        )
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(150.dp, 120.dp)
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
                .selectable(
                    selected = true,
                    onClick = {
                        launcher.launch("image/*")
                    }
                )
        ) {
            if (bitmap == null && imageUri == null) {
                if (image == "") {
                    Image(
                        painter = painterResource(id = R.drawable.ic_menu_camera),
                        contentDescription = null,
                        modifier = Modifier.size(45.dp)
                    )
                } else {
                    Image(
                        painter = rememberImagePainter(data = File(image)),
                        contentDescription = null,
                        modifier = Modifier
                            .width(145.dp)
                            .height(115.dp)
                            .clip(
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentScale = ContentScale.Crop,
                    )
                }
            } else {
                null
            }

            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images.Media.getBitmap(
                        context
                            .contentResolver, it
                    )
                } else {
                    val source = ImageDecoder.createSource(
                        context
                            .contentResolver, it
                    )
                    bitmap = ImageDecoder.decodeBitmap(source)
                }
                bitmap?.let { btm ->
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .width(145.dp)
                            .height(115.dp)
                            .clip(
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentScale = ContentScale.Crop,
                    )
                }
            }


        }

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            textStyle = TextStyle(
                fontSize = 13.sp,
                color = Color.Black,
                fontFamily = FontFamily.Serif
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = Color.Gray,
                focusedPlaceholderColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),
            placeholder = {
                Text(
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif,
                    text = "Nhap ten sv"
                )
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = point,
            onValueChange = { point = it },
            textStyle = TextStyle(
                fontSize = 13.sp,
                color = Color.Black,
                fontFamily = FontFamily.Serif
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = Color.Gray,
                focusedPlaceholderColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),
            placeholder = {
                Text(
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif,
                    text = "Nhap diem sv"
                )
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = klass,
            onValueChange = { klass = it },
            textStyle = TextStyle(
                fontSize = 13.sp,
                color = Color.Black,
                fontFamily = FontFamily.Serif
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedPlaceholderColor = Color.Gray,
                focusedPlaceholderColor = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(8.dp)
                ),
            shape = RoundedCornerShape(8.dp),
            placeholder = {
                Text(
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif,
                    text = "Nhap lop sv"
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            onClick = {

                if (check(name, point, klass, context, bitmap)) {

                    if (int == 0) {
                        bitmap?.let { btm ->
                            val imagePath = saveBitmapToInternalStorage(
                                context,
                                btm,
                                "product_${System.currentTimeMillis()}"
                            )
                            studentViewModel.insert(
                                StudentModel(
                                    name_ph34559 = name,
                                    point_ph34559 = point.toFloat(),
                                    klass_ph34559 = klass,
                                    image_ph34559 = imagePath
                                )
                            )
                        }

                        Toast.makeText(
                            context, "Them thanh cong", Toast
                                .LENGTH_SHORT
                        ).show()

                    } else {
                        var imagePath: String? = null
                        bitmap?.let { btm ->
                            imagePath = saveBitmapToInternalStorage(
                                context,
                                btm,
                                "product_${System.currentTimeMillis()}"
                            )

                        }
                        objProd?.let {
                            it.name_ph34559 = name
                            it.point_ph34559 = point.toFloat()
                            it.klass_ph34559 = klass
                            it.image_ph34559 =
                                if (bitmap == null && imageUri == null) image
                                else imagePath.toString()
                            studentViewModel.update(
                                it
                            )
                        }
                        Toast.makeText(
                            context, "Update thanh cong", Toast
                                .LENGTH_SHORT
                        ).show()
                    }

                    navController.navigate(ROUTE.LIST.name) {
                        popUpTo(ROUTE.CUD.name) { inclusive = true }
                    }
                }

            }) {
            Text(
                fontFamily = FontFamily.Serif,
                text = if (int == 0) "Add" else "Update"
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.clickable {
                navController.navigate(
                    ROUTE.LIST.name
                ) {
                    popUpTo(ROUTE.CUD.name) {
                        inclusive = true
                    }
                }
            },
            fontSize = 13.sp,
            fontFamily = FontFamily.Serif,
            text = "Go to list"
        )

    }


}

fun check(
    name: String,
    point: String,
    klass: String,
    context: Context,
    bitmap: Bitmap?
): Boolean {

    if (name == "" || point == "" || klass == "" || bitmap == null) {
        Toast.makeText(
            context, "Moi nhap thong tin", Toast
                .LENGTH_SHORT
        ).show()
        return false
    }

    try {
        point.toFloat()
    } catch (e: NumberFormatException) {
        Toast.makeText(
            context, "Diem phai la so", Toast
                .LENGTH_SHORT
        ).show()
        return false
    }



    return true
}

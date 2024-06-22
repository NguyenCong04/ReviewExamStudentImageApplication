package com.congntph34559.fpoly.reviewexamstudentimageapplication.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.congntph34559.fpoly.reviewexamstudentimageapplication.room.dao.StudentDAO
import com.congntph34559.fpoly.reviewexamstudentimageapplication.room.model.StudentModel

@Database(entities = [StudentModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDAO(): StudentDAO
}
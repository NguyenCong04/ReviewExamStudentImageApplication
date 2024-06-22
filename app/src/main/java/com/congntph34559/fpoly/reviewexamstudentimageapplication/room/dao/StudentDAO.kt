package com.congntph34559.fpoly.reviewexamstudentimageapplication.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.congntph34559.fpoly.reviewexamstudentimageapplication.room.model.StudentModel

@Dao
interface StudentDAO {

    @Query("SELECT * FROM tb_student")
    fun getAll(): List<StudentModel>


    @Insert
    fun insert(vararg student: StudentModel)

    @Update
    fun update(student: StudentModel)

    @Delete
    fun delete(student: StudentModel)

    @Query("SELECT * FROM tb_student WHERE id = :id")
    fun getById(id: Int): StudentModel

}
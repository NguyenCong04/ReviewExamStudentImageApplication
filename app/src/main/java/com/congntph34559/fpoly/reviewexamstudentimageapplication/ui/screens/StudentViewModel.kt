package com.congntph34559.fpoly.reviewexamstudentimageapplication.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.congntph34559.fpoly.reviewexamstudentimageapplication.room.db.AppDatabase
import com.congntph34559.fpoly.reviewexamstudentimageapplication.room.model.StudentModel
import kotlinx.coroutines.launch

class StudentViewModel(private val db: AppDatabase) : ViewModel() {

    fun getAll(): List<StudentModel>? {
        var list: List<StudentModel>? = null
        viewModelScope.launch {
            list = db.studentDAO().getAll()
        }
        return list
    }

    fun insert(student: StudentModel) {
        viewModelScope.launch {
            db.studentDAO().insert(student)
        }
    }

    fun update(student: StudentModel) {
        viewModelScope.launch {
            db.studentDAO().update(student)
        }
    }

    fun delete(student: StudentModel) {
        viewModelScope.launch {
            db.studentDAO().delete(student)
        }
    }


    fun getById(id: Int): StudentModel? {
        var student: StudentModel? = null
        viewModelScope.launch {
            student = db.studentDAO().getById(id)
        }
        return student
    }


}
package com.congntph34559.fpoly.reviewexamstudentimageapplication.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_student")
data class StudentModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name_ph34559: String,
    var point_ph34559: Float,
    var klass_ph34559: String,
    var image_ph34559: String
) {

    override fun toString(): String {
        return "Name: $name_ph34559\n" +
                "Point: $point_ph34559\n" +
                "Class: $klass_ph34559"
    }
}
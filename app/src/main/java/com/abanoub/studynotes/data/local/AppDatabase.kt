package com.abanoub.studynotes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abanoub.studynotes.domain.model.Session
import com.abanoub.studynotes.domain.model.Subject
import com.abanoub.studynotes.domain.model.Task

@Database(
    entities = [Subject::class, Task::class, Session::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ColorListConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun subjectDao(): SubjectDao

    abstract fun taskDao(): TaskDao

    abstract fun sessionDao(): SessionDao

}
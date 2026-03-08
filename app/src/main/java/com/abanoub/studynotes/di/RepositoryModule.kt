package com.abanoub.studynotes.di

import com.abanoub.studynotes.data.repository.SessionRepositoryImpl
import com.abanoub.studynotes.data.repository.SubjectRepositoryImpl
import com.abanoub.studynotes.data.repository.TaskRepositoryImpl
import com.abanoub.studynotes.domain.repository.SessionRepository
import com.abanoub.studynotes.domain.repository.SubjectRepository
import com.abanoub.studynotes.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindSubjectRepository(impl: SubjectRepositoryImpl): SubjectRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(impl: SessionRepositoryImpl): SessionRepository

}
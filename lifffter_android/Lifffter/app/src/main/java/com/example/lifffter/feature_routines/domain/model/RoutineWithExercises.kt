package com.example.lifffter.feature_routines.domain.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.lifffter.feature_exercise.data.local.ExerciseEntity
import com.example.lifffter.feature_routines.data.local.RoutineEntity
import com.example.lifffter.feature_routines.data.local.RoutineExerciseCrossRef

data class RoutineWithExercises (
    @Embedded
    val routine: RoutineEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = RoutineExerciseCrossRef::class,
            parentColumn = "routineId",
            entityColumn = "exerciseId" // for telling room how to map these ids to each other
        )
    )
    val exercises: List<ExerciseEntity>
)
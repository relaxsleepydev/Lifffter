package com.example.lifffter.feature_routines.domain.model

import java.util.UUID

data class Routine(
    val id: UUID,
    val name: String,
    val targetMuscleGroup: String
)
package com.example.lifffter.core.domain.model

import java.util.UUID

data class Routine(
    val id: UUID,
    val name: String,
    val targetMuscleGroup: String
)
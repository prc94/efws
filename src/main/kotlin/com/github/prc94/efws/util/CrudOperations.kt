package com.github.prc94.efws.util

enum class CrudOperations {
    READ, CREATE, UPDATE, DELETE;

    companion object {
        fun of(any: Any?): CrudOperations? =
            when(any) {
                is CrudOperations -> any
                is String ->
                    values().find { it.name.equals(any, ignoreCase = true) }
                else -> null
            }
    }
}
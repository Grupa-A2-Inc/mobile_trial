package com.adaptive_tutor_mobile.domain.repository

import com.adaptive_tutor_mobile.data.remote.dto.RegisterRequest
import com.adaptive_tutor_mobile.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun register(request: RegisterRequest): Result<User>
    suspend fun logout(): Result<Unit>
    suspend fun forgotPassword(email: String): Result<Unit>
}

package com.adaptive_tutor_mobile.domain.usecase

import com.adaptive_tutor_mobile.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> = repository.logout()
}

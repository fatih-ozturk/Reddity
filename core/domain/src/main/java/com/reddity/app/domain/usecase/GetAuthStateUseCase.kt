package com.reddity.app.domain.usecase

import com.reddity.app.auth.AuthManager
import com.reddity.app.model.ReddityAuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val authManager: AuthManager
) {
    operator fun invoke(): Flow<ReddityAuthState> = flow {
        val isAuthorized = authManager.currentAuthState.isAuthorized
        val reddityAuthState = ReddityAuthState.of(isAuthorized)
        emit(reddityAuthState)
    }
}

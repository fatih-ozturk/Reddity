package com.reddity.app.model

enum class ReddityAuthState {
    LOGGED_IN,LOGGED_OUT;

    companion object {
        fun of(isAuthorized: Boolean): ReddityAuthState = if (isAuthorized) LOGGED_IN else LOGGED_OUT
    }
}

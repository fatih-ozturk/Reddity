package com.reddity.app.login

import androidx.lifecycle.ViewModel
import com.reddity.app.auth.ReddityAuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val redditAuthManager: ReddityAuthManager,
) : ViewModel(), ReddityAuthManager by redditAuthManager {

}

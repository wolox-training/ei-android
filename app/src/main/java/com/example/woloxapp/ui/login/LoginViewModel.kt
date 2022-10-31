package com.example.woloxapp.ui.login

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.woloxapp.Service.NetworkResponse
import com.example.woloxapp.model.User
import com.example.woloxapp.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(val app: Application) : AndroidViewModel(app) {
    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?>
        get() = _userEmail
    private val _userPassword = MutableLiveData<String?>()
    val userPassword: LiveData<String?>
        get() = _userPassword

    private val sharedPreferencesSaved: SharedPreferences = app.applicationContext.getSharedPreferences(SHARED_PREFERENCES_USERNAME, Context.MODE_PRIVATE)

    private val _emptyFields = MutableLiveData<Boolean?>()
    val emptyFieldsError: MutableLiveData<Boolean?>
        get() = _emptyFields

    private val _validEmail = MutableLiveData<Boolean?>()
    val validEmail: MutableLiveData<Boolean?>
        get() = _validEmail
    private val editor = sharedPreferencesSaved.edit()

    private val userRepository = UserRepository()

    private val _userIsLogged = MutableLiveData<Boolean>()
    val userIsLogged: LiveData<Boolean>
        get() = _userIsLogged

    fun fieldsValidation(emailValue: String, passwordValue: String) {
        val emptyField = emailValue.isEmpty() || passwordValue.isEmpty()
        if (emptyField) _emptyFields.value = emptyField
        else emailValidation(emailValue)
    }
    private fun emailValidation(emailValue: String) {
        _validEmail.value = android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue.trim()).matches()
    }

    fun getUserModel() {
        val savedEmail = sharedPreferencesSaved.getString(USERNAME, null)
        val savedPassword = sharedPreferencesSaved.getString(PASSWORD, null)
        if (savedEmail != null && savedPassword != null) {
            _userEmail.value = savedEmail
            _userPassword.value = savedPassword
        }
        _userIsLogged.value = savedEmail != null && savedPassword != null
    }

    private val _credentialsOk = MutableLiveData<ResponseStatus>(null)
    val credentialsOk: LiveData<ResponseStatus>
        get() = _credentialsOk

    fun login(user: User) {
        if (hasInternetConnection()) {
            viewModelScope.launch {
                when (val responseRepository = userRepository.login(user)) {
                    is NetworkResponse.Success -> {
                        editor.also {
                            it.putString(NAME_USER, responseRepository.response.body()?.data?.name)
                            it.putString(USERNAME, user.email)
                            it.putString(PASSWORD, user.password)
                            it.commit()
                            _credentialsOk.value = ResponseStatus.CredentialsOk
                        }
                    } else -> _credentialsOk.value = ResponseStatus.CredentialsFailure
                }
            }
        } else _credentialsOk.value = ResponseStatus.NetworkError
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    enum class ResponseStatus {
        CredentialsOk,
        CredentialsFailure,
        NetworkError
    }

    companion object {
        const val USERNAME = "USERNAME"
        const val PASSWORD = "PASSWORD"
        private const val NAME_USER: String = "NAME_USER"
        const val SHARED_PREFERENCES_USERNAME = "SHARED_PREFERENCES_USERNAME"
    }
}

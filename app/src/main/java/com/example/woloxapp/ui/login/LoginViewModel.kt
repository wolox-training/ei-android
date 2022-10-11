package com.example.woloxapp.ui.login

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.woloxapp.model.User
import com.example.woloxapp.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class LoginViewModel(private val app: Application) : AndroidViewModel(app) {
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
    private val userRepository = UserRepository()
    fun login(user: User) {
        viewModelScope.launch {
            val response = userRepository.loginUser(user)
            if (response.isSuccessful) {
                val editor = sharedPreferencesSaved.edit()
                editor.also {
                    it.putString(USERNAME, user.email)
                    it.putString(PASSWORD, user.password)
                    it.putString(DATA_USER, Gson().toJson(response.body()))
                    it.commit()
                }
            }
            _userIsLogged.value = response.isSuccessful
        }
    }

    private val _userIsLogged = MutableLiveData<Boolean>()
    val userIsLogged: LiveData<Boolean>
        get() = _userIsLogged

    fun logout() {
        editor.clear()
        editor.commit()
    }
    companion object {
        const val USERNAME = "USERNAME"
        const val PASSWORD = "PASSWORD"
        private const val DATA_USER: String = "DATA_USER"
        const val SHARED_PREFERENCES_USERNAME = "SHARED_PREFERENCES_USERNAME"
    }
}

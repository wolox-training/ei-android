package com.example.woloxapp.ui.login

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.woloxapp.R

class LoginViewModel(private val applicationCustom: Application) : AndroidViewModel(applicationCustom) {
    companion object {
        const val USERNAME = R.string.username.toString()
        const val PASSWORD: String = R.string.password.toString()
        const val SHARED_PREFERENCES_USERNAME: String = R.string.shared_preferences_username.toString()
    }
    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?>
        get() = _userEmail
    private val _userPassword = MutableLiveData<String?>()
    val userPassword: LiveData<String?>
        get() = _userPassword

    private val sharedPreferencesSaved: SharedPreferences = applicationCustom.applicationContext.getSharedPreferences(
        SHARED_PREFERENCES_USERNAME,
        Context.MODE_PRIVATE
    )

    private val _emptyFields = MutableLiveData<Boolean?>()
    val emptyFieldsError: MutableLiveData<Boolean?>
        get() = _emptyFields

    private val _validEmail = MutableLiveData<Boolean?>()
    val validEmail: MutableLiveData<Boolean?>
        get() = _validEmail

    fun fieldsValidation(emailValue: String, passwordValue: String) {
        val valid = emailValue.isEmpty() || passwordValue.isEmpty()
        if (valid) _emptyFields.value = valid
        else emailValidation(emailValue, passwordValue)
    }

    private fun emailValidation(emailValue: String, passwordValue: String) {
        val emailTemp = emailValue.trim()
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailTemp).matches()) {
            val editor = sharedPreferencesSaved.edit()
            editor.also {
                it.putString(USERNAME, emailValue)
                it.putString(PASSWORD, passwordValue)
                it.commit()
            }
            _validEmail.value = true
        } else _validEmail.value = false
    }

    fun getUserModel() {
        val savedEmail = sharedPreferencesSaved.getString(USERNAME, null)
        val savedPassword = sharedPreferencesSaved.getString(PASSWORD, null)
        if (savedEmail != null && savedPassword != null) {
            _userEmail.value = savedEmail
            _userPassword.value = savedPassword
        }
    }

    fun logout() {
        // TODO: editor.clear() then commit()
    }
}

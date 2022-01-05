package com.template.mapsapp.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapsapp.R
import com.template.mapsapp.databinding.FragmentLoginBinding
import com.template.models.LoginUserModel

class LoginFragment : Fragment() {
    private val binding: FragmentLoginBinding by viewBinding(CreateMethod.INFLATE)

    private val loginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setAuthMode()
        setOnClicks()
        return binding.root
    }

    private fun setOnClicks() {
        with(binding) {
            registrationBtn.setOnClickListener {
                setRegistrationMode()
            }

            authBtn.setOnClickListener {
                setAuthMode()
            }

            continueAuthBtn.setOnClickListener {
                val loginOrEmail = (loginOrEmailEditText.text ?: return@setOnClickListener).toString()
                val password = (passwordEditText.text ?: return@setOnClickListener).toString()
                if (checkFieldsAreNotEmpty() ) {
                    if (loginViewModel.checkUserData(loginOrEmail, password)) {
                        val navController = findNavController()
                        navController.navigate(R.id.action_login_fragment_to_navigation_map)
                    }
                    else
                        showToast("Неверный логин или пароль!")
                }
            }

            continueRegBtn.setOnClickListener {
                val login = (loginEditText.text ?: return@setOnClickListener).toString()
                val email = (emailEditText.text ?: return@setOnClickListener).toString()
                val pass1 = (pass1EditText.text ?: return@setOnClickListener).toString()
                val pass2 = (pass2EditText.text ?: return@setOnClickListener).toString()

                if (checkFieldsAreNotEmpty() ) {
                    if (loginViewModel.checkUserAlreadyExist(login, email))
                        showToast("Пользователь уже существует!")
                    else {
                        loginViewModel.addUser(
                            user = LoginUserModel(
                                login = login,
                                password = pass1,
                                email = email
                            )
                        )
                        val navController = findNavController()
                        navController.navigate(R.id.action_login_fragment_to_navigation_map)
                    }
                }
            }
        }
    }

    private fun checkFieldsAreNotEmpty() : Boolean {
        with(binding) {
            if (registrationGroup.visibility == View.VISIBLE) {
                if (loginEditText.text.toString() == "" || emailEditText.text.toString() == "" ||
                    pass1EditText.text.toString() == "" || pass2EditText.text.toString() == "" ) {
                    showToast("Поля не могут быть пустыми!")
                    return false
                }
                if (!acceptSwitch.isChecked) {
                    showToast("Ознакомьтесь с правилами!")
                    return false
                }
                if (pass1EditText.text.toString() != pass2EditText.text.toString()) {
                    showToast("Пароли не совпадают")
                    return false
                }
            }
            if (authenticationGroup.visibility == View.VISIBLE) {
                if ( loginOrEmailEditText.text.toString() == "" || passwordEditText.text.toString() == "" ){
                    showToast("Поля не могут быть пустыми!")
                    return false
                }

            }
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun setRegistrationMode() {
        with(binding) {
            registrationBtn.alpha = 0.7f
            authBtn.alpha = 1f
            registrationGroup.visibility = View.VISIBLE
            authenticationGroup.visibility = View.GONE
        }
    }

    private fun setAuthMode() {
        with(binding) {
            authBtn.alpha = 0.7f
            registrationBtn.alpha = 1f
            authenticationGroup.visibility = View.VISIBLE
            registrationGroup.visibility = View.GONE
        }
    }

}
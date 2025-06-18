package eu.efficientsoft.lpl.ssmoke.mobileapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.efficientsoft.lpl.ssmoke.mobileapp.data_not_used.LoginDataSource
import eu.efficientsoft.lpl.ssmoke.mobileapp.data_not_used.LoginRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
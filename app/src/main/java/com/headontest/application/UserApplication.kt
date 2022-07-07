package com.headontest.application

import android.app.Application
import com.adviserall22spdaslld.model.response.RequestWebService
import com.headontest.model.Repository
import java.util.*

/**
 * A application class where we can define the variable scope to use through out the application.
 */
class UserApplication() : Application() {

       private val webService by lazy { RequestWebService()}
    // A variable for repository.
    val repository by lazy { Repository( webService) }

     fun setAppLanguageToHb() {
        val config = resources.configuration
        val lang = "iw" // Hebrew language code
        val locale = Locale(lang)
        Locale.setDefault(locale)
        config.setLocale(locale)

        createConfigurationContext(config)
//        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
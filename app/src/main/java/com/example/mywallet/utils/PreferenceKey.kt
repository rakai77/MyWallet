package com.example.mywallet.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKey {

    val TOKEN = stringPreferencesKey("token")
    val FIRST_NAME = stringPreferencesKey("first_name")
    val LAST_NAME = stringPreferencesKey("last_name")
    val EMAIL = stringPreferencesKey("email")

}
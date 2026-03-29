package com.readymapeo.mobile.data.api

import com.readymapeo.mobile.data.local.dao.UserDao

object UserApiService {

    private var userDao: UserDao? = null

    fun setUserDao(dao: UserDao) {
        if (userDao == null) {
            userDao = dao
        }
    }

}
package com.example.repositorypattern

import android.content.Context
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.withContext

class MyRepository(context: Context) {
    private val baseURL = "https://api.github.com/"
    private val api = retrofitInit(baseURL)
    private val myDao = MyDatabase.getDatabase(context).myDao

    val repos = myDao.getAll()

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            val repos = api.contributors("bryanhunter", "libcluster")
            val repoDs = repos.map {
                RepoD(it.login, it.contributions.toString())
            }
            myDao.insertAll(repoDs)
        }
    }
}
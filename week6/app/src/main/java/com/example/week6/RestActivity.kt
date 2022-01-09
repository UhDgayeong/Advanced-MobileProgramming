package com.example.week6

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class Owner(val login: String)
data class Repo(val name: String, val owner: Owner, val url: String)
data class Contributor(val login: String, val contributions: Int)
var cashList = mutableMapOf<String, String>()
lateinit var un: String
lateinit var repo: String

interface RestApi {
    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>
    /*
    [
        {
            "id": 74595421,
            "node_id": "MDEwOlJlcG9zaXRvcnk3NDU5NTQyMQ==",
            "name": "2ndProject",
            "full_name": "jyheo/2ndProject",
            "private": false,
            "owner": {
                "login": "jyheo",
                "id": 4907532,
                 ... 생략 ...
            },
            "html_url": "https://github.com/jyheo/2ndProject",
            "description": null,
            "fork": true,
            "url": "https://api.github.com/repos/jyheo/2ndProject",
            ... 생략 ...
        },
        {
            ... 생략 ...
        },
        ... 생략 ...
    ]
    */

    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun contributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<Contributor>
}

class MyViewModel : ViewModel() {
    private val baseURL = "https://api.github.com/"
    private lateinit var api: RestApi

    val response = MutableLiveData<String>()

    init {
        retrofitInit()
        //refreshData()
    }

    fun refreshData(username: String) { // JSON. 데이터 가져오기
        viewModelScope.launch {
            try {
                //val c = api.contributors("square", "retrofit")
                val repos = api.listRepos(username)
                response.value = StringBuilder().apply {
                    repos.forEach {
                        append(it.name)
                        append(" - ")
                        append(it.owner.login)
                        append("\n")
                    }
                }.toString()
                cashList.put(un, repo)
            } catch (e: Exception) {
                if (cashList.containsKey(un)) {
                    response.value = cashList.get(un)
                }
                else
                    response.value = "Failed to connect to the server"
            }
        }
    }

    private fun retrofitInit() { // JSON 변환기 지정
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())

            .build()

        api = retrofit.create(RestApi::class.java)
    }
}

class RestActivity : AppCompatActivity() {
    private lateinit var myViewModel: MyViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest)

        findViewById<Button>(R.id.query).setOnClickListener {
            un = findViewById<EditText>(R.id.usernameText).text.toString()
            myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
            myViewModel.response.observe(this) {
                findViewById<TextView>(R.id.textResponse).text = it
                repo = it
            }
            myViewModel.refreshData(un)
        }

    }
}
package com.capstone.sopanfinder.api

import com.google.gson.annotations.SerializedName

data class Register(

    @field:SerializedName("msg")
    val msg: String
)

data class Login(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("accessToken")
    val accessToken: String,

    @field:SerializedName("email")
    val email: String
)

data class Users(

    @field:SerializedName("Users")
    val users: List<UsersItem>
)

data class UsersItem(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String
)
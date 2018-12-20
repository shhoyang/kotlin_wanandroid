package com.hao.easy.user.repository

import com.hao.easy.base.App
import com.hao.easy.base.model.HttpResult
import com.hao.easy.base.user.User
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author Yang Shihao
 * @date 2018/11/19
 */

object Api : Service by App.instance.appComponent.retrofit().create(Service::class.java)

interface Service {

    @FormUrlEncoded
    @POST("user/register")
    fun register(@Field("username") username: String,
                 @Field("password") password: String,
                 @Field("repassword") confirmPassword: String): Observable<HttpResult<User>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username") username: String,
              @Field("password") password: String): Observable<HttpResult<User>>

    @GET("user/logout/json")
    fun logout(): Observable<HttpResult<User>>
}
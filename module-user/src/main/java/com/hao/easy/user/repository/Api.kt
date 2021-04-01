package com.hao.easy.user.repository

import com.hao.easy.base.model.HttpResult
import com.hao.easy.base.user.User
import com.hao.library.http.HttpManager
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author Yang Shihao
 */

object Api : Service by HttpManager.RETROFIT.create(Service::class.java)

interface Service {

    @FormUrlEncoded
    @POST("user/register")
    fun register(@FieldMap params: HashMap<String, Any>): Observable<HttpResult<User>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@FieldMap params: HashMap<String, Any>): Observable<HttpResult<User>>

    @GET("user/logout/json")
    fun logout(): Observable<HttpResult<User>>
}
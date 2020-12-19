package com.hao.easy.user.repository

import com.hao.easy.base.BaseApplication
import com.hao.easy.base.model.HttpResult
import com.hao.easy.base.user.User
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author Yang Shihao
 * @date 2018/11/19
 */

object Api : Service by BaseApplication.instance.httpManager.retrofit.create(Service::class.java)

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
package com.hao.easy.wan.repository

import com.hao.easy.base.App
import com.hao.easy.base.model.HttpResult
import com.hao.easy.base.model.ListPaged
import com.hao.easy.wan.model.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author Yang Shihao
 * @date 2018/11/19
 */

object Api : Service by App.instance.appComponent.retrofit().create(Service::class.java)

interface Service {

    @GET("banner/json")
    fun getAd(): Observable<HttpResult<ArrayList<Ad>>>

    @GET("wxarticle/chapters/json")
    fun getAuthors(): Observable<HttpResult<ArrayList<Author>>>

    @GET("wxarticle/list/{authorId}/{page}/json")
    fun getWechatArticles(@Path("authorId") authorId: Int = 409, @Path("page") page: Int): Observable<HttpResult<ListPaged<Article>>>

    @GET("project/tree/json")
    fun getProjectType(): Observable<HttpResult<ArrayList<ProjectType>>>

    @GET("article/listproject/{page}/json")
    fun getNewProjectArticles(@Path("page") page: Int): Observable<HttpResult<ListPaged<Article>>>

    @GET("project/list/{page}/json")
    fun getProjectArticles(@Path("page") page: Int, @Query("cid") cid: Int): Observable<HttpResult<ListPaged<Article>>>

    @GET("lg/collect/list/{page}/json")
    fun getMyFav(@Path("page") page: Int): Observable<HttpResult<ListPaged<Article>>>

    @POST("lg/collect/{id}/json")
    fun collect(@Path("id") id: Int): Observable<HttpResult<Void>>

    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollect(@Path("id") id: Int): Observable<HttpResult<Void>>

    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    fun cancelCollectFromFav(@Path("id") id: Int, @Field("originId") originId: Int): Observable<HttpResult<Void>>

    @FormUrlEncoded
    @POST("article/query/{page}/json")
    fun search(@Path("page") page: Int, @Field("k") k: String): Observable<HttpResult<ListPaged<Article>>>

    @GET("tree/json")
    fun getKnowledge(): Observable<HttpResult<ArrayList<Knowledge>>>

    @GET("article/list/{page}/json")
    fun getKnowledgeArticle(@Path("page") page: Int, @Query("cid") id: Int): Observable<HttpResult<ListPaged<Article>>>
}
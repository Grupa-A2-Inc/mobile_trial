package com.adaptive_tutor_mobile.data.remote.api

import com.adaptive_tutor_mobile.data.remote.dto.EnrolledCourseDto
import com.adaptive_tutor_mobile.data.remote.dto.ResponseCourseDto
import retrofit2.Response
import retrofit2.http.GET

interface CourseApi {

    // GET /api/v1/courses/public
    @GET("api/v1/courses/public")
    suspend fun getPublicCourses(): Response<List<ResponseCourseDto>>

    // GET /api/v1/students/me/courses
    @GET("api/v1/students/me/courses")
    suspend fun getEnrolledCourses(): Response<List<EnrolledCourseDto>>
}

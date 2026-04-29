package com.adaptive_tutor_mobile.presentation.home.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adaptive_tutor_mobile.data.remote.api.CourseApi
import com.adaptive_tutor_mobile.data.remote.dto.EnrolledCourseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class CoursesUiState {
    object Loading : CoursesUiState()
    data class Success(val courses: List<EnrolledCourseDto>) : CoursesUiState()
    data class Error(val message: String) : CoursesUiState()
}

@HiltViewModel
class StudentViewModel @Inject constructor(
    private val courseApi: CourseApi
) : ViewModel() {

    private val _coursesState = MutableStateFlow<CoursesUiState>(CoursesUiState.Loading)
    val coursesState: StateFlow<CoursesUiState> = _coursesState.asStateFlow()

    init {
        loadEnrolledCourses()
    }

    fun loadEnrolledCourses() {
        viewModelScope.launch {
            _coursesState.value = CoursesUiState.Loading
            try {
                val response = courseApi.getEnrolledCourses()
                if (response.isSuccessful) {
                    _coursesState.value = CoursesUiState.Success(response.body() ?: emptyList())
                } else {
                    _coursesState.value = CoursesUiState.Error("Nu s-au putut încărca cursurile (cod ${response.code()})")
                }
            } catch (e: Exception) {
                _coursesState.value = CoursesUiState.Error(e.message ?: "Eroare necunoscută")
            }
        }
    }
}

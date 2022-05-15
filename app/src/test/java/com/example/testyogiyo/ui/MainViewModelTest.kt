package com.example.testyogiyo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testyogiyo.CoroutinesTestExtension
import com.example.testyogiyo.data.database.repository.LocalUserRepository
import com.example.testyogiyo.data.database.repository.LocalUserRepositoryImpl
import com.example.testyogiyo.data.remote.repository.UserRepository
import com.example.testyogiyo.data.remote.repository.UserRepositoryImpl
import com.example.testyogiyo.data.remote.response.User
import com.example.testyogiyo.repository.FakeApiUserRepository
import com.example.testyogiyo.repository.FakeLocalUserRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.jupiter.api.AfterEach

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@ExtendWith(CoroutinesTestExtension::class)
class MainViewModelTest {

    private lateinit var viewModel : MainViewModel


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var apiRepository = mock(UserRepository::class.java)

    private var localUserRepository  = mock(LocalUserRepository::class.java)

    @BeforeEach
    fun setUp() {

        viewModel = MainViewModel(
            apiRepository,localUserRepository
        )

    }
    @AfterEach
    fun tearDown(){
    }

    @DisplayName("유저 unlike 상태에서 좋아요 클릭시 allUser 변수에 추가 되는지 테스트")
    @Test
    fun test() = runBlocking {
        var testUser = User("test","test")
        viewModel.insertUserToLocal(testUser)
        assertThat(viewModel.allLocalUser.value).contains(testUser)
    }


}
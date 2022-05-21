package com.example.searchgituser.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import com.example.searchgituser.CoroutinesTestExtension
import com.example.searchgituser.InstantExecutorExtension
import com.example.searchgituser.data.database.entity.UserEntity
import com.example.searchgituser.data.database.repository.LocalUserRepository
import com.example.searchgituser.data.remote.GithubApi
import com.example.searchgituser.data.remote.repository.UserRepository
import com.example.searchgituser.data.remote.response.GitResponse
import com.example.searchgituser.data.remote.response.User
import com.example.searchgituser.util.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*


@ExperimentalCoroutinesApi
@ExtendWith(
    CoroutinesTestExtension::class,
    InstantExecutorExtension::class
)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    private var apiRepository = mock(UserRepository::class.java)
    private var localUserRepository = mock(LocalUserRepository::class.java)


    @BeforeEach
    fun setUp() = runBlocking {
        val testList = listOf<UserEntity>()
        `when`(localUserRepository.getAllUser())
            .thenReturn(flowOf(testList))
        viewModel = MainViewModel(apiRepository, localUserRepository)
    }

    @DisplayName("DB로 부터 모든 User정보 가져와 allLocalUser 변수 적용 하는지에 대한 테스트")
    @Test
    fun getAllUserFromDBAndSetallLocalUser() = runBlocking {
        val testList = listOf(UserEntity("1", "testimg"), UserEntity("2", "testImg2"))
        val result = testList.map { it.toUserFromUserEntity() }

        `when`(localUserRepository.getAllUser())
            .thenReturn(flowOf(testList))
        viewModel.getAllUserFromLocal()
        assertThat(viewModel.allLocalUser.value).isEqualTo(result)
    }


    @DisplayName("DB로 부터 모든 User 정보 받아와 localUsers에 적용하는지 테스트")
    @Test
    fun getAllUserFromDBAndSetlocalUsers() = runBlocking {
        val testList = listOf(UserEntity("1", "testimg"), UserEntity("2", "testImg2"))
        val result = testList.map { it.toUserFromUserEntity() }


        `when`(localUserRepository.getAllUser())
            .thenReturn(flowOf(testList))

        viewModel.getAllUserFromLocal()

        assertThat(
            viewModel.localUsers.getOrAwaitValue()
        ).isEqualTo(result)
    }


    @DisplayName("유저 unlike 상태에서 좋아요 클릭시 allUser 변수에 추가 되는지 테스트")
    @Test
    fun addToAllUserVariableTest() {
        val testUser = User("test", "test")
        viewModel.insertUserToLocal(testUser)
        assertThat(viewModel.allLocalUser.value).contains(testUser)
    }

    @DisplayName("유저 unlike 상태에서 좋아요 click시 localUser에 반영 되는지 테스트")
    @Test
    fun addToLocalUserVariableTest(){
        val testUser = User("test", "test")
        viewModel.insertUserToLocal(testUser)
        assertThat(viewModel.localUsers.getOrAwaitValue()).contains(testUser)
    }

    @DisplayName("좋아요 비활성화 시 allLocalUser에서 삭제되는지 테스트")
    @Test
    fun doUnLikeUserAndDeleteAllLocalUserTest(){
        val testUser = User("test","test")
        viewModel.allLocalUser.value.add(testUser)
        viewModel.deleteUserFromLocal(testUser)
        assertThat(viewModel.allLocalUser.value).doesNotContain(testUser)
    }

    @DisplayName("remoteUser에 값이 있고 좋아요 비활성화 시 user의 isLike가 false가 되는지")
    @Test
    fun remoteUserContainsUserAndDoUnLikeUserTest(){
        val testUser = User("test","test",true)
        viewModel.remoteUser.value = listOf(testUser)
        viewModel.deleteUserFromLocal(testUser)
        assertThat(testUser.isLike).isFalse()
    }

    @DisplayName("서버에서 데이터 받아 resultState에 잘 적용하는지 테스트")
    @Test
    fun setResultStateFromServerTest()= runBlocking{
        val data = PagingData.from(listOf(User("test","test")))

        `when`(apiRepository.getSearchUser(anyString(), anyList()))
            .thenReturn(flowOf(data))

        viewModel.getUserFromRemote("test")

        println(viewModel.resultState.getOrAwaitValue() == data)

        val differ = AsyncPagingDataDiffer(
            diffCallback = DiffcallbackTest(),
            workerDispatcher = Dispatchers.Main
        )
    }


}

class DiffcallbackTest : DiffUtil.ItemCallback<User>(){
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.avatar_url == newItem.avatar_url
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}

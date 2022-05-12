package com.example.testyogiyo.room.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.testyogiyo.data.database.UserInfoDatabase
import com.example.testyogiyo.data.database.dao.UserDao
import com.example.testyogiyo.data.database.entity.UserEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class UserDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_user_database")
    lateinit var database : UserInfoDatabase
    private lateinit var dao : UserDao
    

    @Before
    fun init(){
        hiltRule.inject()
        dao = database.userDao
    }

    @After
    fun tearDown(){
        database.close()
    }

    @DisplayName("유저가 있을 시 모든 유저 가져오기 테스트")
    @Test
    fun getAllUserIfDBNotEmpty() = runBlocking {
        val testUser = UserEntity("testid","testimg")
        val otherTestUser = UserEntity("testid2","testimg2")

        dao.insertUser(testUser)
        dao.insertUser(otherTestUser)

        val users = dao.getAllUser()
        val result = users.contains(testUser) && users.contains(otherTestUser)
        assertEquals(result,true)
    }

    @DisplayName("USER DAO INSERT 테스트")
    @Test
    fun insertUserTest() = runBlocking {
        val testUser = UserEntity("testid","testimg")
        dao.insertUser(testUser)

        val allTestUser = dao.getAllUser()
        val isContainTestUser = allTestUser.contains(testUser)
        assertEquals(isContainTestUser,true)
    }

    @DisplayName("USER DAO delete 테스트")
    @Test
    fun deleteUserTest() = runBlocking {
        val testUser = UserEntity("testid","testimg")
        dao.insertUser(testUser)
        dao.deleteUser("testid")

        val result = dao.getAllUser().contains(testUser)

        assertEquals(result,false)

    }

    @DisplayName("해당 user가 있을 시 User 찾기")
    @Test
    fun findUserIfUserIsInDB() = runBlocking {
        val testUser = UserEntity("testid","testimg")
        dao.insertUser(testUser)
        val result = dao.findUser("testid").contains(testUser)
        assertEquals(result,true)
    }

    @DisplayName("해당 user가 없을 시 User 찾기")
    @Test
    fun findUserIfUserIsNotInDB() = runBlocking {
        val testUser = UserEntity("testid","testimg")
        val result = dao.findUser("testid").contains(testUser)
        assertEquals(result,false)
    }



}
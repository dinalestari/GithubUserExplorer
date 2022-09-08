package io.dina.githubuser.domain.usecases

import io.dina.githubuser.ResultWrapper
import io.dina.githubuser.data.services.GithubService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class GetUserDetailUseCaseTest {

    lateinit var getUserDetailUseCase: GetUserDetailUseCase

    @MockK
    lateinit var githubService: GithubService

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getUserDetailUseCase = GetUserDetailUseCase(githubService)
    }

    @Test
    fun `get user detail test`() {
        runTest {
            coEvery { githubService.getUser(any()) }.coAnswers { Response.success(null) }
            val result = getUserDetailUseCase.execute("")
            Assert.assertEquals(ResultWrapper.Error("User Not Found"), result)
        }
    }
}
package com.hectorfortuna.rickandmorty.view.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.hectorfortuna.rickandmorty.core.State
import com.hectorfortuna.rickandmorty.data.model.*
import com.hectorfortuna.rickandmorty.data.repository.CharacterRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.ExpectedException
import org.mockito.Mockito.*
import org.mockito.exceptions.base.MockitoException
import java.io.IOException

@ExperimentalCoroutinesApi
internal class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private var repositoryMock = mock(CharacterRepositoryImpl::class.java)
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = HomeViewModel(repositoryMock, Dispatchers.IO)
    }

    @After
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
        Dispatchers.resetMain()
    }

    @Test
    fun `test getCharacters loading success`() = testCoroutineDispatcher.runBlockingTest {
        testCoroutineDispatcher.pauseDispatcher()

        doReturn(mockCharacterResponse()).`when`(repositoryMock).getCharacters(anyInt())
        viewModel.getCharacters(anyInt())

        testCoroutineDispatcher.resumeDispatcher()

        Truth.assertThat(viewModel.response.value).isEqualTo(State.loading<CharactersResponse>(true))
    }

    @Test
    fun `test getCharacters success`() = testCoroutineDispatcher.runBlockingTest {
        testCoroutineDispatcher.pauseDispatcher()

        doReturn(mockCharacterResponse()).`when`(repositoryMock).getCharacters(anyInt())
        viewModel.getCharacters(anyInt())

        testCoroutineDispatcher.resumeDispatcher()

        Truth.assertThat(viewModel.response.value).isEqualTo(State.loading<CharactersResponse>(true))
        Truth.assertThat(viewModel.response.value).isEqualTo(State.success(mockCharacterResponse()))
    }

    @Test(expected = MockitoException::class)
    fun `test getCharacters error exception`() = testCoroutineDispatcher.runBlockingTest {
        testCoroutineDispatcher.pauseDispatcher()

        doThrow(IOException::class.java).`when`(repositoryMock).getCharacters(anyInt())

        viewModel.getCharacters(anyInt())

        testCoroutineDispatcher.resumeDispatcher()

        Truth.assertThat(viewModel.response.value).isEqualTo(State.loading<CharactersResponse>(true))
        Truth.assertThat(viewModel.response.value).isEqualTo(State.error<CharactersResponse>(IOException()))
    }

    private fun mockCharacterResponse() = CharactersResponse(info = mockInfo(), listOf(mockResults()))

    private fun mockCharacterResponseError() = CharactersResponse(mockInfo(), listOf())

    private fun mockInfo() = Info(1, 1, "next", "previous")

    private fun mockResults() = Results(
        1, "name", "status", "species", "type", "gender",
        mockOrigin(), mockLocation(), "image", emptyList(), "url", "created"
    )

    private fun mockOrigin() = Origin(
        "name", "url"
    )
    private fun mockLocation() = Location(
        "name", "url"
    )

}
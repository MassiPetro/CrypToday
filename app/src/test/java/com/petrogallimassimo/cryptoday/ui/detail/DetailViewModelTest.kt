package com.petrogallimassimo.cryptoday.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.petrogallimassimo.cryptoday.data.CryptoDetail
import com.petrogallimassimo.cryptoday.data.RepositoryImpl
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
internal class DetailViewModelTest {

    private val repository = mockk<RepositoryImpl>(relaxed = true)
    private lateinit var viewModel: DetailViewModel

    private val mockCryptoDetail = CryptoDetail(
        description = CryptoDetail.CryptoDescription("description"),
        links = null
    )

    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        viewModel = DetailViewModel(repository)
    }

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getDetailCrypto() = runTest {
        every { repository.getDetailCrypto("1") } returns flowOf(mockCryptoDetail)

        viewModel.getDetailCrypto("1")
        val expected = mockCryptoDetail.mapToCryptoDetailUIModel()

        Assert.assertEquals(expected, viewModel.detailCryptoLiveData.value)

    }

    @Test
    fun getEur() {
        val testValue = 10.12345F
        val result = viewModel.getEur(testValue)
        Assert.assertEquals("10.123€", result)
    }

    @Test
    fun getDouble() {
        val testValue = 10.12345F
        val result = viewModel.getDouble(testValue)
        Assert.assertEquals("10.123", result)
    }
}
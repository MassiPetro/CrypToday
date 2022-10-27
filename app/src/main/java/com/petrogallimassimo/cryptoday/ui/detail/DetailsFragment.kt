package com.petrogallimassimo.cryptoday.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.petrogallimassimo.cryptoday.R
import com.petrogallimassimo.cryptoday.data.ApiFactory
import com.petrogallimassimo.cryptoday.data.ApiService
import com.petrogallimassimo.cryptoday.data.RepositoryImpl
import com.petrogallimassimo.cryptoday.databinding.FragmentDetailBinding
import com.petrogallimassimo.cryptoday.domain.model.CryptoDetailUIModel
import com.petrogallimassimo.cryptoday.domain.model.CryptoMarketChartUIModel
import com.petrogallimassimo.cryptoday.domain.model.CryptoUIModel
import java.util.ArrayList

class DetailsFragment : Fragment() {
    private val repository = RepositoryImpl(ApiFactory.buildService(ApiService::class.java))

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels {
        DetailViewModel.ViewModelFactory(repository)
    }

    private val args: DetailsFragmentArgs by navArgs()
    private var selectedCrypto: CryptoUIModel? = null
    private var detailCrypto: CryptoDetailUIModel? = null
    private val lineValues = ArrayList<Entry>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        selectedCrypto = args.selectedCrypto

        setViews()
        setObservers()
        setListeners()

        return binding.root
    }

    private fun setViews() {
        selectedCrypto?.id?.let {
            viewModel.getDetailCrypto(it)
            viewModel.getMarketChartCrypto(it)
        }


        with(binding) {
            tvName.text = selectedCrypto?.name
            tvSymbol.text = selectedCrypto?.symbol?.uppercase()
            imgLogo.let {
                Glide.with(requireContext())
                    .load(selectedCrypto?.image)
                    .into(it)
            }
            val currentPrice = selectedCrypto?.currentPrice.toString() + " €"
            tvPrice.text = currentPrice
        }
    }

    private fun setListeners() {
        binding.topAppbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.tvWebsite.setOnClickListener {
            val url = detailCrypto?.homepage?.get(0)
            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
            startActivity(urlIntent)
        }
    }

    private fun setObservers() {
        viewModel.detailCryptoLiveData.observe(viewLifecycleOwner) {
            detailCrypto = it
            binding.tvDesc.text = Html.fromHtml(detailCrypto?.description, FROM_HTML_MODE_COMPACT)
            binding.tvAbout.text = getString(R.string.about, selectedCrypto?.name)
            binding.tvAbout.visibility = VISIBLE
            binding.cardDesc.visibility = VISIBLE
        }

        viewModel.marketChartCryptoLiveData.observe(viewLifecycleOwner) { list ->
            setChart(list)
        }
    }

    private fun setChart(listMarketChart: List<CryptoMarketChartUIModel>) {
        lineValues.addAll(
            listMarketChart.map {
                Entry(
                    it.dateMillis.toFloat(),
                    it.eurValue.toFloat()
                )
            }
        )

        // remove last value which is current price of crypto
        lineValues.removeAt(7)

        val lineDataset = LineDataSet(lineValues, null)

        lineDataset.color =
            resources.getColor(R.color.md_theme_light_primaryContainer, requireActivity().theme)

        lineDataset.circleRadius = 5F
        lineDataset.setDrawFilled(true)
        lineDataset.valueTextSize = 12F
        lineDataset.fillColor =
            resources.getColor(R.color.md_theme_light_primaryContainer, requireActivity().theme)
        lineDataset.setCircleColor(
            resources.getColor(
                R.color.md_theme_light_primary,
                requireActivity().theme
            )
        )
        lineDataset.mode = LineDataSet.Mode.CUBIC_BEZIER

        val data = LineData(lineDataset)
        data.setValueFormatter(EurFormatter())
        with(binding) {
            lineChart.description.isEnabled = false
            lineChart.xAxis.valueFormatter = DateFormatter()
            lineChart.xAxis.setLabelCount(7, true)
            lineChart.axisLeft.valueFormatter = EurFormatter()
            lineChart.axisRight.valueFormatter = EurFormatter()
            lineChart.data = data
            lineChart.invalidate()
            cardChart.visibility = VISIBLE
        }
    }

    inner class DateFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return viewModel.getDate(value)
        }
    }

    inner class EurFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return viewModel.getEur(value)
        }

        override fun getPointLabel(entry: Entry): String {
            return viewModel.getDouble(entry.y)
        }
    }
}
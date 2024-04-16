package com.enriquepalmadev.appmarvel.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.data.series.api.utils.Constants
import com.enriquepalmadev.appmarvel.data.series.api.utils.GenericException
import com.enriquepalmadev.appmarvel.data.series.api.utils.UnauthorizedError
import com.enriquepalmadev.appmarvel.ui.view.adapterfilmsandseries.FilmSerieAdapter
import com.enriquepalmadev.appmarvel.databinding.FragmentFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.ui.viewmodel.FilmSerieUIState
import com.enriquepalmadev.appmarvel.ui.viewmodel.FilmSerieViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FilmSerieFragment : Fragment() {

    private val fsViewModel: FilmSerieViewModel by viewModels()
    private lateinit var fsAdapter : FilmSerieAdapter
    private lateinit var fsBinding: FragmentFilmsSeriesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fsBinding = FragmentFilmsSeriesBinding.inflate(inflater, container, false)
        return fsBinding.root // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendingListenerToAdapterItems()
        btnOrderByOnClick()
        svOnQueryTextChange()
        initObserver()

        fsViewModel.getAllSeriesListFromAPI()
    }

    private fun initObserver(){
        fsViewModel.uiState.onEach { uiState ->
            when(uiState){
                is FilmSerieUIState.Error -> {
                    when(uiState.error){
                        is GenericException -> {
                            setErrorView(uiState.error.code.toString(), uiState.error.msg)
                            showErrorView(true)
                            showAuthorizedErrorView(false)
                        }
                        UnauthorizedError -> {
                            setErrorView(getString(R.string.title_401), getString(R.string.msg_401))
                            showErrorView(false)
                            showAuthorizedErrorView(true)
                        }
                    }
                    // With uiState, I can access to the list that returns (and emits) the state
                    showLoading(false)
                }
                is FilmSerieUIState.SimpleError -> {
                    Toast.makeText(context, uiState.msgError, Toast.LENGTH_LONG).show()
                    showLoading(false)
                    showErrorView(false)
                    showAuthorizedErrorView(false)
                }
                FilmSerieUIState.Loading -> {
                    showLoading(true)
                    showErrorView(false)
                    showAuthorizedErrorView(false)
                }
                is FilmSerieUIState.ListReceived -> {
                    initRecyclerView(uiState.list)
                    showLoading(false)
                    showErrorView(false)
                    showAuthorizedErrorView(false)
                }

                is FilmSerieUIState.ItemClicked -> {
                    val action = FilmSerieFragmentDirections.actionFilmsAndSeriesFragmentToItemDetailsFilmsSeriesFragment(uiState.idSerie)
                    findNavController().navigate(action)
                    fsViewModel.done()
                    showLoading(false)
                    showErrorView(false)
                    showAuthorizedErrorView(false)
                }
            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showLoading(visible: Boolean){
        fsBinding.apply {
            loading.isVisible = visible
        }
    }
    private fun setErrorView(code: String, msg: String) {
        fsBinding.apply {
            txtErrorCode.text = code
            txtErrorMsg.text = msg
        }
    }

    private fun showErrorView(visible: Boolean){
        fsBinding.apply {

            if(visible){
                imgError.isVisible = true
                txtErrorCode.isVisible = true
                txtErrorMsg.isVisible = true

                searchViewSeriesAndFilms.isVisible = false
                btnOrderby.isVisible = false

            } else {
                imgError.isVisible = false
                txtErrorCode.isVisible = false
                txtErrorMsg.isVisible = false

                searchViewSeriesAndFilms.isVisible = true
                btnOrderby.isVisible = true
            }

        }
    }

    private fun showAuthorizedErrorView(visible: Boolean){
        fsBinding.apply {
            imgError.setImageResource(R.drawable.thanos_unauthorized)

            if(visible){
                imgError.isVisible = true
                txtErrorCode.isVisible = true
                txtErrorMsg.isVisible = true

                searchViewSeriesAndFilms.isVisible = false
                btnOrderby.isVisible = false

            } else {
                imgError.isVisible = false
                txtErrorCode.isVisible = false
                txtErrorMsg.isVisible = false

                searchViewSeriesAndFilms.isVisible = true
                btnOrderby.isVisible = true
            }

        }
    }

    private fun sendingListenerToAdapterItems() {
        fsAdapter = FilmSerieAdapter(::onFilmSerieClicked) //"::" -> This expression is used when we have a function that returns a Unit (void in Java)
    }

    private fun initRecyclerView(list: List<FilmSerieModel>){
        fsBinding.rvFilmsSeries.adapter = fsAdapter // Setting the Adapter in the RecyclerView
        fsAdapter.updateList(list) // Updated the list in the Adapter and, in consequence, in the RecyclerView
    }

    private fun onFilmSerieClicked(filmOrSerie: FilmSerieModel){
        fsViewModel.transferToDataDetail(filmOrSerie)
    }

    private fun btnOrderByOnClick(){
        fsBinding.btnOrderby.setOnClickListener { showDialogOrderBy() }
    }

    // Dialog to select the items order
    private fun showDialogOrderBy() {

        var selectedItemIndex :Int = 0
        val arrayItemsOrderBy = arrayOf(
            getString(R.string.orderby_year),
            getString(R.string.orderby_alphabet),
            getString(R.string.orderby_fav_first),
            getString(R.string.orderby_fav_only)
        )
        var selectedItem = arrayItemsOrderBy[selectedItemIndex]

        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(getString(R.string.dialog_title))
                .setSingleChoiceItems(arrayItemsOrderBy, selectedItemIndex) {dialog, which ->
                    selectedItemIndex = which
                    selectedItem = arrayItemsOrderBy[which]
                }
                .setPositiveButton(getString(R.string.dialog_ok)){dialog, which ->
                    fsViewModel.orderListBy(selectedItem, requireContext())
                }
                .setNegativeButton(getString(R.string.dialog_cancel)){dialog, which ->
                }
                .show()
        }
    }

    private fun svOnQueryTextChange(){
        fsBinding.searchViewSeriesAndFilms.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String): Boolean {
                fsViewModel.filteringByName(newText)
                return false
            }
        })
    }
}
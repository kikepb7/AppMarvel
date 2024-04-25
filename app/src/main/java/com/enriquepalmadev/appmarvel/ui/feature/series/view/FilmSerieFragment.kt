package com.enriquepalmadev.appmarvel.ui.feature.series.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.enriquepalmadev.appmarvel.R
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.CustomError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.EmptyError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.UnauthorizedError
import com.enriquepalmadev.appmarvel.data.feature.series.api.utils.UnknownHostError
import com.enriquepalmadev.appmarvel.ui.feature.series.view.adapterfilmsandseries.FilmSerieAdapter
import com.enriquepalmadev.appmarvel.databinding.FragmentFilmsSeriesBinding
import com.enriquepalmadev.appmarvel.domain.feature.series.models.FilmSerieModel
import com.enriquepalmadev.appmarvel.ui.feature.series.viewmodel.FilmSerieUIState
import com.enriquepalmadev.appmarvel.ui.feature.series.viewmodel.FilmSerieViewModel
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
                    showLoading(false)
                    when(uiState.error){
                        is CustomError -> {
                            setErrorView(uiState.error.code.toString(), uiState.error.msg)
                            showErrorView(true, R.drawable.groot_error)
                        }
                        UnauthorizedError -> {
                            setErrorView(getString(R.string.title_401), getString(R.string.msg_401))
                            showErrorView(true, R.drawable.thanos_unauthorized)
                        }

                        EmptyError -> {
                            setErrorView(getString(R.string.title_empty_error), getString(R.string.msg_empty_error))
                            showErrorView(true, R.drawable.deadpool_no_connection)
                        }

                        UnknownHostError -> {
                            setErrorView(getString(R.string.title_unknown_host_error), getString(R.string.msg_unknown_host_error))
                            showErrorView(true, R.drawable.captain_empty)
                        }
                    }
                }
                is FilmSerieUIState.Exception -> {
                    Toast.makeText(context, uiState.msgError, Toast.LENGTH_LONG).show()
                    showLoading(false)
                    showErrorView(false, null)
                }
                FilmSerieUIState.Loading -> {
                    showErrorView(false, null)
                    showLoading(true)
                }
                is FilmSerieUIState.ListReceived -> {
                    initRecyclerView(uiState.list)
                    showLoading(false)
                    showErrorView(false, null)
                }

                is FilmSerieUIState.ItemClicked -> {
                    val action = FilmSerieFragmentDirections.actionFilmsAndSeriesFragmentToItemDetailsFilmsSeriesFragment(uiState.idSerie)
                    findNavController().navigate(action)
                    fsViewModel.done()
                    showLoading(false)
                    showErrorView(false, null)
                }

            }
        }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showLoading(visible: Boolean){
        fsBinding.apply {
            loading.isVisible = visible
            searchViewSeriesAndFilms.isVisible = !visible
            btnOrderby.isVisible = !visible
        }
    }
    private fun setErrorView(code: String, msg: String) {
        fsBinding.apply {
            txtErrorCode.text = code
            txtErrorMsg.text = msg
        }
    }

    private fun showErrorView(visible: Boolean, image: Int?){
        fsBinding.apply {
            if (image != null) {
                imgError.setImageResource(image)
            }

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
        fsAdapter = FilmSerieAdapter(::onFilmSerieClicked, ::onFavIconClicked) //"::" -> This expression is used when we have a function that returns a Unit (void in Java)
    }

    private fun initRecyclerView(list: List<FilmSerieModel>){
        fsBinding.rvFilmsSeries.adapter = fsAdapter // Setting the Adapter in the RecyclerView
        fsAdapter.updateList(list) // Updated the list in the Adapter and, in consequence, in the RecyclerView
    }

    private fun onFilmSerieClicked(id: Int){
        fsViewModel.transferToDataDetail(id)
    }

    private fun onFavIconClicked(id: Int, favState: String, favBtn: ImageView){
        // fsViewModel.favSerie(id, favState) TODO() Function to the database persist
        favBtn.apply {
            if (contentDescription == "on"){
                setImageResource(R.drawable.ic_border_favorite_24dp)
                contentDescription = "off"
            } else if (contentDescription == "off"){
                setImageResource(R.drawable.ic_full_favorite_24dp)
                contentDescription = "on"
            }
        }
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
                    orderListBy(selectedItem, requireContext())
                }
                .setNegativeButton(getString(R.string.dialog_cancel)){dialog, which ->
                }
                .show()
        }
    }

    // Function to order the items of the RecyclerView
    private fun orderListBy(selectedItem: String, context: Context) {
        when(selectedItem) {
            context.getString(R.string.orderby_year) -> fsViewModel.orderListByStartYear()
            context.getString(R.string.orderby_alphabet) -> fsViewModel.orderListByAlphabet()
            // getString(R.string.orderby_fav_first)-> arrayList.sortedBy { it.name }
            // getString(R.string.orderby_fav_only)-> arrayList.sortedBy { it.name }
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
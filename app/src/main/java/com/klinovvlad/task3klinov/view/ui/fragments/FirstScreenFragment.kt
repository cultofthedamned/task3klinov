package com.klinovvlad.task3klinov.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.klinovvlad.task3klinov.R
import com.klinovvlad.task3klinov.databinding.FragmentFirstScreenBinding
import com.klinovvlad.task3klinov.di.MyApplication
import com.klinovvlad.task3klinov.utils.BUNDLE_USER_UUID
import com.klinovvlad.task3klinov.view.adapters.MainAdapter
import com.klinovvlad.task3klinov.viewmodel.FirstScreenViewModel
import com.klinovvlad.task3klinov.viewmodel.FirstScreenViewModelFactory
import javax.inject.Inject

class FirstScreenFragment : Fragment() {
    private lateinit var firstScreenBinding: FragmentFirstScreenBinding
    @Inject
    lateinit var firstScreenViewModelFactory: FirstScreenViewModelFactory
    private val viewModel: FirstScreenViewModel by lazy {
        ViewModelProvider(
            viewModelStore,
            firstScreenViewModelFactory
        ).get(FirstScreenViewModel::class.java)
    }
    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(onItemClick = {
            val bundle = Bundle()
            bundle.putString(BUNDLE_USER_UUID, it.uuid)
            val secondFragment = SecondScreenFragment()
            secondFragment.arguments = bundle
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.main_frame, secondFragment)
                ?.addToBackStack(null)
                ?.commit()
        }, onPageEndReached = {
            viewModel.getUsers()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        firstScreenBinding = FragmentFirstScreenBinding.inflate(
            inflater,
            container,
            false
        )
        return firstScreenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as MyApplication).appComponent.inject(this)
        viewModel.getUsers()
        firstScreenBinding.recyclerviewMain.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = mainAdapter
        }
        viewModel.dataList.observe(viewLifecycleOwner) {
            mainAdapter.submitList(it)
        }
    }
}
package com.example.woloxapp.ui.Home.tablayout.fragments.NewDetail

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.woloxapp.MainActivity
import com.example.woloxapp.R
import com.example.woloxapp.databinding.FragmentNewDetailBinding
import com.example.woloxapp.utils.Constants
import com.example.woloxapp.utils.ToastUtil
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

class NewDetailFragment(
    private val newId: Int
) : Fragment() {
    private lateinit var _binding: FragmentNewDetailBinding
    private val binding get() = _binding
    private lateinit var newDetailViewModel: NewDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBar()
        newDetailViewModel =
            ViewModelProvider(this)[NewDetailViewModel::class.java]
        with(binding) {
            activity?.setActionBar(toolbar)
            newDetailViewModel.getNewDetail(newId)
            newDetailViewModel.newsDetailResponse.observe(viewLifecycleOwner) {
                it?.let { it ->
                    with(it) {
                        commenterTv.text = commenter
                        commentTv.text = comment
                        newDateTv.text = PrettyTime().format(
                            SimpleDateFormat(
                                Constants.DATE_FORMAT,
                                Locale.US
                            ).parse(
                                date
                            )?.time?.let {
                                Date(
                                    it
                                )
                            }
                        )
                        fun setImage(drawable: Int) {
                            newIvLike.setImageResource(drawable)
                        }
                        newIvLike.visibility = View.VISIBLE
                        if (likes.isNotEmpty()) setImage(R.drawable.ic_like_on) else setImage(
                            R.drawable.ic_like_off
                        )

                        Glide.with(avatarIv.context).load(HAMBURGER)
                            .placeholder(ColorDrawable(Color.GRAY))
                            .into(avatarIv)
                    }
                }
            }
            newDetailViewModel.newDetailOk.observe(viewLifecycleOwner) {
                observerUpdatedNewDetail(it)
            }
            swipeRefresh.setOnRefreshListener {
                newDetailViewModel.getNewDetail(newId)
                newDetailViewModel.newDetailOk.observe(viewLifecycleOwner) {
                    swipeRefresh.isRefreshing = it == null
                }
            }
            backIv.setOnClickListener {
                requireContext().startActivity(
                    Intent(
                        requireContext(),
                        MainActivity::class.java
                    )
                )
            }
            newIvLike.setOnClickListener {
                newDetailViewModel.updateLike(newId)
            }
            newDetailViewModel.updateLikeOk.observe(viewLifecycleOwner) {
                observerUpdatedNewDetail(it)
            }
        }
    }

    private fun observerUpdatedNewDetail(it: NewDetailViewModel.ResponseStatus?) {
        with(binding) {
            if (it != null) progressLoadDetail.visibility =
                View.INVISIBLE
            when (it) {
                NewDetailViewModel.ResponseStatus.UpdateLikeOk -> {
                    ToastUtil().showToast(UPDATED_LIKE_OK, requireContext())
                    progressLoadDetail.visibility =
                        View.INVISIBLE
                }
                NewDetailViewModel.ResponseStatus.NewDetailOk -> {
                    progressLoadDetail.visibility =
                        View.INVISIBLE
                }
                NewDetailViewModel.ResponseStatus.UpdateLikeError,
                NewDetailViewModel.ResponseStatus.UpdateLikeFailure,
                NewDetailViewModel.ResponseStatus.NewDetailError,
                NewDetailViewModel.ResponseStatus.NewDetailFailure -> newListErrorMessages()
                null ->
                    progressLoadDetail.visibility =
                        View.VISIBLE
            }
        }
    }

    private fun newListErrorMessages() {
        ToastUtil().showToast(
            CONNECTION_ERROR,
            requireContext()
        )
    }

    private fun setStatusBar() {
        val background =
            getResources().getDrawable(R.drawable.gradient_status_bar)
        val activityWindow = requireActivity().window
        activityWindow.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        activityWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        activityWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activityWindow.setStatusBarColor(getResources().getColor(android.R.color.transparent))
        activityWindow.setBackgroundDrawable(background)
    }

    companion object {
        const val UPDATED_LIKE_OK = "Updated new likes, thank you!"
        const val CONNECTION_ERROR =
            "Couldn't load new right now, try again"
        const val HAMBURGER: String =
            "https://images.unsplash.com/photo-1666879578106-0215a1ec8c2f?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"
    }
}

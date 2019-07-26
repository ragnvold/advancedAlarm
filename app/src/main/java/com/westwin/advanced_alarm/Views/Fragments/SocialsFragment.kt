package com.westwin.advanced_alarm.Views.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.westwin.advanced_alarm.Adapters.SocialsAdapter
import com.westwin.advanced_alarm.Contracts.SocialsContract
import com.westwin.advanced_alarm.Diff.RecyclerViewListener
import com.westwin.advanced_alarm.Models.Social
import com.westwin.advanced_alarm.Presenters.SocialsPresenter
import com.westwin.advanced_alarm.R

class SocialsFragment : Fragment(), SocialsContract.View {

    private var mPresenter: SocialsContract.Presenter = SocialsPresenter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = context!!
        socialAdapter = SocialsAdapter(mContext)
        return inflater.inflate(R.layout.fragment_socials, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.initialize(view)
    }

    override fun attachView(view: View) {
        socialsList = view.findViewById(R.id.socials_list)
    }

    override fun setConfig() {

    }

    override fun attachAdapter() {
        socialAdapter.addSocials(
            arrayOf(
                Social(
                    Social.getSocialImg(activity!!, Social.VK),
                    getString(R.string.vk),
                    Social.getSocialState(activity!!, Social.VK)
                ),
                Social(
                    Social.getSocialImg(activity!!, Social.FB),
                    getString(R.string.fb),
                    Social.getSocialState(activity!!, Social.FB)
                )
            )
        )
        socialsList.adapter = socialAdapter
    }

    override fun attachComponents() {
        socialsList.layoutManager = LinearLayoutManager(activity!!)
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object : RecyclerViewListener {

        @SuppressLint("StaticFieldLeak")
        private lateinit var socialsList: RecyclerView
        @SuppressLint("StaticFieldLeak")
        private lateinit var mContext: Context
        @SuppressLint("StaticFieldLeak")
        private lateinit var socialAdapter: SocialsAdapter

        override fun itemPositionChecker(view: View): Int {
            return socialsList.getChildAdapterPosition(view)
        }

        override fun updateRVData() {
            socialAdapter.notifyDataSetChanged()
        }
    }
}
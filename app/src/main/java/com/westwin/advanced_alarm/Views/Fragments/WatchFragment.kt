package com.westwin.advanced_alarm.Views.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextClock
import android.widget.TextView
import com.westwin.advanced_alarm.Contracts.WatchContract
import com.westwin.advanced_alarm.Presenters.WatchFragmentPresenter
import com.westwin.advanced_alarm.R
import java.util.*

class WatchFragment : Fragment(), WatchContract.View {

    private var listener: OnFragmentInteractionListener? = null

    private var mPresenter: WatchContract.Presenter = WatchFragmentPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_watch, container, false)
        mPresenter.initialize(rootView)
        return rootView
    }

    override fun attachView(view: View) {
        txtClock = view.findViewById(R.id.txtClock)
        gmt = view.findViewById(R.id.gmt)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResumeState()
    }

    override fun redrawWatch() {
        txtClock.refreshDrawableState()
        gmt.text = TimeZone.getDefault().displayName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var txtClock: TextClock
        @SuppressLint("StaticFieldLeak")
        private lateinit var gmt: TextView
    }
}

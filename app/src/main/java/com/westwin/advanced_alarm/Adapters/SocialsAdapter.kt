package com.westwin.advanced_alarm.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.westwin.advanced_alarm.Models.Social
import com.westwin.advanced_alarm.R
import com.westwin.advanced_alarm.Views.Activities.SocialsAuthActivity
import com.westwin.advanced_alarm.Views.Fragments.SocialsFragment

class SocialsAdapter(context: Context) : RecyclerView.Adapter<SocialsAdapter.ViewHolder>(), View.OnClickListener {

    private lateinit var mSocialsList: Array<Social>
    private var mContext: Context = context

    fun addSocials(socials: Array<Social>) {
        mSocialsList = socials
    }

    override fun getItemCount(): Int = mSocialsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.socials_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(mSocialsList[position].img).resize(72, 72).into(holder.socialImg)
        holder.socialName.text = mSocialsList[position].name
        holder.socialState.text = mSocialsList[position].state

        holder.itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val intent = Intent(
            mContext,
            SocialsAuthActivity::class.java
        )
        if (p0 != null)
            when (SocialsFragment.itemPositionChecker(p0)) {
                0 -> {
                    intent.putExtra("id", SocialsFragment.itemPositionChecker(p0))
                }
                1 -> {
                    intent.putExtra("id", SocialsFragment.itemPositionChecker(p0))
                }
            }
        mContext.startActivity(intent)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var socialImg: ImageView = itemView.findViewById(R.id.social_item_picture)
        var socialName: TextView = itemView.findViewById(R.id.social_item_name)
        var socialState: TextView = itemView.findViewById(R.id.social_item_state)
    }
}
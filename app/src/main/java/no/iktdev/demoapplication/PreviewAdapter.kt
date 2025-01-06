package no.iktdev.demoapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import no.iktdev.demoapplication.databinding.AdapterNestedCoverBinding

class PreviewAdapter(): RecyclerView.Adapter<PreviewAdapter.ViewHolder>() {
    class ViewHolder(val view: AdapterNestedCoverBinding): RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        return ViewHolder(AdapterNestedCoverBinding.inflate(inflater, p0, false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

    }
}
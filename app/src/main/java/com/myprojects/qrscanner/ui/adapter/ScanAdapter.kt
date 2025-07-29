package com.myprojects.qrscanner.ui.adapter;

import android.icu.text.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.google.type.Date;
import com.myprojects.qrscanner.R
import com.myprojects.qrscanner.data.model.Scan;


class ScanAdapter(private var scans: List<Scan>) :
        RecyclerView.Adapter<ScanAdapter.ScanViewHolder>() {

class ScanViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
    val content: TextView = itemView.findViewById(R.id.textContent)
    val timestamp: TextView = itemView.findViewById(R.id.textTimestamp)
}

override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): ScanViewHolder {
    val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_scan, parent, false)
    return ScanViewHolder(view)
}

override fun onBindViewHolder(holder: ScanViewHolder, position: Int) {
    val scan = scans[position]
    holder.content.text = scan.content
    holder.timestamp.text = DateFormat.getDateTimeInstance().format(java.util.Date(scan.timestamp))
}

override fun getItemCount() = scans.size

fun updateData(newScans: List<Scan>) {
    scans = newScans
    notifyDataSetChanged()
}
}

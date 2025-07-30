package com.myprojects.qrscanner.ui.adapter;

import android.icu.text.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.myprojects.qrscanner.R
import com.myprojects.qrscanner.data.model.Scan;

/**
 * RecyclerView adapter for displaying QR code scan history.
 * 
 * This adapter manages the display of scan entries in a RecyclerView, providing
 * a clean interface for showing scan history with proper formatting. The adapter
 * handles view creation, binding, and data updates efficiently.
 * 
 * The adapter displays each scan with its content and a formatted timestamp,
 * using a custom layout for each item.
 * 
 * @author Edouard Makon
 * @since 1.0
 */
class ScanAdapter(private var scans: List<Scan>) :
        RecyclerView.Adapter<ScanAdapter.ScanViewHolder>() {

    /**
     * ViewHolder class for scan list items.
     * 
     * This ViewHolder holds references to the UI elements for each scan item,
     * improving performance by avoiding repeated findViewById calls.
     * 
     * @param itemView The root view of the item layout.
     */
    class ScanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * TextView displaying the QR code content.
         * Shows the text extracted from the scanned QR code.
         */
        val content: TextView = itemView.findViewById(R.id.textContent)
        
        /**
         * TextView displaying the scan timestamp.
         * Shows the formatted date and time when the scan was performed.
         */
        val timestamp: TextView = itemView.findViewById(R.id.textTimestamp)
    }

    /**
     * Creates a new ViewHolder for scan items.
     * 
     * This method inflates the item layout and creates a new ViewHolder instance.
     * Called by the RecyclerView when it needs a new ViewHolder for an item.
     * 
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View (not used in this adapter).
     * @return A new ScanViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_scan, parent, false)
        return ScanViewHolder(view)
    }

    /**
     * Binds scan data to the ViewHolder.
     * 
     * This method updates the ViewHolder's views with data from the scan at the
     * specified position. It formats the timestamp for display and sets the
     * QR code content.
     * 
     * @param holder The ViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ScanViewHolder, position: Int) {
        val scan = scans[position]
        holder.content.text = scan.content
        holder.timestamp.text = DateFormat.getDateTimeInstance().format(java.util.Date(scan.timestamp))
    }

    /**
     * Returns the total number of items in the data set.
     * 
     * @return The total number of scans in the adapter's data set.
     */
    override fun getItemCount() = scans.size

    /**
     * Updates the adapter's data set and notifies the RecyclerView of changes.
     * 
     * This method replaces the current data set with new scan data and triggers
     * a full refresh of the RecyclerView. Use this method when the scan list
     * changes (e.g., when new scans are added or the database is updated).
     * 
     * @param newScans The new list of scans to display.
     */
    fun updateData(newScans: List<Scan>) {
        scans = newScans
        notifyDataSetChanged()
    }
}

package postech.cse.servingapp.com.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import postech.cse.servingapp.com.R
import postech.cse.servingapp.com.listdata.Name

class StatusNameListAdapter : RecyclerView.Adapter<StatusNameListAdapter.StatusNameListAdapterViewHolder>(){
    var mStatusNameData : Array<Name>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusNameListAdapterViewHolder {
        val context = parent.context
        val layoutIdForListItem = R.layout.status_name_list_item
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false

        val view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately)
        return StatusNameListAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (null == mStatusNameData) 0 else mStatusNameData!!.size
    }

    override fun onBindViewHolder(holder: StatusNameListAdapterViewHolder, position: Int) {
        val nameItem = mStatusNameData!![position]

        holder.mNameOrder.text = (position + 1).toString()
        holder.mNameName.text = nameItem.name
        holder.mNameTotal.text = nameItem.total.toString()
    }

    inner class StatusNameListAdapterViewHolder(v : View) : RecyclerView.ViewHolder(v){
        val mNameName : TextView
        val mNameTotal : TextView
        val mNameOrder : TextView

        init {
            mNameName = v.findViewById(R.id.tv_name_status_name) as TextView
            mNameTotal = v.findViewById(R.id.tv_name_status_total) as TextView
            mNameOrder = v.findViewById(R.id.tv_name_status_order) as TextView
        }
    }

    fun setNameListData(nameData: Array<Name>?){
        mStatusNameData = nameData
        notifyDataSetChanged()
    }
}
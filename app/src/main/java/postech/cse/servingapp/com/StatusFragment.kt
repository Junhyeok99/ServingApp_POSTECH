package postech.cse.servingapp.com

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import postech.cse.servingapp.com.adapter.StatusMenuListAdapter
import postech.cse.servingapp.com.adapter.StatusNameListAdapter
import postech.cse.servingapp.com.listdata.Name
import postech.cse.servingapp.com.listdata.StoreMenu
import postech.cse.servingapp.com.utilities.MenuJSONUtils
import postech.cse.servingapp.com.utilities.NameJSONUtils
import postech.cse.servingapp.com.utilities.NetworkUtils

class StatusFragment : Fragment() {
    private var mMenuRecyclerView: RecyclerView? = null
    private var mNameRecyclerView: RecyclerView? = null
    private var mStatusMenuListAdapter: StatusMenuListAdapter? = null
    private var mStatusNameListAdapter: StatusNameListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_status, container, false)

        val MenulayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val NamelayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mMenuRecyclerView = view.findViewById(R.id.rv_menu) as RecyclerView
        mNameRecyclerView = view.findViewById(R.id.rv_name) as RecyclerView
        mStatusMenuListAdapter = StatusMenuListAdapter()
        mStatusNameListAdapter = StatusNameListAdapter()

        mMenuRecyclerView!!.layoutManager = MenulayoutManager
        mMenuRecyclerView!!.setHasFixedSize(true)
        mMenuRecyclerView!!.adapter = mStatusMenuListAdapter

        mNameRecyclerView!!.layoutManager = NamelayoutManager
        mNameRecyclerView!!.setHasFixedSize(true)
        mNameRecyclerView!!.adapter = mStatusNameListAdapter

        FetchMenuDataTask().execute()
        FetchNameDataTask().execute()

        return view
    }

    inner class FetchMenuDataTask: AsyncTask<Void, Void, Array<StoreMenu>>() {

        override fun doInBackground(vararg p0: Void?): Array<StoreMenu>? {
            val menuRequestURL = NetworkUtils.buildUrl(false)

            try {
                val menudataResponse = NetworkUtils.getResponseFromHttpUrl(menuRequestURL)

                return MenuJSONUtils.getMenuDataFromJSON(menudataResponse)
            }
            catch (e: Exception){
                e.printStackTrace()
                return null
            }
        }

        override fun onPostExecute(result: Array<StoreMenu>?) {
            if(result != null) {
                mStatusMenuListAdapter!!.setMenuListData(result)
            }
        }
    }

    inner class FetchNameDataTask: AsyncTask<Void, Void, Array<Name>>() {

        override fun doInBackground(vararg p0: Void?): Array<Name>? {
            val nameRequestURL = NetworkUtils.name_buildUrl()

            try {
                val namedataResponse = NetworkUtils.getResponseFromHttpUrl(nameRequestURL)

                return NameJSONUtils.getMenuDataFromJSON(namedataResponse)
            }
            catch (e: Exception){
                e.printStackTrace()
                return null
            }
        }

        override fun onPostExecute(result: Array<Name>?) {
            if(result != null) {
                mStatusNameListAdapter!!.setNameListData(result)
            }
        }
    }
}

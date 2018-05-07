package postech.cse.servingapp.com

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.View
import android.widget.EditText
import android.widget.Toast
import postech.cse.servingapp.com.listdata.StoreMenu
import postech.cse.servingapp.com.utilities.NetworkUtils
import postech.cse.servingapp.com.utilities.OrderJSONUtils

class NameActivity : AppCompatActivity() {
    var customerEditText: TextInputEditText? = null
    var buyerEditText: TextInputEditText? = null
    var OrderList: Array<StoreMenu>? = null
    var table_num: Int = -1
    var total: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)

        OrderList = intent.getSerializableExtra("OrderList") as Array<StoreMenu>
        table_num = intent.getIntExtra("tablenum", -1)
        total = intent.getIntExtra("total", 0)

        customerEditText = findViewById(R.id.et_customer) as TextInputEditText
        buyerEditText = findViewById(R.id.et_buyer) as TextInputEditText
    }

    inner class PushNamePayOrderDataTask: AsyncTask<Void, Void, Int>(){
        override fun doInBackground(vararg p0: Void?): Int {
            var customername = customerEditText!!.text.toString()
            var buyername = buyerEditText!!.text.toString()

            var sendURL = NetworkUtils.buildUrl(OrderList, customername, buyername, table_num, total)

            try{
                var result = NetworkUtils.getResponseFromHttpUrl(sendURL)
                return OrderJSONUtils.getOrderDataFromJSON(applicationContext, result)
            }
            catch(e: Exception){
                e.printStackTrace()
                return -1
            }
        }

        override fun onPostExecute(result: Int?) {
            if(result == 1) {
                var intent = Intent(applicationContext, CompleteActivity::class.java)
                intent.putExtra("tablenum", table_num)
                startActivity(intent)
                finish()
            }
            else
                Toast.makeText(applicationContext, "Error occured. Please try again", Toast.LENGTH_SHORT).show()
        }
    }

    fun confirm_button_click(v: View){
        PushNamePayOrderDataTask().execute()
    }
}

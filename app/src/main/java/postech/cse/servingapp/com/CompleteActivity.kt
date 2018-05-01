package postech.cse.servingapp.com

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import postech.cse.servingapp.com.utilities.NetworkUtils
import postech.cse.servingapp.com.utilities.OrderJSONUtils

class CompleteActivity : AppCompatActivity() {
    var mProgressBar: ProgressBar? = null
    var mImageView: ImageView? = null
    var table_num: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete)

        table_num = intent.getIntExtra("tablenum", -1)

        mProgressBar = findViewById(R.id.pb_complete) as ProgressBar
        mImageView = findViewById(R.id.img_complete) as ImageView

        if(table_num != -1)
            CheckOrderDataTask().execute()
        else{
            Toast.makeText(applicationContext, "Error occured. Please try again", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun complete_button_click(v: View){
        PayActivity.present_screen!!.finish()
        OrderActivity.present_screen!!.finish()
        finish()
    }

    inner class CheckOrderDataTask: AsyncTask<Void, Void, Int>(){
        override fun onPreExecute() {
            super.onPreExecute()

            mProgressBar!!.visibility = View.VISIBLE
            mImageView!!.visibility = View.INVISIBLE
        }

        override fun doInBackground(vararg p0: Void?): Int {
            var sendURL = NetworkUtils.buildUrl(table_num)
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
            if(result == 0)
                CheckOrderDataTask().execute()
            else if(result == 1){
                mProgressBar!!.visibility = View.INVISIBLE
                mImageView!!.visibility = View.VISIBLE
            }
            else if(result == -1) {
                Toast.makeText(applicationContext, "Error occured. Please try again", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(applicationContext, "주문이 취소되었습니다. 다시해주세요!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

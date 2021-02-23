package com.osipsen.weather.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.osipsen.weather.R
import org.json.JSONObject
import java.net.URL

class BlankFragment2 : Fragment() {
    var CITY: String = "-city-"
    var CITYD: String = "-temperature-"
    val API: String = "e9f27d7a3f85307ad5fe961b8d4509ff"

    var rootView = view


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CITY = arguments?.getString("CITYS").toString()
        CITYD = arguments?.getString("city").toString()
        rootView = inflater.inflate(R.layout.fragment_blank2,container,false)
        weatherTask().execute()



        // Inflate the layout for this fragment
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {

        activity!!.supportFragmentManager.popBackStack()
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }


    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            rootView!!.findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            rootView!!.findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            rootView!!.findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/forecast?q=$CITY&lang=ru&units=metric&appid=$API&cnt=28").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)

                val list = jsonObj.getJSONArray("list")
                val city = jsonObj.getJSONObject("city")

                val address = city.getString("name")

                val indexWeath = list.getJSONObject(2);
                val main = indexWeath.getJSONObject("main")
                val weather = indexWeath.getJSONArray("weather").getJSONObject(0)
                val tempMin = "Мин. температура: " + main.getString("temp_min")+"°C"
                val tempMax = "Макс. температура: " + main.getString("temp_max")+"°C"
                val weatherDescription = weather.getString("description")

                val indexWeath1 = list.getJSONObject(10);
                val main1 = indexWeath1.getJSONObject("main")
                val temp1 = main1.getString("temp")+"°C"

                val indexWeath2 = list.getJSONObject(18);
                val main2 = indexWeath2.getJSONObject("main")
                val temp2 = main2.getString("temp")+"°C"
                val date = indexWeath2.getString("dt_txt")

                val indexWeath3 = list.getJSONObject(26);
                val main3 = indexWeath3.getJSONObject("main")
                val temp3 = main3.getString("temp")+"°C"
                val date2 = indexWeath3.getString("dt_txt")

                rootView!!.findViewById<TextView>(R.id.address).text = address
                rootView!!.findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                rootView!!.findViewById<TextView>(R.id.temp).text = CITYD
                rootView!!.findViewById<TextView>(R.id.temp_min).text = tempMin
                rootView!!.findViewById<TextView>(R.id.temp_max).text = tempMax
                rootView!!.findViewById<TextView>(R.id.tomorrow).text = temp1
                rootView!!.findViewById<TextView>(R.id.tomorrow1).text = temp2
                rootView!!.findViewById<TextView>(R.id.tomorrow11).text = date
                rootView!!.findViewById<TextView>(R.id.tomorrow2).text = temp3
                rootView!!.findViewById<TextView>(R.id.tomorrow21).text = date2

                rootView!!.findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                rootView!!.findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                rootView!!.findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                rootView!!.findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }
    }

}
package com.neqabty.yodawy.modules.products.presentation.view.productscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.RetrofitModule
import com.neqabty.yodawy.core.data.CartItems
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.modules.CartActivity
import com.neqabty.yodawy.modules.Medication
import com.neqabty.yodawy.modules.address.presentation.view.adressscreen.AddressViewModel
import com.neqabty.yodawy.modules.products.data.api.ProductApi
import com.neqabty.yodawy.modules.products.data.model.search.Data
import com.neqabty.yodawy.modules.products.data.model.search.SearchResponse
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import com.vlonjatg.progressactivity.ProgressRelativeLayout
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private var list: MutableList<Medication> = ArrayList<Medication>()
    private val mAdapter = SearchAdapter()
    private lateinit var toolbar: Toolbar
    private val productViewModel: ProductViewModel by viewModels()
    private var viewModel: PhoneViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        toolbar = findViewById<Toolbar>(R.id.search_custom_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 0.0f
        findViewById<RecyclerView>(R.id.recycler_view).adapter = mAdapter

//        productViewModel.search("panadol")
//        productViewModel.data.observe(this){
//            Log.e("gfgh", "it[0].name")
////            mAdapter.submitList(it)
//        }





        viewModel = ViewModelProviders.of(this).get(PhoneViewModel::class.java)
        viewModel!!.sendToken("panadol")?.observe(this,
            Observer<ViewState<SearchResponse>> { viewState ->
                when (viewState.status) {
                    ViewState.Status.LOADING -> {
                    }
                    ViewState.Status.SUCCESS -> {
                        Log.e("gfgh", "it[0].name")
                        mAdapter.submitList(viewState.data?.data)
                    }
                    ViewState.Status.ERROR -> {
                        Log.e("gfgh", viewState.message.toString())
                    }

                }
            })







        mAdapter.onItemClickListener = object :
            SearchAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: Data) {
                val intent: Intent = Intent(this@SearchActivity, ProductDetailsActivity::class.java)
                intent.putExtra("product", item)
                startActivity(intent)
            }
        }
        toolbar.findViewById<ImageView>(R.id.back_btn).setOnClickListener { finish() }
        toolbar.findViewById<FrameLayout>(R.id.cart).setOnClickListener { startActivity(Intent(this, CartActivity::class.java)) }

//        setDate()
    }


    private fun setDate(){
        var medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 2, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 2, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 3, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
    }
}

class PhoneViewModel: ViewModel() {

    private var responseLiveData: LiveData<ViewState<SearchResponse>>? = null

    fun sendToken(token: String): LiveData<ViewState<SearchResponse>>? {
        responseLiveData = RemoteRepository().login(token)
        return responseLiveData
    }

}


class RemoteRepository {

    private val serviceApi: ServiceApi = RetrofitModule.instance!!.getService()
    private val loginResponse: MutableLiveData<ViewState<SearchResponse>> =
        MutableLiveData<ViewState<SearchResponse>>()



    fun login(key: String): LiveData<ViewState<SearchResponse>> {
        serviceApi.login(key).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>?, response: Response<SearchResponse>) {
                when {
                    response.code() == 200 -> {// return success
                        loginResponse.value = ViewState.success(response.body()!!)
                    }
                    response.code() == 401 -> {// failure
                        loginResponse.value = ViewState.error("Wrong User id or Password")
                    }
                    else -> {
                        loginResponse.value = ViewState.error("Bad Request")
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>?, t: Throwable) {
                loginResponse.value = ViewState.error(t.message)
            }
        })
        return loginResponse
    }

}

@JvmSuppressWildcards
interface ServiceApi {

    @FormUrlEncoded
    @POST("search/products")
    fun login(@Field("search_term")keyWord: String): Call<SearchResponse>

}
class ViewState<T> private constructor(
    val status: Status,
    val data: T?,
    val message: String?
) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {

        fun <T> success(data: T): ViewState<T> {
            return ViewState(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String?): ViewState<T> {
            return ViewState(
                Status.ERROR,
                null,
                msg
            )
        }

        fun <T> loading(): ViewState<T> {
            return ViewState(
                Status.LOADING,
                null,
                null
            )
        }
    }

}

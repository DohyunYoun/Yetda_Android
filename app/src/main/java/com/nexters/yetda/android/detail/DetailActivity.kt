package com.nexters.yetda.android.detail

import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.detail.DetailAdapter
import com.nexters.yetda.android.detail.DetailViewModel

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.databinding.ActivityDetailBinding
import com.nexters.yetda.android.name.NameActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.item_present_list.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.time.MonthDay
import java.time.format.DateTimeFormatter
import java.util.*

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {
    override val layoutResourceId = R.layout.activity_detail
    override val viewModel: DetailViewModel by viewModel()

    private val TAG = javaClass.simpleName

    var history: History = History()

    override fun initViewStart() {

        history = intent.getParcelableExtra("ITEM")

    }

    override fun initDataBinding() {
        binding.setVariable(BR.vm, viewModel)
        val presents: ArrayList<Present> = ArrayList<Present>()
        presents.addAll(history.presents)

        val adapter = DetailAdapter(presents)
        binding.cardDetail.adapter = adapter

        binding.tvDetailName.text = history.name + resources.getString(R.string.detail)


        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (history.birthday.length == 4) {
                    val newFormatter = DateTimeFormatter.ofPattern("M월 d일", Locale.ENGLISH)
                    val date = MonthDay.of(
                        history.birthday.substring(0, 2).toInt(),
                        history.birthday.substring(2, 4).toInt()
                    )
                    binding.tvDetailBirthday.text = date.format(newFormatter)
                } else {
                    binding.tvDetailBirthday.text = history.birthday
                }
            } else {
                if (history.birthday.length == 4) {
                    binding.tvDetailBirthday.text =
                        "${history.birthday.substring(0, 2)}월 ${history.birthday.substring(2, 4)}일"
                } else {
                    binding.tvDetailBirthday.text = history.birthday
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "* * * e ::: ${e.message}")
            if (history.birthday.length == 4) {
                binding.tvDetailBirthday.text =
                    "${history.birthday.substring(0, 2)}월 ${history.birthday.substring(2, 4)}일"
            } else {
                binding.tvDetailBirthday.text = history.birthday
            }
        }


//        binding.tvDetailBirthday.text = history.birthday
        binding.tvDetailPrice.text =
            "${NumberFormat.getCurrencyInstance(Locale.KOREA).format(history.startPrice)} ~ ${NumberFormat.getCurrencyInstance(
                Locale.KOREA
            ).format(history.endPrice)}"

        viewModel.backBeforeActivityEvent.observe(this, Observer {
            finish()
        })
    }

    override fun initViewFinal() {
    }

}
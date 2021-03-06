package com.nexters.yetda.android.price

import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import com.appyvet.materialrangebar.RangeBar
import com.nexters.yetda.android.BR
import com.nexters.yetda.android.R
import com.nexters.yetda.android.base.BaseActivity
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.databinding.ActivityPriceBinding
import com.nexters.yetda.android.question.QuestionActivity
import org.koin.android.viewmodel.ext.android.viewModel


class PriceActivity : BaseActivity<ActivityPriceBinding, PriceViewModel>() {
    override val layoutResourceId = R.layout.activity_price
    override val viewModel: PriceViewModel by viewModel()

    private val TAG = javaClass.simpleName
    var history = History()
    var leftValue = 0
    var rightValue = 0


    override fun initViewStart() {
        binding.setVariable(BR.vm, viewModel)
        history = viewModel.getUserFromIntent(intent)
    }

    override fun initDataBinding() {
        viewModel.startNextActivityEvent.observe(this, Observer {
            history.startPrice = (leftValue * 10000).toLong()
            history.endPrice = (rightValue * 10000).toLong()

            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("TAGS", viewModel.getTags())
            intent.putExtra("ITEM", history)
            startActivity(intent)
        })
        viewModel.backBeforeActivityEvent.observe(this, Observer {
            finish()
        })
    }

    override fun initViewFinal() {
        binding.rangePrice.setOnRangeBarChangeListener(object : RangeBar.OnRangeBarChangeListener {
            override fun onRangeChangeListener(
                rangeBar: RangeBar,
                leftPinIndex: Int,
                rightPinIndex: Int,
                leftPinValue: String,
                rightPinValue: String
            ) {
            }

            override fun onTouchEnded(rangeBar: RangeBar) {
                Log.e(
                    TAG,
                    "left pin index is ${rangeBar.leftIndex} and right is ${rangeBar.rightIndex}"
                )
                leftValue = rangeBar.leftIndex
                rightValue = rangeBar.rightIndex
                binding.tvPrice.text = "${rangeBar.leftPinValue}~${rangeBar.rightPinValue}만원"
                viewModel.btnActivated.value = true
            }

            override fun onTouchStarted(rangeBar: RangeBar) {
                //
            }
        })

//        textPriceNext.setOnClickListener {
////            startActivity(Intent(this, QuestionActivity::class.java))
//        }
    }

}
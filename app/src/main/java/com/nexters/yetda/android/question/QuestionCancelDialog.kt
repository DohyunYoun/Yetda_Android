package com.nexters.yetda.android.question

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nexters.yetda.android.R
import kotlinx.android.synthetic.main.dialog_question_cancel.view.*

class QuestionCancelDialog : DialogFragment() {

    lateinit var callback: (Boolean) -> Unit

    companion object {
        fun getInstance(callback: (Boolean) -> Unit): QuestionCancelDialog {
            val dialog = QuestionCancelDialog()
            dialog.callback = callback
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_question_cancel, container)
        view.textQuestionCancelProceed.setOnClickListener {
            callback.invoke(true)
            dismissAllowingStateLoss()
        }
        view.textQuestionCancelCancel.setOnClickListener {
            callback.invoke(false)
            dismissAllowingStateLoss()
        }

        return view
    }
}
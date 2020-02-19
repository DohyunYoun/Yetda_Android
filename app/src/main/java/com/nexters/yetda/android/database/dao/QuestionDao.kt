package com.nexters.yetda.android.database.dao

import androidx.lifecycle.LiveData
import com.nexters.yetda.android.database.RealmUtil
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.database.model.Present
import com.nexters.yetda.android.database.model.Question
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlin.random.Random


class QuestionDao(private val mRealm: Realm) {

    fun findAllQuestions(): LiveData<RealmResults<Question>> {
        return RealmUtil.asLiveData(
            mRealm.where<Question>(Question::class.java)
                .findAllAsync()
        )
    }

    fun findQuestionById(id: Int): Question? {
        return mRealm.where<Question>()
            .equalTo("id", id)
            .findFirst()

    }

    fun findQuestion(): Question? {
        return mRealm.where<Question>()
            .findFirst()
    }

    fun findQuestion(tags: Array<String>): Question? {
        val results: RealmResults<Question> =
            mRealm.where<Question>()
                .equalTo("isAsked", false)
                .not()
                .beginGroup()
                .`in`("tags", tags)
                .endGroup()
                .findAll()

        //Randomly pick one
        val r = Random(System.nanoTime())
        val id = r.nextInt(results.size)
        mRealm.executeTransaction {
            results[id]?.isAsked = true
        }
        return results[id]
    }

    fun deleteAll() {
        val result = mRealm.where<Question>().findAll()
        mRealm.executeTransaction {
            result.deleteAllFromRealm()
        }
    }

    fun addQuestion(
        _id: Int,
        _question: String,
        _tag: String
    ) {
        mRealm.executeTransaction {

            val item = mRealm.createObject<Question>(_id)
            item.question = _question
            item.tag = _tag
            it.copyToRealm(item)
        }
    }
}
package com.sudhanshu.aiquiz.feature_quiz.presentation.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class Temp {

    fun doSomeWork(workDone: WorkDone){
        //do your work here
        val result = "resulting value"
        workDone.result(result)
    }

    fun doSomeOtherWork(workDone: WorkDone2){
        val result = "value2"
        workDone.result2(result)
    }

    interface WorkDone{
        fun result(value: String)
    }

    interface WorkDone2{
        fun result2(value: String)
    }
}

class Temp2{
    val t = Temp()
    fun startWork(){
        t.doSomeWork(object : Temp.WorkDone, Temp.WorkDone2{
            override fun result(value: String) {
                println("value = $value")
            }

            override fun result2(value: String) {

            }
        })
    }
}


class ServiceClass(
    private val context: Context,
    private val params: WorkerParameters
): CoroutineWorker(context,params){
    override suspend fun doWork(): Result {
        //do your work here
        return Result.success()
    }

    companion object{
        const val delay = 2000
    }
}
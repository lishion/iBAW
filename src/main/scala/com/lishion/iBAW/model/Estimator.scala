package com.lishion.iBAW.model

import com.lishion.iBAW.data.{Records, SampleTraceRecords}
import main.scala.com.lishion.iBAW.model.Utils.Record


class Estimator(records: SampleTraceRecords){

    def estimate(record: Record): Double ={
        var sumLength = 0d
        val isolator = new Isolator(record,records)
        sumLength += isolator.isolate()
        sumLength
    }
}

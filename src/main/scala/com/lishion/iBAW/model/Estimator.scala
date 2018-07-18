package com.lishion.iBAW.model

import com.lishion.iBAW.data.{Record, Records}


class Estimator(records: Records){

    def estimate(record: Record): Double ={
        var sumLength = 0d
        val isolator = new Isolator(record,records)
        sumLength += isolator.isolate()
        sumLength
    }
}

package com.lishion.iBAW.data

import scala.collection.mutable.ListBuffer
import scala.util.Random

class Records(records: Seq[Record]){

    def sample(num:Int):Records = {

        if(num > this.length){
            throw new IllegalArgumentException(s"sample num ${num} is great than records size ${records.length}")
        }
        val sampledIndexes = ListBuffer(Random.shuffle(0 until length toList).take(num):_*)

        val subRecords = records.filter(record=> sampledIndexes.contains(record.index))

        new Records(subRecords)
    }

    def sampleAll: Records = this.sample(records.length)

    val length: Int = records.length

    def apply(i:Int) = records(i)

    def iterator = records.iterator

    def get(): ListBuffer[Record] = ListBuffer(records:_*).clone()
}



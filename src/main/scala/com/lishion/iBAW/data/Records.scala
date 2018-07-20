package com.lishion.iBAW.data

import main.scala.com.lishion.iBAW.model.Utils
import main.scala.com.lishion.iBAW.model.Utils.Record

import scala.collection.mutable



trait Records{

    def randomTake(num:Int):Records

    def apply(i:Int):Record

    def getItemByIndex(i:Int):String

    def copy():mutable.Buffer[Record]

    def get():mutable.Buffer[Record]

    def length:Int

    def iterator: Iterator[Record]

}


class SimpleRecords(records: mutable.Buffer[Record], itemMap:mutable.Map[Int,String]) extends Records {

    override def randomTake(num:Int):Records = new SimpleRecords(Utils.randomTake(records,num).toBuffer,itemMap)
    override def apply(i:Int) = records(i)
    override def iterator: Iterator[Record] = records.iterator
    override def getItemByIndex(i:Int) = itemMap(i)
    override def get():mutable.Buffer[Record] = records
    override def copy():mutable.Buffer[Record] =  records.toBuffer // toBuffer 会执行一次拷贝
    override def length: Int = records.length
             def getItemMap: mutable.Map[Int, String] = itemMap

}

class SampleTraceRecords(records: SimpleRecords,numToSampleTrace:Int) extends Records {

    // 随机选择 record 并随机对每一个 trace 采样
    override def randomTake(num: Int): SampleTraceRecords = {

        val sampleTraceRecords = mutable.ListBuffer[Record]()
        for(record <- records.randomTake(num).iterator){
            sampleTraceRecords += maybeSample(record)
        }
        new SampleTraceRecords(new SimpleRecords(sampleTraceRecords,records.getItemMap),numToSampleTrace)

    }

    // 对record进行采样
    def maybeSample(record: Record):Record={
        if(record.length > numToSampleTrace){
            Utils.randomTake(record,numToSampleTrace)
        }else{
            record
        }
    }

    override def apply(i: Int): Record = records.apply(i)

    override def getItemByIndex(i: Int): String = records.getItemByIndex(i)

    override def copy(): mutable.Buffer[Record] = records.copy()

    override def length: Int = records.length

    override def iterator: Iterator[Record] = records.iterator

    override def get(): mutable.Buffer[Record] = records.get()
}

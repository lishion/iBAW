package com.lishion.iBAW.model

import java.lang.ThreadLocal

import com.lishion.iBAW.data.{Record, Records}

import scala.collection.mutable.ListBuffer
import scala.util.Random

// 将 record 从 records 中隔离
// records 训练集合
// record 测试样本
class Isolator(record: Record,records: Records){

    private val remainderRecords = new ThreadLocal[ListBuffer[Record]]()
    remainderRecords.set(records.get())

    private val path = new ThreadLocal[Int]()
    path.set(0)

    private val maxNumOfIterations: Int = {
        val maxTrace = math.floor(math.log10(records.length)/0.301).toInt
        math.min(record.iterator.toSeq.distinct.length,maxTrace)
    }

    def isolate():Double = {
        val records = remainderRecords.get()
        val traces = ListBuffer(record.iterator.toSeq:_*)
        while (this.keep){

            val trace = randomChooseAndRemove(traces)
            records.foreach(record=>{
                if(!record.containsTrace(trace)){
                    records -= record
                    remainderRecords.set(records)
                }
            })

            path.set(path.get()+1)
        }
        val compensate = if(records.nonEmpty) math.log(records.length) + 0.57721566 else 0
        path.get() + compensate
    }

    // 注意,traces会在执行后改变
    private def randomChooseAndRemove(traces: ListBuffer[String]): String ={

        val index = Random.nextInt(traces.length)
        val trace = traces(index)
        while (traces.contains(trace)){
            traces -= trace
        }
        trace
    }

    //停止条件
    private def keep:Boolean = {
        val records = remainderRecords.get()
        def cond1 = records.isEmpty //如果所有的训练样本本全部移除
        def cond2 = records.iterator.toSeq.forall(r => r.containsAll(record) ) //如果训练集合中的样本包含了测试样本的所有trace
        def cond3 = path.get() >= maxNumOfIterations
        !(cond3 || cond1 || cond2)
    }
}

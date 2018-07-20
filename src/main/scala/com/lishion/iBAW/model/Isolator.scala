package com.lishion.iBAW.model

import com.lishion.iBAW.data.{Records, SampleTraceRecords}
import main.scala.com.lishion.iBAW.model.Utils
import main.scala.com.lishion.iBAW.model.Utils.Record

import scala.collection.mutable
import scala.util.Random

// 将 record 从 records 中隔离
// records 训练集合
// record 测试样本
class Isolator(record: Record,records: SampleTraceRecords){

    private val remainderRecords = new ThreadLocal[mutable.Buffer[Record]]()
    remainderRecords.set(records.copy())

    private val path = new ThreadLocal[Int]()
    path.set(0)

    def isolate():Double = {

        val localRecords = remainderRecords.get()
        val traces = {

            val clonedTraces = records.maybeSample(record)
            mutable.Buffer(clonedTraces.distinct:_*)
        }
        while (this.keep && traces.nonEmpty){
            val trace = randomChooseAndRemove(traces)
            remainderRecords.set(localRecords.filter(r => !r.contains(trace)))
            path.set(path.get()+1)
        }
        path.get()
    }

    // 注意,traces会在执行后改变
    private def randomChooseAndRemove(traces: mutable.Buffer[String]): String ={

        val index = Random.nextInt(traces.length)
        val trace = traces(index)
        traces -= trace
        trace
    }

    //停止条件
    private def keep:Boolean = {
        val records = remainderRecords.get()
        def cond1 = records.isEmpty //如果所有的训练样本本全部移除
        def cond2 = records.iterator.toSeq.forall( r => Utils.isSubSet(record,r) ) //如果训练集合中的样本包含了测试样本的所有trace
        !(cond1 || cond2)
    }
}


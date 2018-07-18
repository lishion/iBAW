package com.lishion.iBAW.data

import main.scala.com.lishion.iBAW.model.Utils
import main.scala.com.lishion.iBAW.model.Utils.Record

import scala.collection.mutable

class Records(records: Seq[Record]){

    def randomTake(num:Int):Records = new Records(Utils.randomTake(records,num))

    def apply(i:Int) = records(i)
    def iterator = records.iterator

    def get():mutable.Buffer[Record] =  records.clone().asInstanceOf[Seq[Record]].toBuffer
}



package com.lishion.iBAW.data

import scala.util.Random

class Record(_item:String,traces:Seq[String],_index:Int) {
    val item = _item
    val index = _index
    def length = traces.length
    def iterator = traces.iterator

    def containsTrace(trace:String): Boolean = traces.contains(trace)

    // 判断是否包含record的所有trace
    def containsAll(record: Record):Boolean = {
        if(record.index == this.index){
            return true
        }
        if(record.length >= this.length){ //如果当前trace数量少于比较的trace的数量 则不可能完全包含
            return false
        }
        for(trace <- record.iterator){
            if(!this.containsTrace(trace)){
                return false
            }
        }
        true
    }

}

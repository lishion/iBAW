package main.scala.com.lishion.iBAW.model

import scala.collection.generic.CanBuildFrom
import scala.collection.mutable
import scala.language.higherKinds
import scala.util.Random

/**
  * Created by Lishion on 2018/7/18.
  */
object Utils {
    type Record = mutable.Buffer[String]

    // 从集合中随机选择num个元素
    def randomTake[T, CC[X] <: TraversableOnce[X]](xs: CC[T],numToTake:Int)(implicit bf: CanBuildFrom[CC[T], T, CC[T]]):CC[T]={
        if(numToTake > xs.size){
            throw new IllegalArgumentException(s"take num $numToTake great than sequence size ${xs.size}")
        }
        Random.shuffle(xs).toSeq.take(numToTake).asInstanceOf[CC[T]]
    }

    def isSubSet[T](buffer1: mutable.Buffer[T],buffer2: mutable.Buffer[T]):Boolean={
        for(item <- buffer1){
            if(!buffer2.contains(item)){
                return false
            }
        }
        true
    }

}

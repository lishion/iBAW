package com.lishion.iBAW.data
import java.io.FileWriter

import com.alibaba.fastjson._
import main.scala.com.lishion.iBAW.model.Utils.Record

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.io.Source

trait DataLoader {
    def load():Records
}

class JsonDataLoader(path:String) extends DataLoader{

    override def load(): SimpleRecords = {
        val records = new ListBuffer[Record]
        val file = Source.fromFile(path)
        var index = 0
        var itemMap = mutable.Map[Int,String]()
        for(line <- file.getLines()){
            try {
                val jsObj =  JSON.parseObject(line)

                records += loadJSArrayToBuffer(jsObj,"url_and_time")
                itemMap += (index -> jsObj.getString("ip"))

                index += 1
            }catch {
                case e:Exception=>{
                    println("load data error!!",line)
                    e.printStackTrace()
                }
            }
        }

        new SimpleRecords(records,itemMap)
    }

    private def loadJSArrayToBuffer(jsObj:JSONObject,key:String):mutable.Buffer[String]={
        val jsArray =  jsObj.getJSONArray(key)
        val urls:Array[String] = new Array[String](jsArray.size())
        jsArray.toArray(urls)
        ListBuffer(urls:_*)
    }
}



class  TextDataLoader(path:String) extends DataLoader {
    override def load(): Records = {
        val file = Source.fromFile(path)
        val index = 0
        var records = new ListBuffer[Record]
        var itemMap = mutable.Map[Int,String]()
        for(line <- file.getLines()){
            val items = line.split("    ")
            val ip = items(0)
            val urls = items(1).split(",").toBuffer
            itemMap += (index -> ip)
            records += urls
        }
        new SimpleRecords(records,itemMap)
    }
}





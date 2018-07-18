package com.lishion.iBAW.data
import com.alibaba.fastjson._

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.io.Source

trait DataLoader {
    def load():Records
}

class JsonDataLoader(path:String) extends DataLoader{

    override def load(): Records = {
        val records = new ListBuffer[Record]
        val file = Source.fromFile(path)
        var index = 0
        for(line <- file.getLines()){
            try {
                val jsObj =  JSON.parseObject(line)

                val ip = jsObj.getString("ip")

                val jsArray =  jsObj.getJSONArray("url_and_time")
                val urls:Array[String] = new Array[String](jsArray.size())
                jsArray.toArray(urls)

                records += new Record(ip,urls,index)
                index += 1
            }catch {
                case e:Exception=>{
                    println(line)
                }
            }
        }

        new Records(records)
    }
}

object JsonDataLoader{
    def main(args: Array[String]): Unit = {
        val file = Source.fromFile("urlData.json")
        var index = 0
        var max = 0
        var ipMax:String = ""
        for(line <- file.getLines()){
            val jsObj =  JSON.parseObject(line)

            val ip = jsObj.getString("ip")
            val jsArray =  jsObj.getJSONArray("url_and_time")
            if(jsArray.size() > max){
                max = jsArray.size()
                ipMax = ip
            }
        }
        println(ipMax,max)
    }
}


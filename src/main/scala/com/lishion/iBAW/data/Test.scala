
import java.io.FileWriter

import com.lishion.iBAW.data.{JsonDataLoader, SampleTraceRecords}
import com.lishion.iBAW.model.IBAW

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Test{

    def getNormalIp()={
        val writer = new FileWriter("normalIp.txt")
        val ips = ListBuffer[String]()
        val file = Source.fromFile("result-sorted.txt")
        val pattern = "\\s+".r
        var index = 1
        for(line <- file.getLines){
            val triemd = pattern.replaceAllIn(line," ")
            val ip = triemd.split(" ")(0)
            if(index > 500){
                ips += ip
                writer.write(ip+"\n")
            }

            index += 1
        }
        ips
        writer.flush()
        writer.close()
    }


    def main(args: Array[String]): Unit = {
//        val loader = new JsonDataLoader("heyTestData.json")
//        val r = loader.load()
//        val records = new SampleTraceRecords(r,20)
//
//        val iBAW = new IBAW(records,50,256)
//        val model = iBAW.createModel()
//
//        var writer = new FileWriter("result2.txt")
//        for (i <- 0 until  records.length){
//            println(s"handle the $i-th record,whole num is ${records.length}")
//            val record = records(i)
//            if(record.length>=20){
//                val (length,scores) = model.predict(record)
//                val str = f"${i}%-5s ${records.getItemByIndex(i)}%-20s  ${length}%-10.3f  ${scores}%-10.3f \n"
//                writer.append(str)
//            }
//            writer.flush()
//        }
//
//        writer.close()
//
//        val results = new ListBuffer[Seq[String]]
//        val file = Source.fromFile("result2.txt")
//        val pattern = "\\s+".r
//        for(line <- file.getLines()){
//            val triemd = pattern.replaceAllIn(line," ")
//            val arr = triemd.split(" ").toSeq
//            results += arr
//        }
//        val sorted = results.sortWith{case (a1,a2)=>{a1(3)>a2(3)}}
//
//        writer = new FileWriter("result-sorted2.txt")
//        for(item<-sorted){
//            val index = item(0)
//            val ip = item(1)
//            val length = item(2).toDouble
//            val scores = item(3).toDouble
//
//            val str = f"${index}%-5s  ${ip}%-15s  ${length.toDouble}%-10.3f  ${scores.toDouble}%-10.3f \n"
//            writer.write(str)
//        }
//
//        writer.flush()
//        writer.close()
//
//        val top10 = sorted.take(10)
//        val end10 = sorted.takeRight(10)
//
//        writer = new FileWriter("top10-result.txt")
//
//        for(r <- top10){
//            val record = records(r.head.toInt)
//
//            writer.write(record.item + "\n")
//            writer.write("\n")
//            record.iterator.foreach(r => writer.write("\t"+r + "\n"))
//            writer.write("\n")
//        }
//        writer.flush()
//        writer.close()
//        println(getNormalIp())
//        Seq()
        val loader = new JsonDataLoader("urlData.json")
        val r = loader.load()
        val records = new SampleTraceRecords(r,20)
        val iBAW = new IBAW(records,50,256)
        val model = iBAW.createModel()
        println(model.predict())


    }

    def echo(args:String*) = {
        println()
    }

}


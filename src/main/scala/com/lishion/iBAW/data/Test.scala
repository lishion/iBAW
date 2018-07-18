
import java.io.FileWriter

import com.lishion.iBAW.data.JsonDataLoader
import com.lishion.iBAW.model.IBAW

import scala.collection.mutable.ListBuffer
import scala.io.Source

object Test{
    def main(args: Array[String]): Unit = {
        val loader = new JsonDataLoader("testData.json")
        val records = loader.load()
        val iBAW = new IBAW(records,50,256)
        val model = iBAW.createModel()

//        val writer = new FileWriter("result.txt")
//        for (i <- 0 until  records.length){
//            println(s"handle the $i-th record,whole num is ${records.length}")
//            val record = records(i)
//            if(record.length>=20){
//                val (length,scores) = model.predict(record)
//                val str = f"${record.index}%-5s ${record.item}%-20s  ${length}%-10.3f  ${scores}%-10.3f \n"
//                writer.append(str)
//            }
//            writer.flush()
//        }
//
//        writer.close()

//        val record = records(1102)
//        println(record.item)
//        println(model.predict(record))

        val results = new ListBuffer[Seq[String]]
        val file = Source.fromFile("result.txt")
        val pattern = "\\s+".r
        for(line <- file.getLines()){
            val triemd = pattern.replaceAllIn(line," ")
            val arr = triemd.split(" ").toSeq
            results += arr
        }
        val sorted = results.sortWith{case (a1,a2)=>{a1(3)>a2(3)}}

        var writer = new FileWriter("result-sorted.txt")
        for(item<-sorted){

            val ip = item(1)
            val length = item(2).toDouble
            val scores = item(3).toDouble

            val str = f"${ip}%-15s  ${length.toDouble}%-10.3f  ${scores.toDouble}%-10.3f \n"
            writer.write(str)
        }

        writer.flush()
        writer.close()

        val top10 = sorted.take(10)
        val end10 = sorted.takeRight(10)

        writer = new FileWriter("top10-result.txt")

        for(r <- top10){
            val record = records(r.head.toInt)

            writer.write(record.item + "\n")
            writer.write("\n")
            record.iterator.foreach(r => writer.write("\t"+r + "\n"))
            writer.write("\n")
        }
        writer.flush()
        writer.close()
    }
}


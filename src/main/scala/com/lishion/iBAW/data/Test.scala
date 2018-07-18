
import com.lishion.iBAW.data.JsonDataLoader
import com.lishion.iBAW.model.{IBAW, Isolator, PredictModel}

object Test{
    def main(args: Array[String]): Unit = {
        val loader = new JsonDataLoader("testData.json")
        val records = loader.load()
        val iBAW = new IBAW(records,100,9)
        val model = iBAW.createModel()
        for(i <- 0 until 9 ){

            val record = records(i)
            if(record.length>=0){
                println(record.item,model.predict(record))
            }

        }

    }


}


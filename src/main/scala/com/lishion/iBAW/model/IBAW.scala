package com.lishion.iBAW.model

import com.lishion.iBAW.data.{Records, SampleTraceRecords}

import scala.collection.mutable.ListBuffer

class IBAW(records:SampleTraceRecords, numOfEstimate:Int = 10, numOfSample:Int = 256) {

    def createModel():PredictModel={
        val estimators = new ListBuffer[Estimator]
        for(i <- 0 until numOfEstimate){
            estimators += new Estimator(records.randomTake(numOfSample))
        }

        new SingleThreadPredict(estimators,numOfSample)
    }
}







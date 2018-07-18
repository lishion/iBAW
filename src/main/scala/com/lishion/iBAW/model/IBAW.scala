package com.lishion.iBAW.model

import com.lishion.iBAW.data.{Record, Records}

import scala.collection.mutable.ListBuffer

class IBAW(records:Records,numOfEstimate:Int = 10,numOfSample:Int = 256) {

    def createModel():PredictModel={
        val estimators = new ListBuffer[Estimator]
        for(i <- 0 until numOfEstimate){
            estimators += new Estimator(records.sample(numOfSample))
        }
        new SingleThreadPredict(estimators,numOfSample)
    }
}







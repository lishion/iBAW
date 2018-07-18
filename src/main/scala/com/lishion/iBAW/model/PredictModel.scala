package com.lishion.iBAW.model

trait PredictModel {
    protected def abnormalScores(length:Double):Double
    def predict(record: Record):(Double,Double)
}

abstract class AbstractPredictModel(estimators: Seq[Estimator],traceLength:Int) extends PredictModel{
    //根据迭代的次数获得异常得分，具体请参考:
    //iBAT: Detecting Anomalous Taxi Trajectories from GPS Traces
    override protected def abnormalScores(length: Double): Double = {
        val N = traceLength
        def H = (i:Int) => math.log(i) + 0.57721566
        val c = 2 * H(N-1) - 2*(N-1)/N
        math.pow(2,-length/c)
    }
}

class SingleThreadPredict(estimators: Seq[Estimator],traceLength:Int) extends AbstractPredictModel(estimators,traceLength){
    override def predict(record: Record): (Double,Double) = {
        var sumLength = 0d
        for(estimator <- estimators){
            sumLength += estimator.estimate(record)
        }
        val averageLength = sumLength / estimators.length
        (averageLength,this.abnormalScores(averageLength))
    }
}



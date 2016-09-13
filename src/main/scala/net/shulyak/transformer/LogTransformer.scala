package net.shulyak.transformer

import math.log1p

import org.apache.spark.ml.{UnaryTransformer}
import org.apache.spark.ml.Transformer
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.util.Identifiable

import org.apache.spark.sql.types.{DataType, DoubleType}


class LogTransformer(override val uid: String)
    extends UnaryTransformer[Int, Double, LogTransformer] {

  def this() = this(Identifiable.randomUID("logtransformer"))

  /**
  * Transforms Ints to log1p'd vars
  */
  override protected def createTransformFunc: Int => Double = {
    (i: Int) => { log1p(i.asInstanceOf[Double]) }
  }

  override protected def outputDataType: DataType = {
     DoubleType
  }
}

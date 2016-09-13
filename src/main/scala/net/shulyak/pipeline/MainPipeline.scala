package net.shulyak.pipeline


import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.{VectorIndexer, VectorAssembler}
import org.apache.spark.ml.regression.{GBTRegressionModel, GBTRegressor}

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame

import net.shulyak.transformer.LogTransformer
import net.shulyak.utils.DataSchema



object MainPipeline {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder
      .appName("BimboPipeline")
      .getOrCreate()


    // Load CSV with on `Demand` column renamed, so it's easier to use it later on
    val data = spark.read.format("csv")
                         .schema(DataSchema.Input.train)
                         .option("header", true)
                         .load("data/input/train_small.csv")
                         .withColumnRenamed("Demanda_uni_equil", "target")

    // MLLib requires features to be assembled in one vector. `target` feature is excluded, of course.
    val assemble = new VectorAssembler()
      .setInputCols(data.columns.filter(_ != "target"))
      .setOutputCol("features")

    // Automatically identify categorical features, and index them.
    // Set maxCategories so features with > 4 distinct values are treated as continuous.
    val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(4)

    // We need to use RMLSE as error measure, so `target` should be log1p'd.
    val logTransformer = new LogTransformer()
      .setInputCol("target")
      .setOutputCol("targetlog")

    // Temp demo solution, need to use weeks data
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

    val gbt = new GBTRegressor()
      .setLabelCol("targetlog")
      .setFeaturesCol("indexedFeatures")
      .setMaxIter(10) // max iters is low for testing

    // Final pipeline
    val pipeline = new Pipeline()
      .setStages(Array(logTransformer, assemble, featureIndexer, gbt))

    val model = pipeline.fit(trainingData)
    val predictions = model.transform(testData)

    // Example predictions
    predictions.select("prediction", "targetlog", "features").show(5)

    // RMSLE computation
    val evaluator = new RegressionEvaluator()
      .setLabelCol("target")
      .setPredictionCol("prediction")
      .setMetricName("rmse")
    val rmsle = evaluator.evaluate(predictions)
    println("RMSLE: " + rmsle)

    spark.stop()
  }
}

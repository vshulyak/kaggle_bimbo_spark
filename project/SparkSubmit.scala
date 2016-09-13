import sbtsparksubmit.SparkSubmitPlugin.autoImport._

// Here's an idea how to make it better https://github.com/Aluxian/Tweeather/blob/master/project/SparkSubmit.scala
object SparkSubmit {

  lazy val settings = SparkSubmitSetting(
    SparkSubmitSetting("slocal",
      Seq("--class", "net.shulyak.pipeline.MainPipeline",
          "--master", "local[*]")
    ),
    SparkSubmitSetting("scluster",
      Seq("--class", "net.shulyak.pipeline.MainPipeline",
          "--master", "spark://localhost:7077")
    )
  )

}

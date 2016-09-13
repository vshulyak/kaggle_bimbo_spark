package net.shulyak.utils

import org.apache.spark.sql.types._


object DataSchema {

  object Input {

    // training data schema
    val train = StructType(Seq(
      StructField("Semana", ShortType, true),
      StructField("Agencia_ID", ShortType, true),
      StructField("Canal_ID", ShortType, true),
      StructField("Ruta_SAK", IntegerType, true),
      StructField("Cliente_ID", IntegerType, true),
      StructField("Producto_ID", IntegerType, true),
      StructField("Demanda_uni_equil", IntegerType, true)
    ))
  }
}

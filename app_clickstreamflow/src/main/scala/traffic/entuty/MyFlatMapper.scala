package traffic.entuty


import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.util.Collector

/**
 *
 * Author: dougonghou
 * Date: 2022/6/13 15:09
 */
class MyFlatMapper extends FlatMapFunction[String, (String, Int)] {
  @throws[Exception]
  def flatMap(t: String, collector: Collector[(String, Int)]): Unit = {
    val collect = t.split(" ").map {
      x: String => {
        collector.collect((x, 1))
      }
    }
  }
}

package traffic

import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.streaming.api.datastream.{DataStream, DataStreamSource, SingleOutputStreamOperator}
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.util.Collector
import traffic.entuty.MyFlatMapper


/**
 *
 * Author: dougonghou
 * Date: 2022/6/13 14:15
 */
object ClickstreamMain {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val line: DataStreamSource[String] = env.socketTextStream("192.168.122.8", 7777)
    //    val dflatmap: SingleOutputStreamOperator[(String, Int)] = line.flatMap(new MyFlatMapper())
    val flatmap = line.flatMap(
      new FlatMapFunction[String, (String, Int)]() {
        override def flatMap(t: String, collector: Collector[(String, Int)]): Unit = {
          val collect = t.split(" ").map {
            x: String => {
              collector.collect((x, 1))
            }
          }
        }
      })
    flatmap.print()
    env.execute()
  }

}



// THIS IS AN AUTO-GENERATED FILE. DO NOT MODIFY.    
// generated by GenCounter on Tue Jan 27 09:58:59 PST 2009
package scalanlp.counters.shorts;
/*
 Copyright 2009 David Hall, Daniel Ramage
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at 
 
 http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License. 
*/
import scala.collection.mutable.Map;
import scala.collection.mutable.HashMap;

/**
 * Count objects of type Short with type Long.
 * This trait is a wrapper around Scala's Map trait
 * and can work with any scala Map. 
 *
 * @author dlwh
 */
@serializable 
trait Short2LongCounter extends LongCounter[Short] {


  abstract override def update(k : Short, v : Long) = {

    super.update(k,v);
  }

  // this isn't necessary, except that the jcl MapWrapper overrides put to call Java's put directly.
  override def put(k : Short, v : Long) :Option[Long] = { val old = get(k); update(k,v); old}

  abstract override def -=(key : Short) = {

    super.-=(key);
  }

  /**
   * Increments the count by the given parameter.
   */
   override  def incrementCount(t : Short, v : Long) = {
     update(t,(this(t) + v).asInstanceOf[Long]);
   }


  override def ++=(kv: Iterable[(Short,Long)]) = kv.foreach(+=);

  /**
   * Increments the count associated with Short by Long.
   * Note that this is different from the default Map behavior.
  */
  override def +=(kv: (Short,Long)) = incrementCount(kv._1,kv._2);

  override def default(k : Short) : Long = 0;

  override def apply(k : Short) : Long = super.apply(k);

  // TODO: clone doesn't seem to work. I think this is a JCL bug.
  override def clone(): Short2LongCounter  = super.clone().asInstanceOf[Short2LongCounter]

  /**
   * Return the Short with the largest count
   */
  override  def argmax() : Short = (elements reduceLeft ((p1:(Short,Long),p2:(Short,Long)) => if (p1._2 > p2._2) p1 else p2))._1

  /**
   * Return the Short with the smallest count
   */
  override  def argmin() : Short = (elements reduceLeft ((p1:(Short,Long),p2:(Short,Long)) => if (p1._2 < p2._2) p1 else p2))._1

  /**
   * Return the largest count
   */
  override  def max : Long = values reduceLeft ((p1:Long,p2:Long) => if (p1 > p2) p1 else p2)
  /**
   * Return the smallest count
   */
  override  def min : Long = values reduceLeft ((p1:Long,p2:Long) => if (p1 < p2) p1 else p2)

  // TODO: decide is this is the interface we want?
  /**
   * compares two objects by their counts
   */ 
  override  def comparator(a : Short, b :Short) = apply(a) compare apply(b);

  /**
   * Return a new Short2DoubleCounter with each Long divided by the total;
   */
  override  def normalized() : Short2DoubleCounter = {
    val normalized = Short2DoubleCounter();
    val total : Double = this.total
    if(total != 0.0)
      for (pair <- elements) {
        normalized(pair._1) = pair._2 / total;
      }
    normalized
  }

  /**
   * Return the sum of the squares of the values
   */
  override  def l2norm() : Double = {
    var norm = 0.0
    for (val v <- values) {
      norm += (v * v)
    }
    return Math.sqrt(norm)
  }

  /**
   * Return a List the top k elements, along with their counts
   */
  override  def topK(k : Int) = Counters.topK[(Short,Long)](k,(x,y) => if(x._2 < y._2) -1 else if (x._2 == y._2) 0 else 1)(this);

  /**
   * Return \sum_(t) C1(t) * C2(t). 
   */
  def dot(that : Short2LongCounter) : Double = {
    var total = 0.0
    for (val (k,v) <- that.elements) {
      total += get(k).asInstanceOf[Double] * v
    }
    return total
  }

  def +=(that : Short2LongCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) + v).asInstanceOf[Long]);
    }
  }

  def -=(that : Short2LongCounter) {
    for(val (k,v) <- that.elements) {
      update(k,(this(k) - v).asInstanceOf[Long]);
    }
  }

  override  def *=(scale : Long) {
    transform { (k,v) => (v * scale).asInstanceOf[Long]}
  }

  override  def /=(scale : Long) {
    transform { (k,v) => (v / scale).asInstanceOf[Long]}
  }
}


object Short2LongCounter {
  import it.unimi.dsi.fastutil.objects._
  import it.unimi.dsi.fastutil.ints._
  import it.unimi.dsi.fastutil.shorts._
  import it.unimi.dsi.fastutil.longs._
  import it.unimi.dsi.fastutil.floats._
  import it.unimi.dsi.fastutil.doubles._


  import scala.collection.jcl.MapWrapper;
  @serializable
  @SerialVersionUID(1L)
  class FastMapCounter extends MapWrapper[Short,Long] with Short2LongCounter {
    private val under = new Short2LongOpenHashMap;
    def underlying() = under.asInstanceOf[java.util.Map[Short,Long]];
    override def apply(x : Short) = under.get(x);
    override def update(x : Short, v : Long) {
      val oldV = this(x);
      updateTotal(v-oldV);
      under.put(x,v);
    }
  }

  def apply() = new FastMapCounter();

  
}


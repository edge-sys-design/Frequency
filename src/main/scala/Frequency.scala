/** Copyright (C) 2013 Edge System Design, LLC.  All rights reserved.
  *
  * This file is released under a 3-clause BSD license. See the README.md file
  * for more information.
  *
  * Author(s): Ricky Elrod <relrod@edgesysdesign.com>
  */

package com.edgesysdesign.frequency
import java.math.MathContext

/** A representation of a RF frequency.
  *
  * This class represents a frequency, such as (but not limited to) those in the
  * amateur radio bands. It provides methods for working with the frequency and
  * transforming it in various ways -- however it does remain immutable.
  *
  * Internally, the Frequency class '''always''' uses Hertz to perform math such
  * as adding or subtracting. Hertz is always represented by a [[scala.Long]].
  *
  * Megahertz is always represented in a 3-segment [[java.lang.String]] and
  * never contains units.
  *
  * It handles frequencies in the following forms:
  * "146" => 146.000.000
  * "146.52" => 146.520.000
  * "146.520" => 146.520.000
  * "146.520.000" => 146.520.000
  *
  * {{{
  * val f = new Frequency("146.520")
  * f defaultOffset
  * f + 0.300
  * // etc.
  * }}}
  */
class Frequency(val frequency: String) extends Ordered[Frequency] {

  /** Construct a Frequency, given Hertz.
    *
    * @param frequency The frequency in '''hertz'''.
    * @return Frequency
    */
  def this(frequency: BigInt) = this(Frequency.toMHz(frequency))

  override def toString = frequency
  lazy val Hz = Frequency.toHz(this)
  lazy val MHz = Frequency.toMHz(this)

  /** Compares two Frequency objects. */
  def compare(that: Frequency) = this.Hz compare that.Hz

  /** Determines if the value of two Frequency objects are the same. */
  def ==(that: Frequency) = this.Hz == that.Hz

  /** Add two Frequency instances, returning a new Frequency. */
  def +(that: Frequency) = new Frequency(Hz + that.Hz)

  /** Subtract two Frequency instances, returning a new Frequency. */
  def -(that: Frequency) = new Frequency(Hz - that.Hz)

  /** Allow negative-string frequencies. */
  def unary_- = new Frequency(-1 * this.Hz)

  /** Calculate the wavelength of this Frequency instance. */
  def wavelength() = 299792458 / BigDecimal(this.Hz)
}

object Frequency {
  /** Convert a frequency from Hertz to Megahertz.
    *
    * @param frequency The frequency in Hertz.
    * @return The frequency in Megahertz, in three segments, separated with '.'.
    */
  def toMHz(frequency: BigInt): String = {
    val frequencyStringBuilder = new StringBuilder
    val decString = (BigDecimal(frequency, MathContext.UNLIMITED) / 1000000).toString

    // BigDecimal.toString() doesn't add a ".0" if it's not needed, but we
    // depend on it being there. When we split, we add it ourself if we need it.
    // This is a bit of a hack in that we possibly shouldn't be depending on it
    // being there, but for now it works around the issue.
    val split = (if (!decString.contains(".")) (decString + ".0") else decString).split("\\.")

    frequencyStringBuilder.append(split.head)
    frequencyStringBuilder.append(split.last + ("0" * (6 - split.last.length)))
    frequencyStringBuilder.insert(frequencyStringBuilder.length - 3, ".")
    frequencyStringBuilder.insert(frequencyStringBuilder.length - 7, ".")
    frequencyStringBuilder.toString
  }

  def toMHz(frequency: Frequency): String = toMHz(toHz(frequency.frequency))


  /** Convert a frequency from Megahertz to Hertz.
    *
    * @throws IllegalArgumentException if an invalid frequency is given.
    * @param frequency The frequency in Megahertz.
    * @return The frequency in Hertz.
    */
  def toHz(frequency: String): BigInt = {
    val frequencyStringSplit: List[String] = frequency.split("\\.").toList
    val frequencySplit: List[BigInt] = frequencyStringSplit.map(segment =>
      segment match {
        case "" => BigInt(0)
        case x => BigInt(x.toString)
      })

    val sign = if (frequency.startsWith("-")) -1 else 1

    val hertz = frequencySplit.size match {
      case 1 => frequencySplit.head * 1000000
      case 2 => {
        (frequencySplit.head * 1000000) +
        (frequencySplit(1) * 1000 * math.pow(
          10,
          3 - (frequencyStringSplit(1).toString.size)).toLong)
      }
      case 3 => {
        (frequencySplit.head * 1000000) +
        (frequencySplit(1) * 1000 * math.pow(
          10,
          3 - (frequencyStringSplit(1).toString.size)).toLong) +
        (frequencySplit(2) * math.pow(
          10,
          3 - (frequencyStringSplit(2).toString.size)).toLong)
      }
    }

    hertz * sign
  }

  def toHz(frequency: Frequency): BigInt = toHz(frequency.frequency)
}

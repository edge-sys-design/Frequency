/** Copyright (C) 2012 Edge System Design, LLC.  All rights reserved.
  *
  * This file is released under a 3-clause BSD license. See the README.md file
  * for more information.
  *
  * Author(s): Ricky Elrod <relrod@edgesysdesign.com>
  */

package com.edgesysdesign.frequency

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
class Frequency(val frequency: String) {

  /** Construct a Frequency, given Hertz.
    *
    * @param frequency The frequency in '''hertz'''.
    * @return Frequency
    */
  def this(frequency: Long) = this(Frequency.toMHz(frequency))

  override def toString = frequency
  lazy val Hz = Frequency.toHz(this)
  lazy val MHz = Frequency.toMHz(this)

  /** Determine which band the frequency is in.
    *
    * @return A string containing the band and abbreviation (e.g. "20m")
    */
  def band(): Option[String] = {
    Hz match {
      case x if (x >= 1800000L && x <= 2000000L) => Some("160m")
      case x if (x >= 3500000L && x <= 4000000L) => Some("80m")
      case x if (x >= 5100000L && x <= 5500000L) => Some("60m")
      case x if (x >= 7000000L && x <= 7300000L) => Some("40m")
      case x if (x >= 10100000L && x <= 10150000L) => Some("30m")
      case x if (x >= 14000000L && x <= 14350000L) => Some("20m")
      case x if (x >= 18068000L && x <= 18168000L) => Some("17m")
      case x if (x >= 21000000L && x <= 21450000L) => Some("15m")
      case x if (x >= 24890000L && x <= 24990000L) => Some("12m")
      case x if (x >= 28000000L && x <= 29700000L) => Some("10m")
      case x if (x >= 50000000L && x <= 54000000L) => Some("6m")
      case x if (x >= 144000000L && x <= 148000000L) => Some("2m")
      case x if (x >= 420000000L && x <= 480000000L) => Some("70cm")
      case x if (x >= 902000000L && x <= 928000000L) => Some("33cm")
      case x if (x >= 1240000000L && x <= 1300000000L) => Some("23cm")
      case x if (x >= 2390000000L && x <= 2450000000L) => Some("13cm")
      case x if (x >= 3300000000L && x <= 3500000000L) => Some("9m")
      case x if (x >= 5650000000L && x <= 5925000000L) => Some("5cm")
      case x if (x >= 10000000000L && x <= 10500000000L) => Some("3cm")
      case x if (x >= 24000000000L && x <= 24250000000L) => Some("1.2cm")
      case x if (x >= 47000000000L && x <= 47200000000L) => Some("6mm")
      case x if (x >= 75500000000L && x <= 81000000000L) => Some("4mm")
      case x if (x >= 119980000000L && x <= 120020000000L) => Some("2.5mm")
      case x if (x >= 142000000000L && x <= 149000000000L) => Some("2mm")
      case x if (x >= 241000000000L && x <= 250000000000L) => Some("1mm")
      case _ => None
    }
  }
}

object Frequency {
  /** Convert a frequency from Hertz to Megahertz.
    *
    * @param frequency The frequency in Kilohertz.
    * @return The frequency in Megahertz, in three segments, separated with '.'.
    */
  def toMHz(frequency: Long): String = {
    val frequencyStringBuilder = new StringBuilder
    val split = (frequency.toDouble / 1000000).toString split("\\.")
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
  def toHz(frequency: String): Long = {
    val frequencySplit: List[Long] = frequency.split("\\.").toList.map(segment =>
      segment match {
        case "" => 0
        case x => {
          if (x.size > 3) {
            throw new IllegalArgumentException(
              "Sections of the frequency should be no longer than 3 numbers.")
          }
          if (!x.forall(_.isDigit)) {
            throw new IllegalArgumentException(
              "The frequency should only contain numbers and decimals.")
          }
          x.toLong
        }
      })

    frequencySplit.size match {
      case 1 => frequencySplit.head * 1000000
      case 2 => {
        (frequencySplit.head * 1000000) +
        (frequencySplit(1) * 1000 * math.pow(
          10,
          3 - (frequencySplit(1).toString.size)).toLong)
      }
      case 3 => {
        (frequencySplit.head * 1000000) +
        (frequencySplit(1) * 1000 * math.pow(
          10,
          3 - (frequencySplit(1).toString.size)).toLong) +
        (frequencySplit(2) * math.pow(
          10,
          3 - (frequencySplit(2).toString.size)).toLong)
      }
    }
  }

  def toHz(frequency: Frequency): Long = toHz(frequency.frequency)
}

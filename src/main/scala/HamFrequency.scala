/** Copyright (C) 2013 Edge System Design, LLC.  All rights reserved.
  *
  * This file is released under a 3-clause BSD license. See the README.md file
  * for more information.
  *
  * Author(s): Ricky Elrod <relrod@edgesysdesign.com>
  */

package com.edgesysdesign.frequency

/** A representation of a Ham Radio related frequency.
  *
  * This class represents a frequency, specific to the ham radio bands.
  *
  * It extends the [[Frequency]] class and adds methods specific to ham radio
  * operations, such as determining whether or not someone in a given country can
  * transmit on a given frequency, handling common repeater offsets (again,
  * respective to countries), and returning local, human readable wavelengths
  * such as "2 meters" for determining which band a frequency belongs to.
  *
  * {{{
  * val f = HamFrequency(new Frequency("146.520"))
  * }}}
  */
class HamFrequency(frequency: String) extends Frequency(frequency) {

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

object HamFrequency {
  def apply(f: Frequency) = new HamFrequency(f.toString)
}

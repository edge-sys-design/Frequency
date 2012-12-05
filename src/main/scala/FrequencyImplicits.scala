/** Copyright (C) 2012 Edge System Design, LLC.  All rights reserved.
  *
  * This file is released under a 3-clause BSD license. See the README.md file
  * for more information.
  *
  * Author(s): Ricky Elrod <relrod@edgesysdesign.com>
  */

package com.edgesysdesign.frequency
import language.implicitConversions

package object FrequencyImplicits {

  class FrequencyImplicit(frequency: Double) {
    def this(frequency: Long) = this(frequency.toDouble)
    def this(frequency: Int) = this(frequency.toDouble)
    def this(frequency: String) = this(Frequency.toHz(frequency) / 1000000.0)
    def KHz() = new Frequency((frequency * 1000).toLong)
    def MHz() = new Frequency((frequency * 1000000).toLong)
    def Hz() = new Frequency(frequency.toLong)
  }

  implicit def doubleToFrequency(frequency: Double) = new FrequencyImplicit(frequency)
  implicit def intToFrequency(frequency: Int) = new FrequencyImplicit(frequency)
  implicit def LongToFrequency(frequency: Long) = new FrequencyImplicit(frequency)
  implicit def StringToFrequency(frequency: String) = new FrequencyImplicit(frequency)

}

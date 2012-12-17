/** Copyright (C) 2012 Edge System Design, LLC.  All rights reserved.
  *
  * This file is released under a 3-clause BSD license. See the README.md file
  * for more information.
  *
  * Author(s): Ricky Elrod <relrod@edgesysdesign.com>
  */

package com.edgesysdesign.frequency
import language.implicitConversions
import java.math.MathContext

package object FrequencyImplicits {

  class FrequencyImplicit(frequency: BigDecimal) {
    def this(frequency: BigInt) = this(BigDecimal(frequency, MathContext.UNLIMITED))
    def this(frequency: String) = this(
      BigDecimal(Frequency.toHz(frequency), MathContext.UNLIMITED) / 1000000)

    // Keep these in order of magnitude.
    def Hz() = new Frequency(BigInt(frequency.toLong))
    def kHz() = new Frequency(BigInt((frequency * 1E3).toLong))
    def MHz() = new Frequency(BigInt((frequency * 1E6).toLong))
    def GHz() = new Frequency(BigInt((frequency * 1E9).toLong))
    def THz() = new Frequency(BigInt((frequency * 1E12).toLong))
    def PHz() = new Frequency(BigInt((frequency * 1E15).toLong))
  }

  implicit def doubleToFrequency(frequency: Double) = new FrequencyImplicit(frequency)
  implicit def LongToFrequency(frequency: BigInt) = new FrequencyImplicit(frequency)
  implicit def StringToFrequency(frequency: String) = new FrequencyImplicit(frequency)
}

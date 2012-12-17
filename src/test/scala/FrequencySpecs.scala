import com.edgesysdesign.frequency.Frequency
import com.edgesysdesign.frequency.FrequencyImplicits._

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter

class FrequencySpecs extends FunSpec with ShouldMatchers with BeforeAndAfter {
  describe("The Frequency library's implicits") {
    it("should convert Long, String, and Int to Frequency instances") {
      146520.kHz.toString should be === "146.520.000"
      146520000.Hz.toString should be === "146.520.000"
      146.MHz.toString should be === "146.000.000"
      234.Hz.toString should be === "0.000.234"
      "146".MHz.toString should be === "146.000.000"
      "146.52".MHz.toString should be === "146.520.000"
      "146.520.000".MHz.toString should be === "146.520.000"
      "2".GHz.toString should be === "2000.000.000"
      "2".THz.toString should be === "2000000.000.000"
      "2".PHz.toString should be === "2000000000.000.000"
    }
  }

  describe("The Frequency library") {
    it("should convert various forms of frequency strings to Hz") {
      new Frequency("14").Hz should be === 14000000
      new Frequency("14.3").Hz should be === 14300000
      new Frequency("14.300").Hz should be === 14300000
      new Frequency("14.300.00").Hz should be === 14300000
      new Frequency("14.300.000").Hz should be === 14300000
      new Frequency("146.52").Hz should be === 146520000
      (10.kHz).Hz should be === 10000
    }

    it("should throw IllegalArgumentException when given letters in any format") {
      intercept[IllegalArgumentException] { new Frequency("14a.300.000").Hz  }
      intercept[IllegalArgumentException] { new Frequency("14.a").Hz  }
      intercept[IllegalArgumentException] { new Frequency("14.300.a").Hz  }
      intercept[IllegalArgumentException] { new Frequency("14.3b0.00").Hz  }
    }

    it("should convert hertz to megahertz") {
      new Frequency(14325000).frequency should be === "14.325.000"
      Frequency.toMHz(146520000) should be === "146.520.000"
      Frequency.toMHz(1465200) should be === "1.465.200"
      Frequency.toMHz(234) should be === "0.000.234"
      Frequency.toMHz(BigInt("9000200000")) should be === "9000.200.000"
      Frequency.toMHz(BigInt("9000000000")) should be === "9000.000.000"
    }

    it("should add frequencies together") {
      ((146.520.MHz) + (10.kHz)).frequency should be === "146.530.000"
      ((1.MHz) + (10.kHz)).frequency should be === "1.010.000"
    }

    it("should subtract frequencies from each other") {
      ((146.520.MHz) - (10.kHz)).frequency should be === "146.510.000"
      ((1.MHz) - (10.kHz)).frequency should be === "0.990.000"
    }

    it("should handle conversions from and to hertz and megahertz repeatedly") {
      Frequency.toMHz(new Frequency("146.52").Hz) should be === "146.520.000"
      Frequency.toHz(new Frequency(146520000).MHz) should be === 146520000
    }

    it("should determine the amateur radio band, given a ham frequency") {
      "146.520".MHz.band should be === Some("2m")
      999.MHz.band should be === None
    }

    it("should work with huge frequencies") {
      val freq = new Frequency("3000000000000000000000000.000.000") + 2.kHz
      freq.frequency should be === "3000000000000000000000000.002.000"
    }
  }
}

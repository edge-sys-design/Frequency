# Frequency

A library for dealing with RF Frequency conversions and abstractions in Scala.

This library lets you represent frequencies in a variety of ways, and do a
vast amount of conversions relating to them.

Currently, the library is slightly tied to amateur (ham) radio frequencies, but
the end goal for the library is to work for any usecase relating to radio in
general.

# Examples

```scala
import language.postfixOps
val callingFrequency = HamFrequency(146.52 MHz)
callingFrequency.band // => Some(2m)
```

Note that `.band` above is likely to change, as we try to make this library not
so ham radio specific.

### Creating a `Frequency`

`Frequency` objects can be created in all of the following ways:

```scala
import language.postfixOps
146 MHz
146.52 MHz
"146.52" MHz
"146.520.000" MHz
14.325 MHz
14325000 Hz // 14.325.000 MHz
new Frequency("146.520.000") // MHz
new Frequency(146520000) // Hz
```

# Planned

### `Wavelength` class

```scala
import language.postfixOps
val band = 2 meters // Creates a Wavelength object.
band contains(146.52 MHz) // true

(146.520 MHz) band // Wavelength(2m)
```

### Adding/Subtracting `Frequency` instances

```scala
import language.postfixOps
(146.52 MHz) + (1 MHz) // Frequency(147.52 MHz)
```

# License

3-clause BSD, same as Scala itself:

```
Copyright (c) 2012 Edge System Design, unless otherwise specified.
All rights reserved.

This software was developed by Edge System Design, Ohio, USA.

Permission to use, copy, modify, and distribute this software in source
or binary form for any purpose with or without fee is hereby granted,
provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.

   3. Neither the name of Edge System Design nor the names of its contributors
      may be used to endorse or promote products derived from this
      software without specific prior written permission.


THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.
```

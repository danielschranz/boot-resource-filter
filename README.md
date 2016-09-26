[![License: LGPL v3](https://img.shields.io/badge/license-LGPL_v3-brightgreen.svg)](https://www.gnu.org/licenses/lgpl-3.0.en.html#content)
[![Boot](https://img.shields.io/badge/boot-2.6.0-ECC42F.svg?style=flat)](http://boot-clj.com/)
[![Clojars](https://img.shields.io/badge/clojars-0.1.0-blue.svg?style=flat)](https://clojars.org/....)

boot-resource-filter (WIP)
==========
`[schranz/boot-resource-filter "0.1.0"]` (not yet)

A Boot task allowing you to replace tokens in resource files. 

**TODO:** add proper documentation here

### How to test and build

* building: `boot pom jar target`

### Example usages
It is possible to run this task from the command line:
- `boot filter-resources -m ".*\.edn" -r user=mike -r password=secret'`

but most likely you will want to run it from within a `build.boot`
* **TODO** add example here

### Available options
```
Options:
  -h, --help                       Print this help info.
  -f, --files REGEX                Conj REGEX onto the set of regular expressions to select files for filtering.
  -n, --no-properties              Do not use system properties to replace tokens.
  -r, --replacements MATCH=RESULT  Conj [MATCH RESULT] onto the replacements map. You can overwrite system properties by adding entries here.
 ```

### License
Copyright © 2016 Daniel Schranz.

Distributed under the GNU Lesser General Public License v3.
